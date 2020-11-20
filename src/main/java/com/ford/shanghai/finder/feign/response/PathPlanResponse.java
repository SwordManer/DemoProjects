package com.ford.shanghai.finder.feign.response;

import lombok.Data;

@Data
public class PathPlanResponse extends BaseResponse{

	private Integer type;
	private PathPlanResult result;
}
