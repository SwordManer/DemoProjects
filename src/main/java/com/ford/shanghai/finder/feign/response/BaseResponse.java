package com.ford.shanghai.finder.feign.response;

import lombok.Data;

@Data
public abstract class BaseResponse {

	private Integer status;
	private String message;
}
