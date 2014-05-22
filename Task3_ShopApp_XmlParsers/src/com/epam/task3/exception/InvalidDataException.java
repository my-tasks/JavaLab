package com.epam.task3.exception;

import java.util.ArrayList;
import java.util.List;

public final class InvalidDataException extends RuntimeException {
	private List<String> errors = new ArrayList<>();

	public InvalidDataException() {
	}

	public InvalidDataException(String error) {
		super(error);
		errors.add(error);
	}

	public InvalidDataException(List<String> errors) {
		this.errors = errors;
	}

	public List<String> getErrors() {
		return errors;
	}
}
