package com.ford.shanghai.finder.feign.response;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Traffic {

	private Integer status;
	@JsonProperty("geo_cnt")
	private Integer geoCnt;
	private BigDecimal distance;
}
