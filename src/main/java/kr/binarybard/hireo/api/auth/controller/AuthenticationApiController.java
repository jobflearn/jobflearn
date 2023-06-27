package kr.binarybard.hireo.api.auth.controller;

import jakarta.validation.Valid;
import kr.binarybard.hireo.api.auth.dto.RefreshTokenRequest;
import kr.binarybard.hireo.api.auth.dto.SignInRequest;
import kr.binarybard.hireo.api.auth.dto.TokenResponse;
import kr.binarybard.hireo.api.auth.service.JwtLoginService;
import kr.binarybard.hireo.api.auth.service.JwtTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationApiController {
	private final JwtLoginService loginService;
	private final JwtTokenService tokenService;

	@PostMapping("/login")
	public ResponseEntity<TokenResponse> authorize(@Valid @RequestBody SignInRequest request) {
		return ResponseEntity.ok().body(loginService.authenticateUser(request));
	}

	@PostMapping("/refresh")
	public ResponseEntity<TokenResponse> refresh(@Valid @RequestBody RefreshTokenRequest request) {
		return ResponseEntity.ok().body(tokenService.refreshJwtTokens(request));
	}
}
