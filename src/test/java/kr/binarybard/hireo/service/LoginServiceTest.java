package kr.binarybard.hireo.service;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import kr.binarybard.hireo.auth.dto.SignUpRequest;
import kr.binarybard.hireo.auth.service.LoginService;
import kr.binarybard.hireo.member.service.MemberService;

@SpringBootTest
@Transactional
class LoginServiceTest {
	@Autowired
	LoginService loginService;
	@Autowired
	MemberService memberService;
	@Autowired
	PasswordEncoder passwordEncoder;

	@Test
	public void 로그인() throws Exception {
		//given
		String email = "123@naver.com";
		String password = "abc1234";
		SignUpRequest memberDto = getMemberDto(email, password, "gangmin", "FREELANCER");
		loginService.join(memberDto);
		//when
		SignUpRequest loginDto = new SignUpRequest();
		loginDto.setEmail(email);
		loginDto.setPassword(password);
		//then
		assertThat(loginService.isAuthenticated(loginDto)).isTrue();
	}

	public SignUpRequest getMemberDto(String email, String password, String name, String role) {
		SignUpRequest memberDto = new SignUpRequest();
		memberDto.setName(name);
		memberDto.setEmail(email);
		memberDto.setRole(role);
		memberDto.setPassword(password);
		return memberDto;
	}
}
