package kr.binarybard.hireo.common.exceptions;

public class FileProcessingException extends BusinessException {

	public FileProcessingException(ErrorCode errorCode) {
		super(errorCode);
	}

	public FileProcessingException(ErrorCode errorCode, String detail) {
		super(errorCode, detail);
	}
}
