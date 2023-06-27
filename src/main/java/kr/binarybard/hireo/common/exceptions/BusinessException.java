package kr.binarybard.hireo.common.exceptions;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {
	private final String detail;
	private final ErrorCode errorCode;

	public BusinessException(ErrorCode errorCode, String detail) {
		super(errorCode.getMessage() + (detail == null ? "" : " (" + detail + ")"));
		this.errorCode = errorCode;
		this.detail = detail;
	}

	public BusinessException(ErrorCode errorCode) {
		this(errorCode, null);
	}
}
