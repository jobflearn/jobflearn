package kr.binarybard.hireo.common.fixture;

import kr.binarybard.hireo.web.account.domain.AccountType;
import kr.binarybard.hireo.web.auth.dto.SignUpRequest;

public class LoginFixture {
	public static final SignUpRequest TEST_SIGNUP_REQUEST_JOBSEEKER = SignUpRequest.builder()
		.email("freelancer@test.com")
		.password("password123")
		.passwordConfirm("password123")
		.name("freelancerUser")
		.type(AccountType.PERSONNEL)
		.build();

	public static final SignUpRequest TEST_SIGNUP_REQUEST_EMPLOYER = SignUpRequest.builder()
		.email("employer@test.com")
		.password("password123")
		.passwordConfirm("password123")
		.name("employerUser")
		.type(AccountType.PERSONNEL)
		.build();

	public static final SignUpRequest TEST_SIGNUP_REQUEST_ADMINISTRATOR = SignUpRequest.builder()
		.email("admin@test.com")
		.password("password123")
		.passwordConfirm("password123")
		.name("adminUser")
		.type(AccountType.PERSONNEL)
		.build();
}
