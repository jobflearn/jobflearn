package kr.binarybard.hireo.api.auth.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

import java.time.Instant;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import kr.binarybard.hireo.api.auth.domain.RefreshToken;
import kr.binarybard.hireo.api.auth.repository.RefreshTokenRepository;
import kr.binarybard.hireo.config.jwt.JwtTokenProvider;
import kr.binarybard.hireo.web.account.domain.Account;
import kr.binarybard.hireo.web.account.domain.JobSeeker;
import kr.binarybard.hireo.web.account.repository.AccountRepository;

@ExtendWith(MockitoExtension.class)
class RefreshTokenServiceTest {

	@Mock
	private RefreshTokenRepository refreshTokenRepository;

	@Mock
	private JwtTokenProvider jwtTokenProvider;

	@Mock
	private AccountRepository accountRepository;

	@InjectMocks
	private RefreshTokenService refreshTokenService;

	private String validToken;
	private String username;
	private Account account;
	private RefreshToken refreshToken;

	@BeforeEach
	public void setup() {
		validToken = "valid_token";
		username = "username";
		account = JobSeeker.builder()
			.email(username)
			.build();
		refreshToken = RefreshToken.builder()
			.expiryDate(Instant.now().plusMillis(10000))
			.build();
	}

	@Test
	@DisplayName("토큰이 유효하고 DB에 존재할 경우, validateToken은 true를 반환한다.")
	void testValidateToken_success() {
		// given
		given(jwtTokenProvider.validateToken(validToken)).willReturn(true);
		given(jwtTokenProvider.getUsernameFromToken(validToken)).willReturn(username);
		given(accountRepository.findByEmailOrThrow(username)).willReturn(account);
		given(refreshTokenRepository.findByAccountAndToken(any(Account.class), anyString()))
			.willReturn(Optional.of(refreshToken));

		// when
		boolean result = refreshTokenService.validateToken(validToken);

		// then
		assertThat(result).isTrue();
	}

	@Test
	@DisplayName("토큰이 유효하지 않을 경우, validateToken은 false를 반환한다.")
	void testValidateToken_withInvalidToken() {
		// given
		String invalidToken = "invalid_token";
		given(jwtTokenProvider.validateToken(invalidToken)).willReturn(false);

		// when
		boolean result = refreshTokenService.validateToken(invalidToken);

		// then
		assertThat(result).isFalse();
	}

	@Test
	@DisplayName("토큰이 DB에서 찾을 수 없을 경우, validateToken은 false를 반환한다.")
	void testValidateToken_tokenNotFoundInDB() {
		// given
		given(jwtTokenProvider.validateToken(validToken)).willReturn(true);
		given(jwtTokenProvider.getUsernameFromToken(validToken)).willReturn(username);
		given(accountRepository.findByEmailOrThrow(username)).willReturn(account);
		given(refreshTokenRepository.findByAccountAndToken(any(Account.class), anyString()))
			.willReturn(Optional.empty());

		// when
		boolean result = refreshTokenService.validateToken(validToken);

		// then
		assertThat(result).isFalse();
	}

	@Test
	@DisplayName("토큰이 만료되었을 경우, validateToken은 false를 반환한다.")
	void testValidateToken_tokenExpired() {
		// given
		RefreshToken expiredToken = RefreshToken.builder()
			.expiryDate(Instant.now().minusMillis(10000))
			.build();
		given(jwtTokenProvider.validateToken(validToken)).willReturn(true);
		given(jwtTokenProvider.getUsernameFromToken(validToken)).willReturn(username);
		given(accountRepository.findByEmailOrThrow(username)).willReturn(account);
		given(refreshTokenRepository.findByAccountAndToken(any(Account.class), anyString()))
			.willReturn(Optional.of(expiredToken));

		// when
		boolean result = refreshTokenService.validateToken(validToken);

		// then
		assertThat(result).isFalse();
	}
}
