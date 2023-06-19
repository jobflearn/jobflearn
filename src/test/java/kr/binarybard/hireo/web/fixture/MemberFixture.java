package kr.binarybard.hireo.fixture;

import kr.binarybard.hireo.web.member.domain.Member;
import kr.binarybard.hireo.web.member.domain.Role;

public class MemberFixture {
	public static final Member TEST_MEMBER = Member.builder()
		.email("test@test.com")
		.password("1234")
		.name("testUser")
		.role(Role.FREELANCER)
		.build();
}
