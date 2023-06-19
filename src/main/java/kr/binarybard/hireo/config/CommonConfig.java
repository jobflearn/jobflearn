package kr.binarybard.hireo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import kr.binarybard.hireo.web.member.dto.MemberMapper;

@Configuration
public class CommonConfig {

	@Bean
	public MemberMapper memberMapper() {
		return MemberMapper.INSTANCE;
	}
}
