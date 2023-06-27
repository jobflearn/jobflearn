package kr.binarybard.hireo.api.auth.service;

import kr.binarybard.hireo.api.auth.dto.SignInRequest;
import kr.binarybard.hireo.api.auth.dto.TokenResponse;
import kr.binarybard.hireo.common.fixture.MemberFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JwtLoginServiceTest {

	@Mock
	private AuthenticationManagerBuilder authenticationManagerBuilder;

	@Mock
	private JwtTokenService tokenService;

	@Mock
	private AuthenticationManager authenticationManager;

	@Mock
	private Authentication authentication;

	@InjectMocks
	private JwtLoginService jwtLoginService;

	@Test
	@DisplayName("사용자 인증 후 JWT 토큰을 생성한다.")
	void authenticateUserTest() {
		// given
		String testAccessToken = "testAccessToken";
		String testRefreshToken = "testRefreshToken";
		SignInRequest request = MemberFixture.SIGNIN_REQUEST_MEMBER;

		when(authenticationManagerBuilder.getObject()).thenReturn(authenticationManager);
		when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
			.thenReturn(authentication);
		when(tokenService.generateJwtTokens(anyString(), any(Authentication.class)))
			.thenReturn(new TokenResponse(testAccessToken, testRefreshToken));

		// when
		TokenResponse response = jwtLoginService.authenticateUser(request);

		// then
		assertThat(response.accessToken()).isEqualTo(testAccessToken);
		assertThat(response.refreshToken()).isEqualTo(testRefreshToken);
	}
}

