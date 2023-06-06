package kr.binarybard.hireo.service;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import kr.binarybard.hireo.domain.MemberDto;

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
		MemberDto memberDto = getMemberDto(email, password, "gangmin", "FREELANCER");
		memberService.join(memberDto);
		//when
		MemberDto loginDto = new MemberDto();
		loginDto.setEmail(email);
		loginDto.setPassword(password);
		//then
		System.out.println(passwordEncoder.matches(password, memberService.findMemberByEmail(email).getPassword()));
		assertThat(loginService.login(loginDto)).isTrue();

		//Assertions.assertThrows(IllegalStateException.class, ()->loginService.login(loginDto))
	}

	public MemberDto getMemberDto(String email, String password, String name, String role) {
		MemberDto memberDto = new MemberDto();
		memberDto.setName(name);
		memberDto.setEmail(email);
		memberDto.setRole(role);
		memberDto.setPassword(password);
		return memberDto;
	}
}
