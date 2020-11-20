package com.ford.shanghai.finder.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static com.ford.shanghai.finder.utils.Constants.MEDIA_TYPE_TEXT_JAVASCRIPT_UTF_8;

import com.ford.shanghai.finder.feign.response.PathPlanResponse;
import com.ford.shanghai.finder.feign.response.SearchingResultResponse;

@FeignClient(url = "${api.map.baidu.url}",name="pathplan")
public interface PathPlanFeignClient {

	@GetMapping(value="/direction/v2/driving", produces = MEDIA_TYPE_TEXT_JAVASCRIPT_UTF_8)
	public PathPlanResponse fetchDrivingPathPlan(
							@RequestParam("origin") String origin, 
							@RequestParam("destination") String destination,
							@RequestParam("ak") String apiKey);

	@GetMapping(value="/place/v2/search", produces = MEDIA_TYPE_TEXT_JAVASCRIPT_UTF_8)
	public SearchingResultResponse fetchSearchingResult(
			@RequestParam("query") String query,
			@RequestParam("location") String location,
			@RequestParam("radius") String radius,
			@RequestParam("output") String output,
			@RequestParam("ak") String apiKey);

}
