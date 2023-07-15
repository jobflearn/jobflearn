package kr.binarybard.hireo.web.account.domain;

import lombok.Getter;

@Getter
public enum AccountType {
	PERSONNEL("기업회원"), EMPLOYEE("사원"), JOBSEEKER("개인회원");

	private final String description;

	AccountType(String description) {
		this.description = description;
	}
}
