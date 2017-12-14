package com.yryz.service.modules.id.exception;

public class GenPrimaryKeyException extends BaseGenKeyException {

	private static final long serialVersionUID = 1L;

	public GenPrimaryKeyException() {
		super();
	}

	public GenPrimaryKeyException(String message) {
		super(message);
	}

	public GenPrimaryKeyException(String message, Throwable cause) {
		super(message, cause);
	}
}
