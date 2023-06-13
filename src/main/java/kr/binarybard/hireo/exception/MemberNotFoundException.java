package kr.binarybard.hireo.exception;

public class MemberNotFoundException extends RuntimeException {
	
	private static final String DEFAULT_MESSAGE = "멤버가 존재하지 않습니다.";

	public MemberNotFoundException() {
		super(DEFAULT_MESSAGE);
	}

	public MemberNotFoundException(String username) {
		super(String.format(DEFAULT_MESSAGE + " (%s)", username));
	}
}
