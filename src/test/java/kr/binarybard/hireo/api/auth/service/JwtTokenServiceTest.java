package kr.binarybard.hireo.api.auth.service;

import kr.binarybard.hireo.api.auth.dto.RefreshTokenRequest;
import kr.binarybard.hireo.api.auth.dto.TokenResponse;
import kr.binarybard.hireo.config.jwt.JwtTokenProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JwtTokenServiceTest {

	@Mock
	private JwtTokenProvider tokenProvider;

	@Mock
	private RefreshTokenService refreshTokenService;

	@Mock
	private Authentication authentication;

	@InjectMocks
	private JwtTokenService jwtTokenService;

	@Test
	@DisplayName("JWT 토큰을 새로 발급한다.")
	void refreshJwtTokensTest() {
		// given
		String testRefreshToken = "testRefreshToken";
		String testUsername = "testUsername";
		RefreshTokenRequest request = RefreshTokenRequest.builder()
				.refreshToken(testRefreshToken)
				.build();

		when(tokenProvider.getAuthentication(testRefreshToken)).thenReturn(authentication);
		when(authentication.getName()).thenReturn(testUsername);
		when(refreshTokenService.validateToken(anyString())).thenReturn(true);
		when(tokenProvider.createAccessToken(authentication)).thenReturn(testRefreshToken);
		when(tokenProvider.createRefreshToken(authentication)).thenReturn(testRefreshToken);
		when(refreshTokenService.save(anyString(), anyString())).thenReturn(1L);

		// when
		TokenResponse response = jwtTokenService.refreshJwtTokens(request);

		// then
		assertThat(response.accessToken()).isEqualTo(testRefreshToken);
		assertThat(response.refreshToken()).isEqualTo(testRefreshToken);
	}

	@Test
	@DisplayName("JWT 토큰을 새로 생성한다.")
	void generateJwtTokensTest() {
		// given
		String testUsername = "testUsername";
		String testAccessToken = "testAccessToken";
		String testRefreshToken = "testRefreshToken";

		when(tokenProvider.createAccessToken(authentication)).thenReturn(testAccessToken);
		when(tokenProvider.createRefreshToken(authentication)).thenReturn(testRefreshToken);
		when(refreshTokenService.save(anyString(), anyString())).thenReturn(1L);
		doNothing().when(refreshTokenService).deleteTokenByEmail(anyString());

		// when
		TokenResponse response = jwtTokenService.generateJwtTokens(testUsername, authentication);

		// then
		assertThat(response.accessToken()).isEqualTo(testAccessToken);
		assertThat(response.refreshToken()).isEqualTo(testRefreshToken);
	}
}
