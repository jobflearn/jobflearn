package kr.binarybard.hireo.common.exceptions;

public class AuthorizationException extends BusinessException {

	public AuthorizationException(ErrorCode errorCode, String detail) {
		super(errorCode, detail);
	}

	public AuthorizationException(ErrorCode errorCode) {
		super(errorCode);
	}
}
