package com.ford.shanghai.finder.feign.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Step {

	@JsonProperty("leg_index")
	private Integer legIndex;
	@JsonProperty("road_name")
	private String roadName;
	private Integer direction;
	private Integer distance;
	private Integer duration;
	@JsonProperty("road_type")
	private Integer roadType;
	private Integer toll;
	private String toll_distance;
	@JsonProperty("traffic_condition")
	private List<Traffic> trafficCondition;
	private String path;
	@JsonProperty("start_location")
	private Location startLocation;
	@JsonProperty("end_location")
	private Location endLocation;

}
