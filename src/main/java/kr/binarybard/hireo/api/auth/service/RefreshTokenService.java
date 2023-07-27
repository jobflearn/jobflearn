package kr.binarybard.hireo.api.auth.service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.Base64;
import java.util.Optional;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import kr.binarybard.hireo.api.auth.domain.RefreshToken;
import kr.binarybard.hireo.api.auth.repository.RefreshTokenRepository;
import kr.binarybard.hireo.common.exceptions.ErrorCode;
import kr.binarybard.hireo.common.exceptions.InvalidValueException;
import kr.binarybard.hireo.config.jwt.JwtTokenProvider;
import kr.binarybard.hireo.web.account.domain.Account;
import kr.binarybard.hireo.web.account.repository.AccountRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
	private final RefreshTokenRepository refreshTokenRepository;
	private final JwtTokenProvider tokenProvider;
	private final AccountRepository accountRepository;

	@Transactional
	public void deleteTokenByEmail(String email) {
		refreshTokenRepository.deleteByAccount(accountRepository.findByEmailOrThrow(email));
		refreshTokenRepository.flush();
	}

	@Transactional
	public Long save(String email, String token) {
		var refreshToken = RefreshToken.builder()
			.account(accountRepository.findByEmailOrThrow(email))
			.token(hashToken(token))
			.expiryDate(Instant.now().plusMillis(tokenProvider.REFRESH_TOKEN_EXPIRE_TIME))
			.build();
		return refreshTokenRepository.save(refreshToken).getId();
	}

	private String hashToken(String token) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			byte[] hash = md.digest(token.getBytes(StandardCharsets.UTF_8));
			return Base64.getEncoder().encodeToString(hash);
		} catch (NoSuchAlgorithmException e) {
			throw new InvalidValueException(ErrorCode.TOKEN_HASHING_ERROR);
		}
	}

	public boolean validateToken(String token) {
		if (!tokenProvider.validateToken(token)) {
			return false;
		}

		Optional<RefreshToken> storedRefreshToken = getStoredRefreshToken(token);
		return storedRefreshToken.isPresent() && isTokenNotExpired(storedRefreshToken.get());
	}

	private Optional<RefreshToken> getStoredRefreshToken(String token) {
		String username = tokenProvider.getUsernameFromToken(token);
		Account account = accountRepository.findByEmailOrThrow(username);
		return refreshTokenRepository.findByAccountAndToken(account, hashToken(token));
	}

	private boolean isTokenNotExpired(RefreshToken token) {
		return !token.getExpiryDate().isBefore(Instant.now());
	}
}
