package kr.binarybard.hireo.common.exceptions;

public class AuthException extends BusinessException {
	public AuthException(ErrorCode errorCode) {
		super(errorCode);
	}

	public AuthException(ErrorCode errorCode, String detail) {
		super(errorCode, detail);
	}
}
