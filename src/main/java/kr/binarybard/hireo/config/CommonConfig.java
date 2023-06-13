package kr.binarybard.hireo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import kr.binarybard.hireo.member.dto.MemberMapper;

@Configuration
public class CommonConfig {
	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

	@Bean
	public MemberMapper memberMapper() {
		return MemberMapper.INSTANCE;
	}
}
