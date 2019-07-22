package com.restapi.error.response;

import lombok.Data;

@Data
public class ExceptionResponse {
	private String errorMessage;
	private String requestedURI;
}
