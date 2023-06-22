package kr.binarybard.hireo.exception;

public class LocationNotFoundException extends RuntimeException {
	private static final String DEFAULT_MESSAGE = "주소가 존재하지 않습니다.";

	public LocationNotFoundException() {
		super(DEFAULT_MESSAGE);
	}

}
