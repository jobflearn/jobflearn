package kr.binarybard.hireo.web.company.service;

import kr.binarybard.hireo.common.exceptions.EntityNotFoundException;
import kr.binarybard.hireo.common.exceptions.ErrorCode;
import kr.binarybard.hireo.web.company.dto.CompanyResponse;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import kr.binarybard.hireo.web.company.domain.Company;
import kr.binarybard.hireo.web.company.dto.CompanyMapper;
import kr.binarybard.hireo.web.company.dto.CompanyRegister;
import kr.binarybard.hireo.web.company.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;

import java.util.Locale;

@Service
@RequiredArgsConstructor
public class CompanyService {

	private final CompanyRepository companyRepository;
	private final CompanyMapper companyMapper;
	private final MessageSource messageSource;

	public Long registerCompany(CompanyRegister companyRegister) {
		Company company = companyMapper.toEntity(companyRegister);
		return companyRepository.save(company).getId();
	}

	public Company findById(Long id) {
		return companyRepository.findById(id)
			.orElseThrow(() -> new EntityNotFoundException(ErrorCode.COMPANY_NOT_FOUND, id.toString()));
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
