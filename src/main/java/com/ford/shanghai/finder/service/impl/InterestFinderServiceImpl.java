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
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ford.shanghai.finder.dao.FinderRecordDAO;
import com.ford.shanghai.finder.dao.InterestPointDAO;
import com.ford.shanghai.finder.dao.LocationInterestPointDAO;
import com.ford.shanghai.finder.dao.entity.FinderRecord;
import com.ford.shanghai.finder.dao.entity.InterestPoint;
import com.ford.shanghai.finder.dao.entity.LocationInterestPoint;
import com.ford.shanghai.finder.entity.InterestPointEntity;
import com.ford.shanghai.finder.entity.WaysidePOIsEntity;
import com.ford.shanghai.finder.feign.PathPlanFeignClient;
import com.ford.shanghai.finder.feign.response.Location;
import com.ford.shanghai.finder.feign.response.PathPlanResponse;
import com.ford.shanghai.finder.feign.response.PointOfInterest;
import com.ford.shanghai.finder.feign.response.SearchingResultResponse;
import com.ford.shanghai.finder.feign.response.Step;
import com.ford.shanghai.finder.mapper.InterestPointMapper;
import com.ford.shanghai.finder.mapper.LocationMapper;
import com.ford.shanghai.finder.service.InterestFinderService;
import com.google.common.collect.Sets;
import com.google.common.collect.Sets.SetView;

import static com.ford.shanghai.finder.utils.Constants.CHANNEL_TYPE_BAIDUMAP;
import static com.ford.shanghai.finder.utils.Constants.STATUS_SUCCESS;

@Service
public class InterestFinderServiceImpl implements InterestFinderService {

	@Autowired
	private PathPlanFeignClient pathPlanFeignClient;

	@Autowired
	private InterestPointDAO interestPointDAO;

	@Autowired
	private LocationInterestPointDAO locationInterestPointDAO;

	@Autowired
	private FinderRecordDAO finderRecordDAO;
	
	@Value("${api.map.baidu.apikey}")
	private String apiKey;

	public InterestPointEntity fetchPathPlan(String origin, String destination) {

		PathPlanResponse response = pathPlanFeignClient.fetchDrivingPathPlan(origin, destination, apiKey);
		return null;
	}

	public WaysidePOIsEntity fetchPOI(String origin, String destination, String poiType) {

		WaysidePOIsEntity waysidePOIs = null;
		try {
			PathPlanResponse response = pathPlanFeignClient.fetchDrivingPathPlan(origin, destination, apiKey);
			Integer status = Optional.of(response).map(PathPlanResponse::getStatus).get();
			if (status!=STATUS_SUCCESS) {
				return null;
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

			waysidePOIs = new WaysidePOIsEntity();
			waysidePOIs.setSearchingResults(poiResults);
			recordQueryInformation(origin, destination, poiType);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return waysidePOIs;
	}

	private void recordQueryInformation(String origin, String destination, String poiType) {
		FinderRecord record = new FinderRecord();
		record.setQueryTime(new Date());
		record.setPoiType(poiType);
		record.setStartLocation(origin);
		record.setEndLocation(destination);
		finderRecordDAO.save(record);
	}

	private Set<PointOfInterest> queryFromFeignAndAssembly(String poiType, Set<String> locs) {
		if (StringUtils.isBlank(poiType)||locs==null||locs.isEmpty()) {
			return Collections.emptySet();
		}
		Set<CompletableFuture<Set<PointOfInterest>>> searchingResultFutures = locs.stream().map(
					loc -> CompletableFuture.supplyAsync( () -> fetchPOIsByFeign(poiType, loc.toString())))
				.collect(Collectors.toSet());

		Set<PointOfInterest> poisFromFeign = searchingResultFutures.parallelStream()
											.map(CompletableFuture::join)
											.flatMap(list -> list.stream()).collect(Collectors.toSet());
		return poisFromFeign;
	}

	private Set<PointOfInterest> queryFromDBAndAssembly(String poiType, Set<String> locs) {

		if (StringUtils.isBlank(poiType)||locs==null||locs.isEmpty()) {
			return Collections.emptySet();
		}

		Set<LocationInterestPoint> locationInterestPoints = locationInterestPointDAO.queryPOIsByLocs(poiType, locs);
		Set<String> locsInDB = locationInterestPoints.stream().map(LocationInterestPoint::getLocation).collect(Collectors.toSet());
		Set<String> poiLocsInDB = locationInterestPoints.stream().map(LocationInterestPoint::getPoiLocation).collect(Collectors.toSet());
		locs.removeAll(locsInDB);
		Set<InterestPoint> locPOIs = interestPointDAO.queryPOIsByLocs(poiType, poiLocsInDB);
		Set<PointOfInterest> poisFromDB = InterestPointMapper.INSTANCE.toDomains(locPOIs);
		return new HashSet<PointOfInterest>(poisFromDB);
	}

	private List<PointOfInterest> mergeAndResolveSet(Set<PointOfInterest> poisFromDB,
			Set<PointOfInterest> poisFromFeign) {
		SetView<PointOfInterest> mergedPoi = Sets.union(poisFromDB, poisFromFeign);
		return new ArrayList<PointOfInterest>(mergedPoi);
	}

	private Set<PointOfInterest> fetchPOIsByFeign(String poiType, String loc) {
//		try to fetch the data from db:
		SearchingResultResponse result = pathPlanFeignClient.fetchSearchingResult(poiType, loc, "5000", "json", apiKey);
		if (result==null||result.getStatus()!=STATUS_SUCCESS) {
			return null;
		}
		List<PointOfInterest> pois = result.getResults();
		Set<InterestPoint> interestPoints = InterestPointMapper.INSTANCE.toDtos(new HashSet<>(result.getResults()));
		interestPoints.forEach(ip -> {
			ip.setChannel(CHANNEL_TYPE_BAIDUMAP);
			ip.setPoiType(poiType);
		});
		interestPointDAO.saveAll(interestPoints);
//		insert into the locationInterestPoint table:
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
//					locIp.setDistance(distance);
				return locIp; 
			}).collect(Collectors.toSet());
		locationInterestPointDAO.saveAll(locIps);
		return new HashSet<PointOfInterest>(pois);
	}

	@Override
	public String fetchSearching(String query, String location, String radius, String output) {
		SearchingResultResponse response = pathPlanFeignClient.fetchSearchingResult(query, location, radius, output, apiKey);
		return null;
	}
}
