package kr.binarybard.hireo.api.auth.controller;

import static kr.binarybard.hireo.common.fixture.AccountFixture.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;

import kr.binarybard.hireo.api.auth.dto.RefreshTokenRequest;
import kr.binarybard.hireo.api.auth.dto.SignInRequest;
import kr.binarybard.hireo.api.auth.dto.SignUpRequest;
import kr.binarybard.hireo.api.auth.repository.RefreshTokenRepository;
import kr.binarybard.hireo.common.AcceptanceTest;
import kr.binarybard.hireo.common.fixture.LoginFixture;
import kr.binarybard.hireo.web.account.domain.AccountType;
import kr.binarybard.hireo.web.account.repository.AccountRepository;
import kr.binarybard.hireo.web.account.service.AccountService;
import kr.binarybard.hireo.web.review.repository.ReviewRepository;

class AuthenticationApiControllerTest extends AcceptanceTest {

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private AccountService accountService;

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private ReviewRepository reviewRepository;

	@Autowired
	private RefreshTokenRepository refreshTokenRepository;

	private final String username = "testuser@test.com";
	private final String password = "testPassword123";

	@BeforeEach
	public void setUp() {
		refreshTokenRepository.deleteAll();
		reviewRepository.deleteAll();
		accountRepository.deleteAll();

		SignUpRequest signUpRequest = SignUpRequest.builder()
			.email(username)
			.password(password)
			.type(AccountType.PERSONNEL)
			.build();
		accountService.save(signUpRequest);
	}

	@DisplayName("로그인을 성공적으로 수행한다.")
	@Test
	void testAuthorize() throws Exception {
		SignInRequest signInRequest = SignInRequest.builder()
			.email(username)
			.password(password)
			.build();

		mockMvc.perform(post("/api/auth/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(signInRequest)))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.access_token").isNotEmpty())
			.andExpect(jsonPath("$.refresh_token").isNotEmpty());
	}

	@Test
	@DisplayName("잘못된 비밀번호로 로그인을 시도하면 실패한다.")
	void loginFailDueToWrongPassword() throws Exception {
		SignInRequest request = SignInRequest.builder()
			.email(username)
			.password(password + "wrong")
			.build();

		mockMvc.perform(post("/api/auth/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request)))
			.andExpect(status().isUnauthorized());
	}

	@Test
	@DisplayName("유효하지 않은 리프레시 토큰으로 새로운 토큰을 발급받을 수 없다.")
	void reissueFailDueToInvalidRefreshToken() throws Exception {
		RefreshTokenRequest request = RefreshTokenRequest.builder()
			.refreshToken("invalid_refresh_token")
			.build();

		mockMvc.perform(post("/api/auth/refresh")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request)))
			.andExpect(status().isUnauthorized());
	}

	@DisplayName("존재하지 않는 사용자로 로그인을 시도하면 실패한다.")
	@Test
	void testAuthorizeWithNonExistingUser() throws Exception {
		SignInRequest signInRequest = SignInRequest.builder()
			.email("non-existing@test.com")
			.password(password)
			.build();

		mockMvc.perform(post("/api/auth/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(signInRequest)))
			.andExpect(status().isUnauthorized());
	}

	@DisplayName("리프레시 토큰으로 새로운 토큰을 성공적으로 발급한다.")
	@Test
	void testReissue() throws Exception {
		SignInRequest signInRequest = SignInRequest.builder()
			.email(username)
			.password(password)
			.build();

		MvcResult result = mockMvc.perform(post("/api/auth/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(signInRequest)))
			.andReturn();

		String response = result.getResponse().getContentAsString();
		String refreshToken = JsonPath.read(response, "$.refresh_token");

		RefreshTokenRequest refreshTokenRequest = RefreshTokenRequest.builder()
			.refreshToken(refreshToken)
			.build();

		mockMvc.perform(post("/api/auth/refresh")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(refreshTokenRequest)))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.access_token").isNotEmpty())
			.andExpect(jsonPath("$.refresh_token").isNotEmpty());
	}

	private ResultActions performSignUpRequest(SignUpRequest signUpRequest) throws Exception {

		return mockMvc.perform(post("/api/auth/new")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(signUpRequest)))
			.andDo(print());
	}

	@Test
	@DisplayName("회원가입 요청")
	void registerAccountTest() throws Exception {
		SignUpRequest signUpRequest = LoginFixture.TEST_SIGNUP_REQUEST_JOBSEEKER;
		performSignUpRequest(signUpRequest)
			.andExpect(status().isCreated())
			.andExpect(redirectedUrl("/members/" + TEST_ID));
	}

	@Test
	@DisplayName("회원가입 요청 - 이메일 중복")
	void registerAccountWithDuplicateEmailTest() throws Exception {
		performSignUpRequest(LoginFixture.TEST_DUPLICATED_EMAIL)
			.andExpect(status().isBadRequest());
	}

	@Test
	@DisplayName("회원가입 요청 - 유효하지 않은 비밀번호")
	void registerAccountWithBindingResultError() throws Exception {
		SignUpRequest invalidRequest = SignUpRequest.builder()
			.email("freelancer@test.com")
			.password("password123")
			.passwordConfirm("password456")
			.name("freelancerUser")
			.build();

		performSignUpRequest(invalidRequest)
			.andExpect(status().isBadRequest());
	}
}
