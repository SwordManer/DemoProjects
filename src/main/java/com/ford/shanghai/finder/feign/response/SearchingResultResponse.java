package com.ford.shanghai.finder.feign.response;

import java.util.List;

import lombok.Data;

@Data
public class SearchingResultResponse extends BaseResponse{

	private List<PointOfInterest> results;

}
