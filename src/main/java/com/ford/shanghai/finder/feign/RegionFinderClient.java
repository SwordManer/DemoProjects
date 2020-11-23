package com.ford.shanghai.finder.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static com.ford.shanghai.finder.utils.Constants.MEDIA_TYPE_TEXT_JAVASCRIPT_UTF_8;

import com.ford.shanghai.finder.feign.fallback.RegionFinderFeignFallBack;
import com.ford.shanghai.finder.feign.response.RegionPOIResponse;

@FeignClient(url = "${api.map.baidu.url}", fallback = RegionFinderFeignFallBack.class, name="regionFinder")
public interface RegionFinderClient {

	@GetMapping(value="/place/v2/search", produces = MEDIA_TYPE_TEXT_JAVASCRIPT_UTF_8)
	public RegionPOIResponse fetchPOIsInCircle(
			@RequestParam("query") String query,
			@RequestParam("location") String location,
			@RequestParam("radius") String radius,
			@RequestParam("output") String output,
			@RequestParam("ak") String apiKey);

}
