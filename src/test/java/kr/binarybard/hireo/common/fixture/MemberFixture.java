package kr.binarybard.hireo.common.fixture;

import kr.binarybard.hireo.api.auth.dto.SignInRequest;
import kr.binarybard.hireo.web.auth.dto.SignUpRequest;
import kr.binarybard.hireo.web.member.domain.Member;
import kr.binarybard.hireo.web.member.domain.Role;
import kr.binarybard.hireo.web.member.dto.MemberResponse;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

public class MemberFixture {
	public static final String TEST_EMAIL = "test@test.com";
	public static final String TEST_PASSWORD = "test123456";
	public static final String TEST_USERNAME = "testUser";
	public static final Role TEST_ROLE = Role.FREELANCER;

	public static final MemberResponse MEMBER_RESPONSE = MemberResponse.builder()
		.email(TEST_EMAIL)
		.companyBookmarks(List.of(BookmarkFixture.COMPANY_BOOKMARK_RESPONSE))
		.jobBookmarks(List.of(BookmarkFixture.JOB_BOOKMARK_RESPONSE))
		.build();

	public static final SignUpRequest SIGNUP_REQUEST_MEMBER = SignUpRequest.builder()
		.email(TEST_EMAIL)
		.name(TEST_USERNAME)
		.role(TEST_ROLE)
		.password(TEST_PASSWORD)
		.passwordConfirm(TEST_PASSWORD)
		.build();

	public static final SignInRequest SIGNIN_REQUEST_MEMBER = SignInRequest.builder()
		.email(TEST_EMAIL)
		.password(TEST_PASSWORD)
		.build();

	public static final User USER = new User(
		TEST_EMAIL,
		TEST_PASSWORD,
		List.of(new SimpleGrantedAuthority("USER"))
	);

	public static Member createMember() {
		return Member.builder()
			.email(TEST_EMAIL)
			.password(TEST_PASSWORD)
			.name(TEST_USERNAME)
			.role(TEST_ROLE)
			.build();
	}

	public static Member createMemberWithId(Long id) {
		var member = createMember();
		ReflectionTestUtils.setField(member, "id", id);
		return member;
	}
}

