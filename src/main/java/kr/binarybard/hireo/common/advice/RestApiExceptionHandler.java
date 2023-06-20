package kr.binarybard.hireo.common.advice;

import kr.binarybard.hireo.common.dto.ErrorResponse;
import kr.binarybard.hireo.common.exceptions.BusinessException;
import kr.binarybard.hireo.common.exceptions.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(annotations = RestController.class, basePackages = "kr.binarybard.hireo.api")
public class RestApiExceptionHandler {
	@ExceptionHandler(MethodArgumentNotValidException.class)
	protected ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
		var response = ErrorResponse.of(ErrorCode.INVALID_INPUT_VALUE, e.getBindingResult());
		return new ResponseEntity<>(response, response.getStatus());
	}

	@ExceptionHandler(BusinessException.class)
	protected ResponseEntity<ErrorResponse> handleBusinessException(BusinessException e) {
		var response = ErrorResponse.of(e.getErrorCode());
		return new ResponseEntity<>(response, response.getStatus());
	}

	@ExceptionHandler(AuthenticationException.class)
	protected ResponseEntity<ErrorResponse> handleAuthenticationException(AuthenticationException e) {
		var response = ErrorResponse.of(ErrorCode.AUTHENTICATION_FAILED);
		log.warn(e.getMessage(), e);
		return new ResponseEntity<>(response, response.getStatus());
	}

	@ExceptionHandler(Exception.class)
	protected ResponseEntity<ErrorResponse> handleApiException(Exception e) {
		var response = ErrorResponse.of(ErrorCode.INTERNAL_SERVER_ERROR);
		log.error(e.getMessage(), e);
		return new ResponseEntity<>(response, response.getStatus());
	}
}
