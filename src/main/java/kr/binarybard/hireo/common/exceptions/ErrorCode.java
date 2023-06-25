package kr.binarybard.hireo.common.exceptions;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum ErrorCode {

	DUPLICATED_EMAIL(HttpStatus.BAD_REQUEST, "AU_001", "이미 사용중인 이메일입니다."),
	AUTHENTICATION_FAILED(HttpStatus.UNAUTHORIZED, "AU_002", "이메일 또는 비밀번호가 일치하지 않습니다."),

	MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "M_001", "회원을 찾을 수 없습니다."),

	COMPANY_NOT_FOUND(HttpStatus.NOT_FOUND, "C_001", "회사를 찾을 수 없습니다."),

	LOCATION_NOT_FOUND(HttpStatus.NOT_FOUND, "L_001", "주소가 존재하지 않습니다."),

	COMPANY_BOOKMARK_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "B_001", "이미 북마크한 회사입니다."),
	COMPANY_BOOKMARK_NOT_FOUND(HttpStatus.NOT_FOUND, "B_002", "북마크한 회사를 찾을 수 없습니다."),

	INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "A_001", "유효하지 않은 토큰입니다."),
	UNSUPPORTED_OAUTH2_PROVIDER(HttpStatus.BAD_REQUEST, "A_002", "지원하지 않는 OAuth2 프로바이더입니다."),

	FILE_UPLOAD_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "F_001", "파일 업로드 중 오류가 발생하였습니다."),
	FILE_HASHING_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "F_002", "파일 해싱 중 오류가 발생하였습니다."),
	FILE_READING_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "F_003", "파일 읽기 중 오류가 발생하였습니다."),
	FILE_STORAGE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "F_004", "파일 저장 중 오류가 발생하였습니다."),
	FILE_NOT_FOUND(HttpStatus.NOT_FOUND, "F_005", "파일을 찾을 수 없습니다."),
	FILE_SIZE_TOO_LARGE(HttpStatus.BAD_REQUEST, "F_006", "파일 크기가 너무 큽니다."),
	FILE_SIZE_ZERO(HttpStatus.BAD_REQUEST, "F_007", "파일 크기가 0입니다."),

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
