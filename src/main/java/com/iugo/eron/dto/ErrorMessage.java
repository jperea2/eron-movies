package com.iugo.eron.dto;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class ErrorMessage {

	private int status;
	private String error;
	
	public ErrorMessage(HttpStatus status, String error) {
		this.status = status.value();
		this.error = error;
	}
}
