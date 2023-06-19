package kr.binarybard.hireo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import kr.binarybard.hireo.web.company.dto.CompanyMapper;
import kr.binarybard.hireo.web.location.dto.LocationMapper;
import kr.binarybard.hireo.web.member.dto.MemberMapper;

@Configuration
public class CommonConfig {

	@Bean
	public MemberMapper memberMapper() {
		return MemberMapper.INSTANCE;
	}

	@Bean
	public CompanyMapper companyMapper() {
		return CompanyMapper.INSTANCE;
	}

	@Bean
	public LocationMapper locationMapper() {
		return LocationMapper.INSTANCE;
	}
}
