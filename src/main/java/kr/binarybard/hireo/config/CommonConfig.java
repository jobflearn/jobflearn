package kr.binarybard.hireo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import kr.binarybard.hireo.domain.MemberMapper;

@Configuration
public class CommonConfigure {
	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

	@Bean
	public MemberMapper memberMapper() {
		return MemberMapper.INSTANCE;
	}
}
