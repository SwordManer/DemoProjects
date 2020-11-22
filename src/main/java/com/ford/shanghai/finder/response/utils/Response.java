package com.ford.shanghai.finder.response.utils;

import java.io.Serializable;

import lombok.Data;

@Data
public class Response<T> implements Serializable {

	private static final long serialVersionUID = -8285922745983067807L;

	private Integer status;

	private String message;

	private T data;

	public Response(Integer status, String message, T data) {
		this.status = status;
		this.message = message;
		this.data = data;
	}

	public Response(Integer status, String message) {
		this.status = status;
		this.message = message;
	}

	public Response(ResponseEnum responseEnum) {
		this.status = responseEnum.getStatus();
		this.message = responseEnum.getMessage();
	}

	public Response(ResponseEnum responseEnum, T data) {
		this.status = responseEnum.getStatus();
		this.message = responseEnum.getMessage();
		this.data = data;
	}

	public static <T> Response<T> buildSuccess(T data) {
		return new Response<T>(ResponseEnum.SUCCESS, data);
	}

	public static <T> Response<T> buildSuccess() {
		return new Response<T>(ResponseEnum.SUCCESS);
	}

	public static <T> Response<T> buildSuccess(String message) {
		return new Response<T>(ResponseEnum.SUCCESS.getStatus(), message);
	}

	public static <T> Response<T>  buildSuccess(Integer status, String message) {
		return new Response<T>(status, message);
	}

	public static <T> Response<T> buildSuccess(Integer status, String message, T data) {
		return new Response<T>(status, message, data);
	}

	public static <T> Response<T> buildSuccess(ResponseEnum responseEnum) {
		return new Response<T>(responseEnum);
	}

	public static <T> Response<T> buildError(T data) {
		return new Response<T>(ResponseEnum.ERROR, data);
	}
 
	public static <T> Response<T> buildError() {
		return new Response<T>(ResponseEnum.ERROR);
	}

	public static <T> Response<T> buildError(String message) {
		return new Response<T>(ResponseEnum.ERROR.getStatus(), message);
	}

	public static <T> Response<T>  buildError(Integer status, String message) {
		return new Response<T>(status, message);
	}

	public static <T> Response<T> buildError(Integer status, String message, T data) {
		return new Response<T>(status, message, data);
	}

	public static <T> Response<T>  buildError(ResponseEnum responseEnum) {
		return new Response<T>(responseEnum);
	}
}