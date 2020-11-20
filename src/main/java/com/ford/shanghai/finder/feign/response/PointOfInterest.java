package com.ford.shanghai.finder.feign.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class PointOfInterest {
	private String name;
	private Location location;
	private String address;
	private String province;
	private String city;
	private String area;
	@JsonProperty("street_id")
	private String streetId;
	private String telephone;
	private Integer detail;
	private String uid;
}
