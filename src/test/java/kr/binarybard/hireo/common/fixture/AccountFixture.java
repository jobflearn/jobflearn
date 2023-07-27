package kr.binarybard.hireo.common.fixture;

import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.util.ReflectionTestUtils;

import kr.binarybard.hireo.api.auth.dto.SignInRequest;
import kr.binarybard.hireo.api.auth.dto.SignUpRequest;
import kr.binarybard.hireo.web.account.domain.Account;
import kr.binarybard.hireo.web.account.domain.AccountType;
import kr.binarybard.hireo.web.account.domain.Personnel;
import kr.binarybard.hireo.web.account.dto.AccountResponse;

public class AccountFixture {
	public static final String TEST_EMAIL = "test@test.com";
	public static final String TEST_PASSWORD = "test123456";
	public static final String TEST_USERNAME = "testUser";
	public static final Long TEST_ID = 6L;

	public static final AccountResponse ACCOUNT_RESPONSE = AccountResponse.builder()
		.email(TEST_EMAIL)
		.companyBookmarks(List.of(BookmarkFixture.COMPANY_BOOKMARK_RESPONSE))
		.jobBookmarks(List.of(BookmarkFixture.JOB_BOOKMARK_RESPONSE))
		.build();

	public static final SignUpRequest SIGNUP_REQUEST_ACCOUNT = SignUpRequest.builder()
		.email(TEST_EMAIL)
		.name(TEST_USERNAME)
		.password(TEST_PASSWORD)
		.passwordConfirm(TEST_PASSWORD)
		.type(AccountType.PERSONNEL)
		.build();

	public static final SignInRequest SIGNIN_REQUEST_ACCOUNT = SignInRequest.builder()
		.email(TEST_EMAIL)
		.password(TEST_PASSWORD)
		.build();

	public static final User USER = new User(
		TEST_EMAIL,
		TEST_PASSWORD,
		List.of(new SimpleGrantedAuthority("USER"))
	);

	public static Account createAccount() {
		return Personnel.builder()
			.email(TEST_EMAIL)
			.password(TEST_PASSWORD)
			.name(TEST_USERNAME)
			.build();
	}

	public static Account createAccountWithId(Long id) {
		var Account = createAccount();
		ReflectionTestUtils.setField(Account, "id", id);
		return Account;
	}
}

