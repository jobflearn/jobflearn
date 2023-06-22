package kr.binarybard.hireo.web.fixture;

import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import kr.binarybard.hireo.web.member.domain.Member;
import kr.binarybard.hireo.web.member.domain.Role;

public class MemberFixture {
	public static final long EXISTING_MEMBER_ID = 1L;
	public static final Member TEST_MEMBER = Member.builder()
		.email("test@test.com")
		.password("1234")
		.name("testUser")
		.role(Role.FREELANCER)
		.build();

	public static final User TEST_USER = new User(
		TEST_MEMBER.getEmail(),
		TEST_MEMBER.getPassword(),
		List.of(new SimpleGrantedAuthority("USER"))
	);
}
