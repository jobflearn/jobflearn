package kr.binarybard.hireo.common.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

	DUPLICATED_EMAIL(HttpStatus.BAD_REQUEST, "AU_001", "이미 사용중인 이메일입니다."),
	AUTHENTICATION_FAILED(HttpStatus.UNAUTHORIZED, "AU_002", "이메일 또는 비밀번호가 일치하지 않습니다."),

	MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "M_001", "회원을 찾을 수 없습니다."),

	COMPANY_NOT_FOUND(HttpStatus.NOT_FOUND, "C_001", "회사를 찾을 수 없습니다."),

	INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "A_001", "유효하지 않은 토큰입니다."),
	UNSUPPORTED_OAUTH2_PROVIDER(HttpStatus.BAD_REQUEST, "A_002", "지원하지 않는 OAuth2 프로바이더입니다."),

	INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "S_001", "서버에 오류가 발생하였습니다."),
	INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "S_002", "잘못된 요청 값입니다."),
	TOKEN_HASHING_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "S_003", "토큰 해싱 중 오류가 발생하였습니다.");

	private final HttpStatus status;
	private final String code;
	private final String message;

	ErrorCode(HttpStatus status, String code, String message) {
		this.status = status;
		this.code = code;
		this.message = message;
	}
}
