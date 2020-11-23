package com.ford.shanghai.finder.exception;

public class FeignInvocationExcetion extends Exception {

	private static final long serialVersionUID = -426795323769522238L;

	public FeignInvocationExcetion(String message){
		super(message);
	}
}
