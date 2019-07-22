package com.restapi.exception;

import static java.util.stream.Collectors.toList;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.restapi.error.ErrorResponse;
import com.restapi.exception.constant.ExceptionConstant;
import com.restapi.exception.util.ErrorMessageUtility;

@RestControllerAdvice
public class GlobalExceptionHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@Autowired
	private ErrorMessageUtility errorMessageUtility;

	@ExceptionHandler(NullPointerException.class)
	public ResponseEntity<ErrorResponse> handleException(NullPointerException exception) {
		LOGGER.info(ExceptionConstant.METHOD_ENTRY_POINT, System.currentTimeMillis());
		try {
			LOGGER.error(ExceptionConstant.EXCEPTION_RESOLVE, exception);
			return createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
					errorMessageUtility.getErrorCodeAndMessageString(ExceptionConstant.INTERNAL_SERVER_ERROR_MSG));
		} finally {
			LOGGER.info(ExceptionConstant.METHOD_EXIT_POINT, System.currentTimeMillis());
		}
	}

	/**
	 * @param exception
	 * @return response
	 */
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ErrorResponse> handleException(IllegalArgumentException exception) {
		LOGGER.info(ExceptionConstant.METHOD_ENTRY_POINT, System.currentTimeMillis());
		try {
			LOGGER.error(ExceptionConstant.EXCEPTION_RESOLVE, exception);
			return createErrorResponse(HttpStatus.BAD_REQUEST,
					errorMessageUtility.getErrorCodeAndMessageString(exception.getLocalizedMessage()));
		} finally {
			LOGGER.info(ExceptionConstant.METHOD_EXIT_POINT, System.currentTimeMillis());
		}
	}

	/**
	 * @param exception
	 * @return response
	 */
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<ErrorResponse> handleException(HttpRequestMethodNotSupportedException exception) {
		LOGGER.info(ExceptionConstant.METHOD_ENTRY_POINT, System.currentTimeMillis());
		try {
			LOGGER.error(ExceptionConstant.EXCEPTION_RESOLVE, exception);
			return createErrorResponse(HttpStatus.METHOD_NOT_ALLOWED,
					errorMessageUtility.getErrorCodeAndMessageString(exception.getLocalizedMessage()));
		} finally {
			LOGGER.info(ExceptionConstant.METHOD_EXIT_POINT, System.currentTimeMillis());
		}
	}

	/**
	 * @param exception
	 * @return response
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleException(MethodArgumentNotValidException exception) {
		LOGGER.info(ExceptionConstant.METHOD_ENTRY_POINT, System.currentTimeMillis());
		List<String> errorMessages = null;
		try {
			if (exception.getBindingResult().hasErrors()) {
				List<ObjectError> globalErrors = exception.getBindingResult().getAllErrors();
				errorMessages = globalErrors.stream().map(error -> {
					final String message = error.getDefaultMessage();
					return errorMessageUtility.getErrorCodeAndMessageString(message);
				}).collect(toList());
				LOGGER.info("MethodArgumentNotValidException messages [{}] ", errorMessages);
			}
			return createErrorResponse(HttpStatus.BAD_REQUEST, errorMessages);
		} finally {
			LOGGER.info(ExceptionConstant.METHOD_EXIT_POINT, System.currentTimeMillis());
		}
	}

	/**
	 * @param exception
	 * @return response
	 */
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<ErrorResponse> handleException(ConstraintViolationException exception) {
		LOGGER.info(ExceptionConstant.METHOD_ENTRY_POINT, System.currentTimeMillis());
		List<String> messages = null;
		try {
			LOGGER.error(ExceptionConstant.EXCEPTION_RESOLVE, exception);
			if (null != exception.getConstraintViolations() && !exception.getConstraintViolations().isEmpty()) {
				messages = exception.getConstraintViolations().stream().map(v -> {
					final String constraint = v.getConstraintDescriptor().getAnnotation().annotationType()
							.getSimpleName();
					LOGGER.debug("Exception constraint messages  [{}] ", constraint);
					return errorMessageUtility.getErrorCodeAndMessageString(constraint, v.getPropertyPath().toString(),
							v.getMessage());
				}).collect(toList());
				LOGGER.info("ConstraintViolationException messages [{}] ", messages);
			}
			return createErrorResponse(HttpStatus.BAD_REQUEST, messages);
		} finally {
			LOGGER.info(ExceptionConstant.METHOD_EXIT_POINT, System.currentTimeMillis());
		}
	}

	/**
	 * @param exception
	 * @return response
	 */
	@ExceptionHandler(PersonNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleException(PersonNotFoundException exception) {
		LOGGER.info(ExceptionConstant.METHOD_ENTRY_POINT, System.currentTimeMillis());
		try {
			LOGGER.error(ExceptionConstant.EXCEPTION_RESOLVE, exception);
			LOGGER.error(ExceptionConstant.EXCEPTION_RESOLVE, exception);
			return createErrorResponse(HttpStatus.BAD_REQUEST,
					errorMessageUtility.getErrorCodeAndMessageString(exception.getMessage()));
		} finally {
			LOGGER.info(ExceptionConstant.METHOD_EXIT_POINT, System.currentTimeMillis());
		}
	}

	private ResponseEntity<ErrorResponse> createErrorResponse(HttpStatus httpStatus, List<String> errorMessages) {
		LOGGER.info(ExceptionConstant.METHOD_ENTRY_POINT, System.currentTimeMillis());
		try {
			ErrorResponse error = new ErrorResponse();
			for (String msg : errorMessages) {
				error.setMessage(msg);
			}
			error.setTimestamp(LocalDateTime.now());
			error.setStatus(httpStatus);
			return ResponseEntity.status(httpStatus)
					.header(HttpHeaders.CONTENT_TYPE, ExceptionConstant.APPLICATION_ERROR_CONTENT_TYPE).body(error);
		} finally {
			LOGGER.info(ExceptionConstant.METHOD_EXIT_POINT, System.currentTimeMillis());
		}
	}

	private ResponseEntity<ErrorResponse> createErrorResponse(HttpStatus httpStatus, String message) {
		LOGGER.info(ExceptionConstant.METHOD_ENTRY_POINT, System.currentTimeMillis());
		try {
			ErrorResponse error = new ErrorResponse();
			error.setMessage(message);
			error.setTimestamp(LocalDateTime.now());
			error.setStatus(httpStatus);
			return ResponseEntity.status(httpStatus)
					.header(HttpHeaders.CONTENT_TYPE, ExceptionConstant.APPLICATION_ERROR_CONTENT_TYPE).body(error);
		} finally {
			LOGGER.info(ExceptionConstant.METHOD_EXIT_POINT, System.currentTimeMillis());
		}
	}
}
