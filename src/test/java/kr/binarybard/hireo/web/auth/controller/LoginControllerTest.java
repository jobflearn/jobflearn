package kr.binarybard.hireo.web.auth.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import kr.binarybard.hireo.common.AcceptanceTest;
import kr.binarybard.hireo.common.exceptions.AuthenticationException;
import kr.binarybard.hireo.common.exceptions.ErrorCode;
import kr.binarybard.hireo.common.fixture.LoginFixture;
import kr.binarybard.hireo.web.auth.dto.SignUpRequest;
import kr.binarybard.hireo.web.member.domain.Role;
import kr.binarybard.hireo.web.member.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

class LoginControllerTest extends AcceptanceTest {

	@MockBean
	private MemberService memberService;

	private ResultActions performSignUpRequest(SignUpRequest signUpRequest) throws Exception {
		return mockMvc.perform(post("/auth/new")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("email", signUpRequest.getEmail())
				.param("password", signUpRequest.getPassword())
				.param("passwordConfirm", signUpRequest.getPasswordConfirm())
				.param("name", signUpRequest.getName())
				.param("role", signUpRequest.getRole().toString()))
			.andDo(print());
	}

	@Test
	@DisplayName("회원가입 요청")
	void registerMemberTest() throws Exception {
		SignUpRequest signUpRequest = LoginFixture.TEST_SIGNUP_REQUEST_FREELANCER;

		when(memberService.save(signUpRequest)).thenReturn(1L);

		performSignUpRequest(signUpRequest)
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/auth/login"));
	}

	@Test
	@DisplayName("회원가입 폼 요청")
	void registerFormTest() throws Exception {
		mockMvc.perform(get("/auth/new"))
			.andExpect(status().isOk())
			.andExpect(view().name("new"))
			.andExpect(model().attributeExists("member"));
	}

	@Test
	@DisplayName("회원가입 요청 - 이메일 중복")
	void registerMemberWithDuplicateEmailTest() throws Exception {
		when(memberService.save(any())).thenThrow(new AuthenticationException(ErrorCode.DUPLICATED_EMAIL));

		performSignUpRequest(LoginFixture.TEST_SIGNUP_REQUEST_FREELANCER)
			.andExpect(status().isOk())
			.andExpect(view().name("new"));
	}

	@Test
	@DisplayName("회원가입 요청 - BindingResult 에러")
	void registerMemberWithBindingResultError() throws Exception {
		SignUpRequest invalidRequest = SignUpRequest.builder()
			.email("freelancer@test.com")
			.password("password123")
			.passwordConfirm("password456")
			.name("freelancerUser")
			.role(Role.FREELANCER)
			.build();

		performSignUpRequest(invalidRequest)
			.andExpect(status().isOk())
			.andExpect(view().name("new"));
	}

	@Test
	@DisplayName("로그인 폼 요청")
	void loginFormTest() throws Exception {
		mockMvc.perform(get("/auth/login"))
			.andExpect(status().isOk())
			.andExpect(view().name("login"));
	}
}
