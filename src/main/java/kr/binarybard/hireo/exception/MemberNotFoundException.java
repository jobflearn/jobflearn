package kr.binarybard.hireo.exception;

public class MemberNotFoundException extends RuntimeException {

	private static final String DEFAULT_MESSAGE = "멤버가 존재하지 않습니다.";

	public MemberNotFoundException() {
		super(DEFAULT_MESSAGE);
	}

	public MemberNotFoundException(String username) {
		super(username + "은(는) 존재하지 않는 멤버입니다.");
	}
}
