package com.epam.xslt.exception;

public class ConfigurationException extends RuntimeException {

	public ConfigurationException() {
		super();
	}

	public ConfigurationException(String error) {
		super(error);
	}

	public ConfigurationException(String error, Throwable cause) {
		super(error, cause);
	}
}
