package kr.binarybard.hireo.web.auth.controller;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import kr.binarybard.hireo.common.AcceptanceTest;
import kr.binarybard.hireo.common.exceptions.AuthenticationException;
import kr.binarybard.hireo.common.exceptions.ErrorCode;
import kr.binarybard.hireo.common.fixture.LoginFixture;
import kr.binarybard.hireo.web.account.service.AccountService;
import kr.binarybard.hireo.web.auth.dto.SignUpRequest;

class LoginControllerTest extends AcceptanceTest {

	@MockBean
	private AccountService accountService;

	private ResultActions performSignUpRequest(SignUpRequest signUpRequest) throws Exception {
		return mockMvc.perform(post("/auth/new")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("email", signUpRequest.getEmail())
				.param("password", signUpRequest.getPassword())
				.param("passwordConfirm", signUpRequest.getPasswordConfirm())
				.param("name", signUpRequest.getName()))
			.andDo(print());
	}

	@Test
	@DisplayName("회원가입 요청")
	void registerAccountTest() throws Exception {
		SignUpRequest signUpRequest = LoginFixture.TEST_SIGNUP_REQUEST_JOBSEEKER;

		when(accountService.save(signUpRequest)).thenReturn(1L);

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
			.andExpect(model().attributeExists("account"));
	}

	@Test
	@DisplayName("회원가입 요청 - 이메일 중복")
	void registerAccountWithDuplicateEmailTest() throws Exception {
		when(accountService.save(any())).thenThrow(new AuthenticationException(ErrorCode.DUPLICATED_EMAIL));

		performSignUpRequest(LoginFixture.TEST_SIGNUP_REQUEST_JOBSEEKER)
			.andExpect(status().isOk())
			.andExpect(view().name("new"));
	}

	@Test
	@DisplayName("회원가입 요청 - BindingResult 에러")
	void registerAccountWithBindingResultError() throws Exception {
		SignUpRequest invalidRequest = SignUpRequest.builder()
			.email("freelancer@test.com")
			.password("password123")
			.passwordConfirm("password456")
			.name("freelancerUser")
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
