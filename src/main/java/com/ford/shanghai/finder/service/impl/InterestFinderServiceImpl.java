package com.ford.shanghai.finder.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ford.shanghai.finder.dao.InterestPointDAO;
import com.ford.shanghai.finder.dao.LocationInterestPointDAO;
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
import com.ford.shanghai.finder.service.InterestFinderService;

import com.google.common.collect.Sets;
import com.google.common.collect.Sets.SetView;

import static com.ford.shanghai.finder.utils.Constants.STATUS_SUCCESS;

@Service
public class InterestFinderServiceImpl implements InterestFinderService {

	@Autowired
	private PathPlanFeignClient pathPlanFeignClient;

	@Autowired
	private InterestPointDAO interestPointDAO;

	@Autowired
	private LocationInterestPointDAO locationInterestPointDAO;

	@Value("${api.map.baidu.apikey}")
	private String apiKey;

	public InterestPointEntity fetchPathPlan(String origin, String destination) {

		PathPlanResponse response = pathPlanFeignClient.fetchDrivingPathPlan(origin, destination, apiKey);
		return null;
	}

	public WaysidePOIsEntity fetchPOI(String origin, String destination, String poiType) {

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

		Set<LocationInterestPoint> locationInterestPoints = locationInterestPointDAO.queryPOIsByLocs(poiType, locs);
		Set<String> locsInDB = locationInterestPoints.stream().map(LocationInterestPoint::getLocation).collect(Collectors.toSet());
		
		Set<InterestPoint> locPOIs = interestPointDAO.queryPOIsByLocs(poiType, locsInDB);
		Set<PointOfInterest> poisFromDB = InterestPointMapper.INSTANCE.toDomains(locPOIs);
		SetView<String> locSetNotInDB = Sets.difference(locs,locsInDB);

		Set<CompletableFuture<Set<PointOfInterest>>> searchingResultFutures = locSetNotInDB.stream().map(
					loc -> CompletableFuture.supplyAsync( () -> fetchPOIsByFeign(poiType, loc.toString())))
				.collect(Collectors.toSet());

		Set<PointOfInterest> poisFromFeign = searchingResultFutures.parallelStream()
											.map(CompletableFuture::join)
											.flatMap(list -> list.stream()).collect(Collectors.toSet());

		List<PointOfInterest> searchingResults = mergeAndResolveSet(poisFromDB, poisFromFeign);
		WaysidePOIsEntity waysidePOIs = new WaysidePOIsEntity();
		waysidePOIs.setSearchingResults(searchingResults);
		return waysidePOIs;
	}

	private List<PointOfInterest> mergeAndResolveSet(Set<PointOfInterest> poisFromDB,
			Set<PointOfInterest> poisFromFeign) {

		
		return null;
	}

	private Set<PointOfInterest> fetchPOIsByFeign(String poiType, String loc) {
//		try to fetch the data from db:
		SearchingResultResponse result = pathPlanFeignClient.fetchSearchingResult(poiType, loc, "5000", "json", apiKey);
		if (result==null||result.getStatus()!=STATUS_SUCCESS) {
			return null;
		}
		List<PointOfInterest> pois = result.getResults();
		List<InterestPoint> interestPoints = InterestPointMapper.INSTANCE.toDtos(result.getResults());
		interestPointDAO.saveAll(interestPoints);
		return new HashSet<PointOfInterest>(pois);
	}

	@Override
	public String fetchSearching(String query, String location, String radius, String output) {
		SearchingResultResponse response = pathPlanFeignClient.fetchSearchingResult(query, location, radius, output, apiKey);
		return null;
	}
}
