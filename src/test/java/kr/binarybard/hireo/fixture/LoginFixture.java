package kr.binarybard.hireo.fixture;

import kr.binarybard.hireo.web.auth.dto.SignUpRequest;
import kr.binarybard.hireo.web.member.domain.Role;

public class LoginFixture {
	public static final SignUpRequest TEST_SIGNUP_REQUEST = SignUpRequest.builder()
		.email("test@test.com")
		.password("password123")
		.passwordConfirm("password123")
		.name("testUser")
		.role(Role.FREELANCER)
		.build();
}
