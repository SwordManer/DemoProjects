package com.ford.shanghai.finder.response;

import lombok.Data;

@Data
public abstract class AbstractResponse {

	private Integer status;
	private String message;
}
