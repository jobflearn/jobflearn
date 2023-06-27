package kr.binarybard.hireo.api.auth.service;

import kr.binarybard.hireo.api.auth.dto.RefreshTokenRequest;
import kr.binarybard.hireo.api.auth.dto.TokenResponse;
import kr.binarybard.hireo.common.exceptions.AuthException;
import kr.binarybard.hireo.common.exceptions.ErrorCode;
import kr.binarybard.hireo.config.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtTokenService {
	private final JwtTokenProvider tokenProvider;
	private final RefreshTokenService refreshTokenService;

	public TokenResponse refreshJwtTokens(RefreshTokenRequest request) {
		String currentRefreshToken = request.getRefreshToken();
		validateRefreshToken(currentRefreshToken);
		var authentication = tokenProvider.getAuthentication(currentRefreshToken);
		return generateJwtTokens(authentication.getName(), authentication);
	}

	public TokenResponse generateJwtTokens(String username, Authentication authentication) {
		String accessToken = tokenProvider.createAccessToken(authentication);
		String refreshToken = createAndSaveRefreshToken(username, authentication);
		return new TokenResponse(accessToken, refreshToken);
	}

	private String createAndSaveRefreshToken(String email, Authentication authentication) {
		String refreshToken = tokenProvider.createRefreshToken(authentication);

		refreshTokenService.deleteTokenByEmail(email);
		refreshTokenService.save(email, refreshToken);

		return refreshToken;
	}

	private void validateRefreshToken(String token) {
		if (!refreshTokenService.validateToken(token)) {
			throw new AuthException(ErrorCode.INVALID_TOKEN);
		}
	}
}
