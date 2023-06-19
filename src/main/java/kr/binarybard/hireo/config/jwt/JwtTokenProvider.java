package kr.binarybard.hireo.config.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.sql.Date;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtTokenProvider {
	private static final String AUTHORITIES_KEY = "auth";
	private Key key;

	private final String base64Secret;
	public final long REFRESH_TOKEN_EXPIRE_TIME;
	public final long ACCESS_TOKEN_EXPIRE_TIME;

	public JwtTokenProvider(
		@Value("${security.jwt.base64-secret}") String base64Secret,
		@Value("${security.jwt.refresh-expiration-time}") long refreshExpirationTime,
		@Value("${security.jwt.access-expiration-time}") long accessExpirationTime
	) {
		this.base64Secret = base64Secret;
		this.REFRESH_TOKEN_EXPIRE_TIME = refreshExpirationTime;
		this.ACCESS_TOKEN_EXPIRE_TIME = accessExpirationTime;
	}

	/**
	 * 사용자가 인증에 성공하면 토큰을 발급한다. 주어진 클레임과 주체(토큰에서 사용자에 대한 식별값)를
	 * 가지고 토큰을 만든다.
	 *
	 * @param authentication 인증 정보
	 * @return 토큰
	 */
	public String createToken(Authentication authentication, long expirationTime) {
		String authorities = authentication.getAuthorities().stream()
			.map(GrantedAuthority::getAuthority)
			.collect(Collectors.joining(","));
		return Jwts.builder()
			.setSubject(authentication.getName())
			.claim(AUTHORITIES_KEY, authorities)
			.setIssuedAt(new Date(System.currentTimeMillis()))
			.setExpiration(new Date(System.currentTimeMillis() + expirationTime))
			.signWith(key, SignatureAlgorithm.HS512)
			.compact();
	}

	public String createAccessToken(Authentication authentication) {
		return createToken(authentication, ACCESS_TOKEN_EXPIRE_TIME);
	}

	public String createRefreshToken(Authentication authentication) {
		return createToken(authentication, REFRESH_TOKEN_EXPIRE_TIME);
	}

	public String getUsernameFromToken(String token) {
		return getAllClaimsFromToken(token)
			.getBody()
			.getSubject();
	}

	@PostConstruct
	public void init() {
		byte[] secretKeyBytes = Decoders.BASE64.decode(base64Secret);
		this.key = Keys.hmacShaKeyFor(secretKeyBytes);
	}

	public Authentication getAuthentication(String token) {
		Claims claims = getAllClaimsFromToken(token).getBody();
		Collection<? extends GrantedAuthority> authorities =
			Arrays.stream(Optional.ofNullable(claims.get(AUTHORITIES_KEY))
					.map(Object::toString)
					.orElse("")
					.split(","))
				.map(String::trim)
				.filter(auth -> !auth.isEmpty())
				.map(SimpleGrantedAuthority::new)
				.toList();
		User principal = new User(claims.getSubject(), "", authorities);
		return new UsernamePasswordAuthenticationToken(principal, token, authorities);
	}


	/**
	 * 토큰이 유효한지 검증한다.
	 *
	 * @param token 토큰
	 * @return 유효하면 true, 아니면 false
	 */
	public boolean validateToken(String token) {
		try {
			getAllClaimsFromToken(token);
			return true;
		} catch (JwtException ex) {
			log.trace("Invalid JWT token trace: {}", ex.toString());
			return false;
		}
	}

	/**
	 * 토큰에서 모든 클레임(claims)을 가져온다.
	 *
	 * @param token 토큰
	 * @return 클레임
	 */
	private Jws<Claims> getAllClaimsFromToken(String token) {
		return Jwts.parserBuilder()
			.setSigningKey(key)
			.build()
			.parseClaimsJws(token);
	}
}
