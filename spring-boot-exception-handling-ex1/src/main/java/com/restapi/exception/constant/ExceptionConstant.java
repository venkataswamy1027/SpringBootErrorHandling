package com.restapi.exception.constant;

public class ExceptionConstant {
	public static final String APPLICATION_ERROR_CONTENT_TYPE = "application/json";
	public static final String METHOD_ENTRY_POINT = "Entering into handleException method at {}";
	public static final String METHOD_EXIT_POINT = "Exiting on handleException method at {}";
	public static final String EXCEPTION_RESOLVE = "Exception Resolved {}";

	public static final String ERROR_METHOD_ENTRY_POINT = "Entering into createErrorResponse method at {}";
	public static final String ERROR_METHOD_EXIT_POINT = "Exiting on createErrorResponse method at {}";

	public static final String CUSTOM_ERROR_METHOD_ENTRY_POINT = "Entering into generateCustomErrorCodeAndMessage method at {}";
	public static final String CUSTOM_ERROR_METHOD_EXIT_POINT = "Exiting on generateCustomErrorCodeAndMessage method at {}";

	public static final String CUSTOM_METHOD_ENTRY_POINT = "Entering into handleError method at {}";
	public static final String CUSTOM_METHOD_EXIT_POINT = "Exiting on handleError method at {}";
	public static final String FINAL_ERROR_RESPONSE = "finalError {}";
	
	public static final String INTERNAL_SERVER_ERROR_MSG = "Internal Server Error. Please contact Admin.";
}
