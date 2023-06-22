package kr.binarybard.hireo.web.company.service;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.binarybard.hireo.exception.CompanyNotFoundException;
import kr.binarybard.hireo.exception.MemberNotFoundException;
import kr.binarybard.hireo.web.company.domain.Company;
import kr.binarybard.hireo.web.company.dto.CompanyMapper;
import kr.binarybard.hireo.web.company.dto.CompanyRegister;
import kr.binarybard.hireo.web.company.dto.CompanyResponse;
import kr.binarybard.hireo.web.company.repository.CompanyRepository;
import kr.binarybard.hireo.web.location.dto.LocationMapper;
import kr.binarybard.hireo.web.member.domain.Member;
import kr.binarybard.hireo.web.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CompanyService {

	private final CompanyRepository companyRepository;
	private final MemberRepository memberRepository;
	private final CompanyMapper companyMapper;
	private final LocationMapper locationMapper;
	private final MessageSource messageSource;

	@Transactional
	public CompanyResponse registerCompany(CompanyRegister companyRegister, User user) {
		Company company = companyMapper.toEntity(companyRegister);
		Member member = memberRepository.findByEmail(user.getUsername())
			.orElseThrow(MemberNotFoundException::new);
		member.changeCompany(company);
		CompanyResponse dto = companyMapper.toDto(companyRepository.save(company));
		log.info(dto.getLocationDto().getCountryCode());
		log.info(dto.getLocationDto().getAddress().getDistrict());
		return dto;
	}

	public Company findById(Long id) {
		return companyRepository.findById(id)
			.orElseThrow(CompanyNotFoundException::new);
	}

	public CompanyResponse findOne(Long id) {
		var company = companyMapper.toDto(findById(id));
		company.setCountryName(getCountryName(company.getLocationDto().getCountryCode()));
		return company;
	}

	private String getCountryName(String countryCode) {
		Locale locale = LocaleContextHolder.getLocale();
		return messageSource.getMessage("country." + countryCode, null, locale);
	}
}
