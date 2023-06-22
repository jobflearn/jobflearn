package kr.binarybard.hireo.common.exceptions;

public class InvalidValueException extends BusinessException {
	public InvalidValueException(ErrorCode errorCode) {
		super(errorCode);
	}

	public InvalidValueException(ErrorCode errorCode, String detail) {
		super(errorCode, detail);
	}
}
