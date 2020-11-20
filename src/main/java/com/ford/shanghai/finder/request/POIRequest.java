package com.ford.shanghai.finder.request;

import lombok.Data;

@Data
public class POIRequest {
	private String query;
	private String origin;
	private String destination;
}
