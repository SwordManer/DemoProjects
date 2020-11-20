package com.ford.shanghai.finder.feign.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class PathPlanResult {

	private String restriction;
	private Integer total;
	@JsonProperty("session_id")
	private String sessionId;
	private List<PlanRoute> routes;
}
