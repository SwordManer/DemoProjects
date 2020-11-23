package com.ford.shanghai.finder.feign.fallback;

import org.springframework.stereotype.Component;

import com.ford.shanghai.finder.feign.RegionFinderClient;
import com.ford.shanghai.finder.feign.response.RegionPOIResponse;

@Component
public class RegionFinderFeignFallBack implements RegionFinderClient{

	@Override
	public RegionPOIResponse fetchPOIsInCircle(String query, String location, String radius, String output, String apiKey) {

		return null;
	}

}