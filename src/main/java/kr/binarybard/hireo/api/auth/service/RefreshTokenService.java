package kr.binarybard.hireo.api.auth.service;

import jakarta.transaction.Transactional;
import kr.binarybard.hireo.api.auth.domain.RefreshToken;
import kr.binarybard.hireo.api.auth.repository.RefreshTokenRepository;
import kr.binarybard.hireo.api.common.exceptions.ErrorCode;
import kr.binarybard.hireo.api.common.exceptions.InvalidValueException;
import kr.binarybard.hireo.config.jwt.JwtTokenProvider;
import kr.binarybard.hireo.web.member.domain.Member;
import kr.binarybard.hireo.web.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
	private final RefreshTokenRepository refreshTokenRepository;
	private final JwtTokenProvider tokenProvider;
	private final MemberService memberService;

	@Transactional
	public void deleteTokenByEmail(String email) {
		refreshTokenRepository.deleteByMember(memberService.findByEmail(email));
	}

	@Transactional
	public Long save(String email, String token) {
		var refreshToken = RefreshToken.builder()
			.member(memberService.findByEmail(email))
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
		return isTokenValid(token) && isTokenStored(token) && isTokenNotExpired(token);
	}

	private boolean isTokenValid(String token) {
		return tokenProvider.validateToken(token);
	}

	private boolean isTokenStored(String token) {
		String username = tokenProvider.getUsernameFromToken(token);
		Member member = memberService.findByEmail(username);
		var refreshToken = refreshTokenRepository.findByMemberAndToken(member, hashToken(token));
		return refreshToken.isPresent();
	}

	private boolean isTokenNotExpired(String token) {
		String username = tokenProvider.getUsernameFromToken(token);
		Member member = memberService.findByEmail(username);
		var refreshToken = refreshTokenRepository.findByMemberAndToken(member, hashToken(token));
		return refreshToken
			.filter(value -> !value.getExpiryDate().isBefore(Instant.now()))
			.isPresent();
	}
}
