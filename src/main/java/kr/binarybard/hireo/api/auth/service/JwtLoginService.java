package kr.binarybard.hireo.api.auth.service;

import kr.binarybard.hireo.api.auth.dto.SignInRequest;
import kr.binarybard.hireo.api.auth.dto.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtLoginService {
	private final AuthenticationManagerBuilder authenticationManagerBuilder;
	private final JwtTokenService tokenService;

	public TokenResponse authenticateUser(SignInRequest request) {
		var authenticationToken = new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());
		var authentication = authenticationManagerBuilder.getObject()
			.authenticate(authenticationToken);
		return tokenService.generateJwtTokens(request.getEmail(), authentication);
	}
}
