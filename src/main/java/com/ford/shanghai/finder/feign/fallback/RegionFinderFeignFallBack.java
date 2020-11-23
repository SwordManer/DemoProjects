package com.ford.shanghai.finder.feign.fallback;

import org.springframework.stereotype.Component;

import com.ford.shanghai.finder.feign.RegionFinderClient;
import com.ford.shanghai.finder.feign.response.RegionPOIResponse;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class RegionFinderFeignFallBack implements RegionFinderClient{

	@Override
	public RegionPOIResponse fetchPOIsInCircle(String query, String location, String radius, String output, String apiKey) {
		log.error("Failed to fetch POIs data in circle region by feign: query: {}, location: {} ", query, location);
		return null;
	}

}