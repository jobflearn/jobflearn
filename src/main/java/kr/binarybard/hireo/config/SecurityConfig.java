package kr.binarybard.hireo.config;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.*;

import kr.binarybard.hireo.config.oauth.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	private final CustomOAuth2UserService customOAuth2UserService;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return web -> web.ignoring()
			.requestMatchers("/css/**", "/js/**", "/images/**", "/sass/**", "/fonts/**", "/error/**");
	}

	// TODO: 카카오 로그인, 구글 로그인
	@Bean
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
