
package com.restapi.exception;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.restapi.error.ErrorResponse;
import com.restapi.exception.constant.ExceptionConstant;

/**
 * @author 503118564
 */
@RestController
@RequestMapping("${server.error.path:${error.path:/error}}")
public class CustomErrorController extends AbstractErrorController {
	private static final Logger LOGGER = LoggerFactory.getLogger(CustomErrorController.class);

	public CustomErrorController(ErrorAttributes errorAttributes) {
		super(errorAttributes);
	}

	@Override
	public String getErrorPath() {
		return "/error";
	}

	/**
	 * This method used to handle incorrect in coming URL's request.
	 * 
	 * @param request
	 * @return ResponseEntity<ApiErrorResponse>
	 */
	@SuppressWarnings("unchecked")
	@GetMapping
	@ResponseBody
	public ResponseEntity<ErrorResponse> handleError(HttpServletRequest request) {
		LOGGER.info(ExceptionConstant.CUSTOM_METHOD_ENTRY_POINT, System.currentTimeMillis());
		ErrorResponse errorResponse;
		try {
			errorResponse = new ErrorResponse();
			Map<String, Object> errors = getErrorAttributes(request, false);
			LOGGER.error("Received errors [{}]", errors);
			String message = (String) errors.getOrDefault("message", "Error");
			List<FieldError> errorList = (List<FieldError>) errors.getOrDefault("errors", null);
			if (errorList != null) {
				FieldError fieldError = errorList.get(0);
				message = fieldError.getDefaultMessage();
			}
			LOGGER.error("message {}", message);
			// handle the message
			HttpStatus status = getStatus(request);
			errorResponse.setMessage(message);
			errorResponse.setStatus(status);
			errorResponse.setTimestamp(LocalDateTime.now());
			return ResponseEntity.status(status)
					.header(HttpHeaders.CONTENT_TYPE, ExceptionConstant.APPLICATION_ERROR_CONTENT_TYPE)
					.body(errorResponse);
		} finally {
			LOGGER.info(ExceptionConstant.CUSTOM_METHOD_EXIT_POINT, System.currentTimeMillis());
		}
	}
}
