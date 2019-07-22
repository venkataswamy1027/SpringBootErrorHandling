
package com.restapi.exception.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ErrorMessageUtility {
	private static final Logger LOGGER = LoggerFactory.getLogger(ErrorMessageUtility.class);
	private static final String CODE_SEPERATOR = ".";

	/**
	 * @param code
	 * @return code
	 */
	public String getErrorCodeAndMessageString(String message) {
		LOGGER.info("message {}", message);
		return message;
	}

	/**
	 * @param constraint
	 * @param fieldName
	 * @param message
	 * @return
	 */
	public String getErrorCodeAndMessageString(String constraint, String fieldName, String message) {
		LOGGER.info("constraint {},fieldName {},message {}", constraint, fieldName, message);
		final String code = constraint + CODE_SEPERATOR + fieldName;
		return getErrorCodeAndMessageString(code);
	}

}
