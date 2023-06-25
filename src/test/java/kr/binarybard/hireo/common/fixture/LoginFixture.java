package kr.binarybard.hireo.common.fixture;

import kr.binarybard.hireo.web.auth.dto.SignUpRequest;
import kr.binarybard.hireo.web.member.domain.Role;

public class LoginFixture {
	public static final SignUpRequest TEST_SIGNUP_REQUEST_FREELANCER = SignUpRequest.builder()
		.email("freelancer@test.com")
		.password("password123")
		.passwordConfirm("password123")
		.name("freelancerUser")
		.role(Role.FREELANCER)
		.build();

	public static final SignUpRequest TEST_SIGNUP_REQUEST_EMPLOYER = SignUpRequest.builder()
		.email("employer@test.com")
		.password("password123")
		.passwordConfirm("password123")
		.name("employerUser")
		.role(Role.EMPLOYER)
		.build();

	public static final SignUpRequest TEST_SIGNUP_REQUEST_ADMINISTRATOR = SignUpRequest.builder()
		.email("admin@test.com")
		.password("password123")
		.passwordConfirm("password123")
		.name("adminUser")
		.role(Role.ADMINISTRATOR)
		.build();
}
