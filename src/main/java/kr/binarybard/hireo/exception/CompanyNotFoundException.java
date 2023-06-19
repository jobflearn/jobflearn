package kr.binarybard.hireo.exception;

import kr.binarybard.hireo.web.member.domain.Member;

public class CompanyNotFoundException extends RuntimeException {
	private static final String DEFAULT_MESSAGE = "회사가 존재하지 않습니다.";

	public CompanyNotFoundException() {
		super(DEFAULT_MESSAGE);
	}

	public CompanyNotFoundException(String companyName) {
		super(companyName + "은(는) 존재하지 않는 회사입니다.");
	}

	public CompanyNotFoundException(Member member) {
		super(member.getName() + "은(는) 소속된 회사가 없습니다.");
	}
}
