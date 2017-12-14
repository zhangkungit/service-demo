package com.yryz.service.modules.id.exception;

public class BaseGenKeyException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public BaseGenKeyException() {
		super();
	}

	public BaseGenKeyException(String message) {
		super(message);
	}

	public BaseGenKeyException(String message, Throwable cause) {
		super(message, cause);
	}
}
