package kr.binarybard.hireo.common.exceptions;

public class EntityNotFoundException extends BusinessException {
	public EntityNotFoundException(ErrorCode errorCode) {
		super(errorCode);
	}

	public EntityNotFoundException(ErrorCode errorCode, String detail) {
		super(errorCode, detail);
	}
}
