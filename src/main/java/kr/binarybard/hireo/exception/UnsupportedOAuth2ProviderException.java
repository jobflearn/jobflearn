package kr.binarybard.hireo.exception;

public class UnsupportedOAuth2ProviderException extends RuntimeException {

	private static final String MESSAGE = "지원하지 않는 OAuth2 프로바이더입니다.";

	public UnsupportedOAuth2ProviderException(String provider) {
		super(MESSAGE + " (" + provider + ")");
	}
}
