package com.example.demo.controller.data;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;

@Getter
@Setter
public class StandardError implements Serializable{
    @Serial
	private static final long serialVersionUID = 1L;
	
    private Instant timestamp;
    private Integer status;
    private String error;
    private String message;
    private String path;
}