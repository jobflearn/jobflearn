package kr.binarybard.hireo.api.docs;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.snippet.Attributes.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.binarybard.hireo.api.auth.dto.RefreshTokenRequest;
import kr.binarybard.hireo.api.auth.dto.SignInRequest;
import kr.binarybard.hireo.api.auth.dto.SignUpRequest;
import kr.binarybard.hireo.api.auth.service.LoginService;
import kr.binarybard.hireo.api.auth.service.RefreshTokenService;
import kr.binarybard.hireo.config.jwt.JwtTokenProvider;
import kr.binarybard.hireo.web.account.domain.AccountType;

@Transactional
class AuthenticationApiControllerTest extends RestDocsConfiguration {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private LoginService loginService;

	@MockBean
	private JwtTokenProvider jwtTokenProvider;

	@MockBean
	private AuthenticationManagerBuilder authenticationManagerBuilder;

	@Mock
	private AuthenticationManager authenticationManager;

	@MockBean
	private RefreshTokenService refreshTokenService;

	private final String username = "test@example.com";
	private final String password = "password1234";

	@BeforeEach
	void setup() {
		when(jwtTokenProvider.createAccessToken(any())).thenReturn("valid-access-token");
		when(jwtTokenProvider.createRefreshToken(any())).thenReturn("valid-refresh-token");
		when(refreshTokenService.validateToken(anyString())).thenReturn(true);
		when(jwtTokenProvider.getUsernameFromToken(anyString())).thenReturn(username);
		when(loginService.loadUserByUsername(anyString())).thenReturn(new User(username, password, new ArrayList<>()));
		when(jwtTokenProvider.getAuthentication(anyString())).thenReturn(
			new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>()));
		when(authenticationManagerBuilder.getObject()).thenReturn(authenticationManager);
	}

	@Test
	@DisplayName("로그인 성공")
	void loginSuccess() throws Exception {
		// Arrange
		SignInRequest signInRequest = SignInRequest.builder()
			.email(username)
			.password(password)
			.build();

		// Act & Assert
		mockMvc.perform(post("/api/auth/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(signInRequest)))
			.andExpect(status().isOk())
			.andDo(document("login",
				requestFields(
					fieldWithPath("email").description("로그인 이메일"),
					fieldWithPath("password").description("로그인 비밀번호")
				),
				responseFields(
					fieldWithPath("access_token").description("액세스 토큰"),
					fieldWithPath("refresh_token").description("리프레시 토큰")
				)
			));
	}

	@Test
	@DisplayName("토큰 재발급 성공")
	void reissueTokenSuccess() throws Exception {
		// Arrange
		RefreshTokenRequest refreshTokenRequest = RefreshTokenRequest.builder()
			.refreshToken("refresh_token")
			.build();

		// Act & Assert
		mockMvc.perform(post("/api/auth/refresh")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(refreshTokenRequest)))
			.andExpect(status().isOk())
			.andDo(document("reissue-token",
				requestFields(
					fieldWithPath("refresh_token").description("재발급 요청 토큰")
				),
				responseFields(
					fieldWithPath("access_token").description("액세스 토큰"),
					fieldWithPath("refresh_token").description("리프레시 토큰")
				)
			));
	}

	@Test
	@DisplayName("회원가입 성공")
	void registerAccountTest() throws Exception {
		//Arrange
		SignUpRequest signUpRequest = SignUpRequest.builder()
			.email("freelancer@test.com")
			.password("password123")
			.passwordConfirm("password123")
			.type(AccountType.JOBSEEKER)
			.name("freelancerUser")
			.build();
		//Act&Assert
		mockMvc.perform(post("/api/auth/new")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(signUpRequest)))
			.andDo(print())
			.andExpect(status().isCreated())
			.andDo(document("create-account",
				requestFields(
					fieldWithPath("email").description("이메일"),
					fieldWithPath("password").description("비밀번호"),
					fieldWithPath("passwordConfirm").description("비밀번호 확인")
						.attributes(key("constraint").value("비밀번호는 비밀번호 확인의 값과 항상 같아야 합니다.")),
					fieldWithPath("type").description("회원 타입"),
					fieldWithPath("name").description("회원 이름").optional()
				)
			));

	}
}
