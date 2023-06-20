package kr.binarybard.hireo.api.common.exceptions;

public class AuthException extends BusinessException {
	public AuthException(ErrorCode errorCode) {
		super(errorCode);
	}
}
