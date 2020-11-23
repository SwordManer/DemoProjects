package com.ford.shanghai.finder.feign.fallback;

import org.springframework.stereotype.Component;

import com.ford.shanghai.finder.feign.PathPlanFeignClient;
import com.ford.shanghai.finder.feign.response.PathPlanResponse;

@Component
public class PathPlanFeignFallBack implements PathPlanFeignClient{

	@Override
	public PathPlanResponse fetchDrivingPathPlan(String origin, String destination, String apiKey) {

		
		return null;
	}

}