package com.example.demo.controller.data;

import java.io.Serial;
import java.io.Serializable;

public record FieldMessage(String fieldName, String message) implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;
}