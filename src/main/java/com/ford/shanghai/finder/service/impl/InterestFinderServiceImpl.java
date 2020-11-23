package com.ford.shanghai.finder.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;

import com.ford.shanghai.finder.dao.FinderRecordDAO;
import com.ford.shanghai.finder.dao.InterestPointDAO;
import com.ford.shanghai.finder.dao.LocationInterestPointDAO;
import com.ford.shanghai.finder.dao.entity.FinderRecord;
import com.ford.shanghai.finder.dao.entity.InterestPoint;
import com.ford.shanghai.finder.dao.entity.LocationInterestPoint;
import com.ford.shanghai.finder.entity.WaysidePOIsEntity;
import com.ford.shanghai.finder.exception.EmptyDataSetException;
import com.ford.shanghai.finder.exception.FeignInvocationExcetion;
import com.ford.shanghai.finder.feign.PathPlanFeignClient;
import com.ford.shanghai.finder.feign.RegionFinderClient;
import com.ford.shanghai.finder.feign.response.Location;
import com.ford.shanghai.finder.feign.response.PathPlanResponse;
import com.ford.shanghai.finder.feign.response.PointOfInterest;
import com.ford.shanghai.finder.feign.response.RegionPOIResponse;
import com.ford.shanghai.finder.feign.response.Step;
import com.ford.shanghai.finder.mapper.InterestPointMapper;
import com.ford.shanghai.finder.mapper.LocationMapper;
import com.ford.shanghai.finder.service.InterestFinderService;
import com.google.common.collect.Sets;
import com.google.common.collect.Sets.SetView;

import lombok.extern.log4j.Log4j2;

import static com.ford.shanghai.finder.utils.Constants.CHANNEL_TYPE_BAIDUMAP;
import static com.ford.shanghai.finder.utils.Constants.FORMAT_JSON;
import static com.ford.shanghai.finder.utils.Constants.STATUS_SUCCESS;

@Log4j2
@Service
public class InterestFinderServiceImpl implements InterestFinderService {

	@Autowired
	private PathPlanFeignClient pathPlanFeignClient;

	@Autowired
	private RegionFinderClient regionFinderClient;
	
	@Autowired
	private InterestPointDAO interestPointDAO;

	@Autowired
	private LocationInterestPointDAO locationInterestPointDAO;

	@Autowired
	private PlatformTransactionManager platformTransactionManager;

	@Autowired
	private TransactionDefinition transactionDefinition;
	
	@Autowired
	private FinderRecordDAO finderRecordDAO;

	@Autowired
	private Executor executor;
	
	@Value("${api.map.baidu.apikey}")
	private String apiKey;

	public WaysidePOIsEntity fetchPOI(String origin, String destination, String poiType) throws Exception {

		log.info("Try to invocate the service of BaiduMap to fetch path plan while driving: poiType: {}", poiType);
		PathPlanResponse response = pathPlanFeignClient.fetchDrivingPathPlan(origin, destination, apiKey);
		if (response==null || response.getStatus()!=STATUS_SUCCESS) {
			log.error("Response from the invocation by feign is not healthy");
			throw new FeignInvocationExcetion("Invocation by feign is not healthy");
		}

		List<Location> locations = Optional.of(response.getResult()).get()
				.getRoutes().stream()
				.flatMap(route -> route.getSteps().stream())
				.map(Step::getStartLocation)
				.collect(Collectors.toList());

		Set<String> locs = locations.stream().map(Location::toString).collect(Collectors.toSet());
		Set<PointOfInterest> poisFromDB = queryFromDBAndAssembly(poiType, locs);
		Set<PointOfInterest> poisFromFeign = queryFromFeignAndAssembly(poiType, locs);
		List<PointOfInterest> poiResults = mergeAndResolveSet(poisFromDB, poisFromFeign);

		WaysidePOIsEntity waysidePOIs = new WaysidePOIsEntity();
		waysidePOIs.setSearchingResults(poiResults);
		CompletableFuture.runAsync(() -> recordQueryInformation(origin, destination, poiType), executor);
		return waysidePOIs;
	}

	private void recordQueryInformation(String origin, String destination, String poiType) {
		try {
			FinderRecord record = new FinderRecord();
			record.setQueryTime(new Date());
			record.setPoiType(poiType);
			record.setStartLocation(origin);
			record.setEndLocation(destination);
			finderRecordDAO.save(record);
		} catch (Exception e) {
			log.error("Error encountered while save the query record to DB.");
		}
	}

	private Set<PointOfInterest> queryFromFeignAndAssembly(String poiType, Set<String> locs) {
		if (poiType==null||locs==null||locs.isEmpty()) {
			log.warn("The input locs or poiType is empty or invalid, pay attention if data not totally fetched from DB.");
			return Collections.emptySet();
		}

		log.info("It is going to do the invocation by feign fetching pois, with the CompletableFuture style.");
		Set<CompletableFuture<Set<PointOfInterest>>> resultFutures = locs.stream().map(
					loc -> CompletableFuture.supplyAsync(()-> fetchPOIsByFeign(poiType, loc.toString()), executor))
				.collect(Collectors.toSet());

		Set<PointOfInterest> poisFromFeign = resultFutures.parallelStream()
											.map(CompletableFuture::join)
											.flatMap(list -> list.stream()).collect(Collectors.toSet());
		log.info("Complete the assembly process to the data from invocation by feign.");
		return poisFromFeign;
	}

