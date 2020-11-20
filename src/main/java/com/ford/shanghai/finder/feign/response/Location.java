package com.ford.shanghai.finder.feign.response;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Location {

	private BigDecimal lng;
	private BigDecimal lat;

	@Override
	public String toString() {
		return lat+","+lng;
	}
}
