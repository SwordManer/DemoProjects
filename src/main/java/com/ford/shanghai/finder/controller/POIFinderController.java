package com.ford.shanghai.finder.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ford.shanghai.finder.entity.WaysidePOIsEntity;
import com.ford.shanghai.finder.request.POIRequest;
import com.ford.shanghai.finder.response.InterestPointResponse;
import com.ford.shanghai.finder.response.utils.Response;
import com.ford.shanghai.finder.response.utils.ResponseEnum;
import com.ford.shanghai.finder.service.InterestFinderService;

import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
public class POIFinderController extends AbstractController{

	@Autowired
	private InterestFinderService interestFinderService;

	@RequestMapping(value = "/path/driving/poi", method = RequestMethod.POST)
	public Response<InterestPointResponse> fetchPointsOfInterest(@RequestBody POIRequest request) {

		try {
			WaysidePOIsEntity waysidePOIs = interestFinderService.fetchPOI(request.getOrigin(), request.getDestination(), request.getQuery());
			InterestPointResponse response = new InterestPointResponse();
			response.setInterestPoints(waysidePOIs.getSearchingResults());
			return Response.buildSuccess(response);
		} catch (Exception e) {
			log.error("Error encountered while fetch pois: ", e);
			return Response.buildError(ResponseEnum.ERROR);
		}
	}
}
