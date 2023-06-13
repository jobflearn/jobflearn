package kr.binarybard.hireo.exception;

public class DuplicateEmailException extends RuntimeException {

	private static final String DEFAULT_MESSAGE = "이미 존재하는 이메일입니다.";

	public DuplicateEmailException() {
		super(DEFAULT_MESSAGE);
	}

	public DuplicateEmailException(String email) {
		super(email + "은(는) 이미 존재하는 이메일입니다.");
	}
}
