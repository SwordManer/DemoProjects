package com.ford.shanghai.finder.response;

import java.util.List;

import com.ford.shanghai.finder.feign.response.PointOfInterest;

import lombok.Data;

@Data
public class InterestPointResponse {

	private List<PointOfInterest> interestPoints;

}
