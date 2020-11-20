package com.ford.shanghai.finder.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ford.shanghai.finder.entity.InterestPointEntity;
import com.ford.shanghai.finder.entity.WaysidePOIsEntity;
import com.ford.shanghai.finder.request.POIRequest;
import com.ford.shanghai.finder.request.PathPlanRequest;
import com.ford.shanghai.finder.response.InterestPointResponse;
import com.ford.shanghai.finder.service.InterestFinderService;

@RestController
public class POIFinderController {

	@Autowired
	private InterestFinderService interestFinderService;

	@RequestMapping(value = "/path/driving", method = RequestMethod.POST)
	public InterestPointResponse fetchPathPlan(@RequestBody PathPlanRequest request) {

		InterestPointEntity fetchPathPlan = interestFinderService.fetchPathPlan(request.getOrigin(), request.getDestination());
		return null;
	}

	@RequestMapping(value = "/path/driving/poi", method = RequestMethod.POST)
	public InterestPointResponse fetchPointsOfInterest(@RequestBody POIRequest request) {

		WaysidePOIsEntity waysidePOIs = interestFinderService.fetchPOI(request.getOrigin(), request.getDestination(), request.getQuery());
		InterestPointResponse response = new InterestPointResponse();
		response.setInterestPoints(waysidePOIs.getSearchingResults());
		return response;
	}

	@RequestMapping("/testSearching")
	public Object fetchSearchingPlaces(
			@RequestParam("query") String query,
			@RequestParam("location") String location,
			@RequestParam("radius") String radius,
			@RequestParam("output") String output,
			@RequestParam("ak") String apiKey) {
		return interestFinderService.fetchSearching(query, location, radius, output);
	}
}
