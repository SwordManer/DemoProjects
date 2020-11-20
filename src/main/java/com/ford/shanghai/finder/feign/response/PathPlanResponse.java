package com.ford.shanghai.finder.feign.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class PathPlanResponse extends BaseResponse{

	private Integer type;
	private PathPlanResult result;
}
