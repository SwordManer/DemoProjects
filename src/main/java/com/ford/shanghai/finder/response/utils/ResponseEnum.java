package com.ford.shanghai.finder.response.utils;

public enum ResponseEnum {

	SUCCESS(0, "SUCCESS"),
	ERROR(1111, "ERROR"),
	SYSTEM_ERROR(1000, "SYSTEM_ERROR"),
	BUSSINESS_ERROR(2001, "BUSSINESS_ERROR"),
	VERIFY_CODE_ERROR(2002, "VERIFY_CODE_ERROR"),
	PARAM_ERROR(2003, "PARAM_ERROR");
	
	private Integer status;
	private String message;

	ResponseEnum(Integer status, String message) {
		this.status = status;
		this.message = message;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}