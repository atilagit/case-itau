package com.example.demo.controller.data;

import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
public class ValidationError extends StandardError implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;
	
	private final List<FieldMessage> errors = new ArrayList<>();

	public void addError(String fieldName, String message) {
		errors.add(new FieldMessage(fieldName, message));
	}
	public void addError(String message) {
		errors.add(new FieldMessage(null, message));
	}
}