	private Set<PointOfInterest> queryFromDBAndAssembly(String poiType, Set<String> locs) {

		if (poiType==null||locs==null||locs.isEmpty()) {
			log.warn("Warn: the data fetched from DB is empty, pay attention if this is not the first invocation around the origin and destination.");
			return Collections.emptySet();
		}
		Set<String> copyedLocs = locs.stream().collect(Collectors.toSet());
		TransactionStatus transactionStatus = platformTransactionManager.getTransaction(transactionDefinition);
		try {
			Set<LocationInterestPoint> locationInterestPoints = locationInterestPointDAO.queryPOIsByLocs(poiType, locs);
			Set<String> locsInDB = locationInterestPoints.stream().map(LocationInterestPoint::getLocation).collect(Collectors.toSet());
			Set<String> poiLocsInDB = locationInterestPoints.stream().map(LocationInterestPoint::getPoiLocation).collect(Collectors.toSet());
			Set<InterestPoint> locPOIs = interestPointDAO.queryPOIsByLocs(poiType, poiLocsInDB);
			locs.removeAll(locsInDB);
			platformTransactionManager.commit(transactionStatus);
			return InterestPointMapper.INSTANCE.toDomains(locPOIs);
		} catch (Exception e) {
			log.error("Error encountered whild fetching locationInteresPoint and interestPoint data from DB before invocation by feign");
			locs = copyedLocs.stream().collect(Collectors.toSet());
			platformTransactionManager.rollback(transactionStatus);
			return Collections.emptySet();
		}
	}

	private List<PointOfInterest> mergeAndResolveSet(Set<PointOfInterest> poisFromDB, Set<PointOfInterest> poisFromFeign) throws EmptyDataSetException {
		if (poisFromDB.isEmpty()&&poisFromFeign.isEmpty()) {
			log.error("Data set from DB and invocation by feign is empty");
			throw new EmptyDataSetException("data set from DB and invocation by feign is empty");
		}
		SetView<PointOfInterest> mergedPoi = Sets.union(poisFromDB, poisFromFeign);
		return new ArrayList<PointOfInterest>(mergedPoi);
	}

	private Set<PointOfInterest> fetchPOIsByFeign(String poiType, String loc) {
		RegionPOIResponse result = regionFinderClient.fetchPOIsInCircle(poiType, loc, "5000", FORMAT_JSON, apiKey);
		if (result==null||result.getStatus()!=STATUS_SUCCESS) {
			log.warn("Response is not healthy while invocating by feign: poiType: {}, loc: {}", poiType, loc);
			return Collections.emptySet();
		}
		List<PointOfInterest> pois = result.getResults();
		CompletableFuture.runAsync(() -> asyncUpData2DB(poiType, loc, pois), executor);
		return new HashSet<PointOfInterest>(pois);
	}

	private void asyncUpData2DB(String poiType, String loc, List<PointOfInterest> pois) {

		log.info("Entering into the async process to save the data of POIs from invocation to DB: poiType: {}, location: {}", poiType, loc);
		TransactionStatus transactionStatus = platformTransactionManager.getTransaction(transactionDefinition);
		try {
			Set<InterestPoint> interestPoints = InterestPointMapper.INSTANCE.toDtos(new HashSet<>(pois));
			interestPoints.forEach(ip -> {
				ip.setChannel(CHANNEL_TYPE_BAIDUMAP);
				ip.setPoiType(poiType);
			});
			LocationMapper mapper = new LocationMapper();
			Set<LocationInterestPoint> locIps = pois.stream()
					.map(poi -> {
						LocationInterestPoint locIp = new LocationInterestPoint();
						Location location = mapper.map2Location(loc);
						locIp.setLocation(loc);
						locIp.setLocLatitude(location.getLat());
						locIp.setLocLogitude(location.getLng());
						locIp.setPoiType(poiType);
						locIp.setPoiId(poi.getUid());
						locIp.setPoiLocation(String.valueOf(poi.getLocation()));
						locIp.setPoiLatitude(poi.getLocation().getLat());
						locIp.setPoiLogitude(poi.getLocation().getLng());
						locIp.setRadius(new BigDecimal("5000"));
//					TODO to caculate the distance
//					locIp.setDistance(distance);
					return locIp; 
				}).collect(Collectors.toSet());

			interestPointDAO.saveAll(interestPoints);
			locationInterestPointDAO.saveAll(locIps);
			platformTransactionManager.commit(transactionStatus);
			log.info("Transaction committed after save POIs data successfully.");
		} catch (Exception e) {
			log.error("Error encountered while async data from POIs invocation to DB. The save operation is being rollbacked: poiType: {}, location: {}", poiType, loc);
			platformTransactionManager.rollback(transactionStatus);
		}
	}
}
