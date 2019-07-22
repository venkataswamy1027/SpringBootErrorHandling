package com.restapi.error;

import lombok.Data;

@Data
public class ItemErrorResponse {

	private int status;
	private String message;
	private long timeStamp;

}
