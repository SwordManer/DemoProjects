package com.ford.shanghai.finder.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ford.shanghai.finder.dao.InterestFinderDAO;
import com.ford.shanghai.finder.dao.entity.InterestPoint;
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
import static com.ford.shanghai.finder.utils.Constants.STATUS_SUCCESS;

@Service
public class InterestFinderServiceImpl implements InterestFinderService {

	@Autowired
	private PathPlanFeignClient pathPlanFeignClient;

	@Autowired
	private InterestFinderDAO interestFinderDAO;
	
	@Value("${api.map.baidu.apikey}")
	private String apiKey;
	
	public InterestPointEntity fetchPathPlan(String origin, String destination) {

		PathPlanResponse response = pathPlanFeignClient.fetchDrivingPathPlan(origin, destination, apiKey);
		return null;
	}

	public WaysidePOIsEntity fetchPOI(String origin, String destination, String query) {

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

		List<CompletableFuture<List<PointOfInterest>>> searchingResultFutures = locations.stream().map(
					loc -> CompletableFuture.supplyAsync( () -> fetchPOIByLocation(query, loc.toString())))
				.collect(Collectors.toList());

		List<PointOfInterest> searchingResults = searchingResultFutures.parallelStream()
											.map(CompletableFuture::join)
											.flatMap(list -> list.stream()).collect(Collectors.toList());
		WaysidePOIsEntity waysidePOIs = new WaysidePOIsEntity();
		waysidePOIs.setSearchingResults(searchingResults);
		return waysidePOIs;
	}

	private List<PointOfInterest> fetchPOIByLocation(String query, String loc) {
//		try to fetch the data from db:
		List<InterestPoint> poisInDB = interestFinderDAO.queryPOIsByConditions(query, loc);
		List<PointOfInterest> domains = InterestPointMapper.INSTANCE.toDomains(poisInDB);

		if (poisInDB==null || poisInDB.isEmpty()) {
			SearchingResultResponse result = pathPlanFeignClient.fetchSearchingResult(query, loc, "5000", "json", apiKey);
			if (result==null||result.getStatus()!=STATUS_SUCCESS) {
				return null;
			}
			List<InterestPoint> pois = InterestPointMapper.INSTANCE.toDtos(result.getResults());
			interestFinderDAO.savePOIs(pois);
		}
		return domains;
	}

	@Override
	public String fetchSearching(String query, String location, String radius, String output) {
		SearchingResultResponse response = pathPlanFeignClient.fetchSearchingResult(query, location, radius, output, apiKey);
		return null;
	}
}
