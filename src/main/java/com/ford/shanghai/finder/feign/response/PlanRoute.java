package com.ford.shanghai.finder.feign.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class PlanRoute {

	private Location origin;

	private Location destination;

	private String tag;

	@JsonProperty("route_id")
	private String routeId;

	private Integer distance;

	private Integer duration;

	@JsonProperty("taxi_fee")
	private Integer taxiFee;

	@JsonProperty("restriction_info")
	private RestrictionInfo restrictionInfo;

	private Integer toll;

	@JsonProperty("toll_distance")
	private Integer tollDistance;

	private List<Step> steps;
}
