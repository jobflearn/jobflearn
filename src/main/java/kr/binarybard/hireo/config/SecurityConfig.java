package kr.binarybard.hireo.config;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.*;

import kr.binarybard.hireo.config.jwt.JwtAccessDeniedHandler;
import kr.binarybard.hireo.config.jwt.JwtAuthTokenFilter;
import kr.binarybard.hireo.config.jwt.JwtAuthenticationEntryPoint;
import kr.binarybard.hireo.config.jwt.JwtTokenProvider;
import kr.binarybard.hireo.config.oauth.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	private final CustomOAuth2UserService customOAuth2UserService;
	private final JwtTokenProvider jwtTokenProvider;
	private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return web -> web.ignoring()
			.requestMatchers("/css/**", "/js/**", "/images/**", "/sass/**", "/fonts/**", "/error/**");
	}

	@Bean
	@Order(1)
	public SecurityFilterChain restApiFilterChain(final HttpSecurity http) throws Exception {
		return http
			.securityMatcher("/api/**")
			.csrf(AbstractHttpConfigurer::disable)
			.authorizeHttpRequests(auth -> auth
				.requestMatchers("/api/auth/**").permitAll()
				.anyRequest().authenticated())
			.exceptionHandling(handler -> handler
				.authenticationEntryPoint(jwtAuthenticationEntryPoint)
				.accessDeniedHandler(jwtAccessDeniedHandler))
			.sessionManagement(session -> session
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.addFilterBefore(new JwtAuthTokenFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class)
			.build();
	}

	@Bean
	@Order(2)
	public SecurityFilterChain formFilterChain(final HttpSecurity http) throws Exception {
		return http
			.csrf(AbstractHttpConfigurer::disable)
			.authorizeHttpRequests(auth -> auth
				.requestMatchers(toH2Console()).permitAll()
				.requestMatchers("/", "/auth/**").permitAll()
				.anyRequest().authenticated()
			)
			.formLogin(login -> login
				.loginPage("/auth/login")
				.usernameParameter("email")
				.defaultSuccessUrl("/"))
			.headers(headers -> headers
				.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
			.logout(logout -> logout
				.logoutUrl("/auth/logout")
				.logoutSuccessUrl("/"))
			.sessionManagement(session -> session
				.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
			.oauth2Login(oauth -> oauth
				.userInfoEndpoint(userInfo -> userInfo
					.userService(customOAuth2UserService))
				.authorizationEndpoint(authorization -> authorization
					.baseUri("/auth/login/oauth2/authorize"))
				.redirectionEndpoint(redirection -> redirection
					.baseUri("/auth/login/oauth2/code/{code}")))
			.build();
	}
}
