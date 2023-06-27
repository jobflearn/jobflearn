package kr.binarybard.hireo.common.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import kr.binarybard.hireo.common.exceptions.ErrorCode;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ErrorResponse {
	private String message;
	private String code;
	private List<FieldError> errors;

	@JsonIgnore
	private HttpStatus status;

	private ErrorResponse(ErrorCode code) {
		this(code, null);
	}

	private ErrorResponse(ErrorCode code, List<FieldError> errors) {
		this.code = code.getCode();
		this.message = code.getMessage();
		this.status = code.getStatus();
		this.errors = errors != null ? new ArrayList<>(errors) : Collections.emptyList();
	}

	public static ErrorResponse of(ErrorCode code) {
		return new ErrorResponse(code);
	}

	public static ErrorResponse of(ErrorCode code, @NonNull BindingResult bindingResult) {
		return new ErrorResponse(code, FieldError.of(bindingResult));
	}

	@Getter
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public static class FieldError {
		private String field;
		private String value;
		private String reason;

		@Builder
		private FieldError(String field, String value, String reason) {
			this.field = field;
			this.value = value;
			this.reason = reason;
		}

		private static List<FieldError> of(BindingResult bindingResult) {
			List<org.springframework.validation.FieldError> fieldErrors = bindingResult.getFieldErrors();
			return fieldErrors.stream()
				.map(error -> FieldError.builder()
					.field(error.getField())
					.value(error.getRejectedValue() == null ? "" : error.getRejectedValue().toString())
					.reason(error.getDefaultMessage()).build())
				.toList();
		}
	}
}
