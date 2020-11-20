package com.ford.shanghai.finder.feign.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PointOfInterest {

	private String uid;
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

}
