package com.ford.shanghai.finder.feign.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class RegionPOIResponse extends BaseResponse{

	@JsonProperty("result_type")
	private String resultType;
	private List<PointOfInterest> results;

}
