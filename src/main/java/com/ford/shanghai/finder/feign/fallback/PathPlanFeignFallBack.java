package com.ford.shanghai.finder.feign.fallback;

import org.springframework.stereotype.Component;

import com.ford.shanghai.finder.feign.PathPlanFeignClient;
import com.ford.shanghai.finder.feign.response.PathPlanResponse;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class PathPlanFeignFallBack implements PathPlanFeignClient{

	@Override
	public PathPlanResponse fetchDrivingPathPlan(String origin, String destination, String apiKey) {
		log.error("Failed to fetch path plan data while driving by feign: query: {}, location: {} ", origin, destination);
		return null;
	}

}