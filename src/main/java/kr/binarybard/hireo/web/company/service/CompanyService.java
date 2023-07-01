package kr.binarybard.hireo.web.company.service;

import kr.binarybard.hireo.api.file.service.FileService;
import kr.binarybard.hireo.common.exceptions.EntityNotFoundException;
import kr.binarybard.hireo.common.exceptions.ErrorCode;
import kr.binarybard.hireo.web.company.domain.Company;
import kr.binarybard.hireo.web.company.dto.CompanyMapper;
import kr.binarybard.hireo.web.company.dto.CompanyRegister;
import kr.binarybard.hireo.web.company.dto.CompanyResponse;
import kr.binarybard.hireo.web.company.repository.CompanyRepository;
import kr.binarybard.hireo.web.member.domain.Member;
import kr.binarybard.hireo.web.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CompanyService {

	private final CompanyRepository companyRepository;
	private final MemberRepository memberRepository;
	private final CompanyMapper companyMapper;
	private final FileService fileService;

	@Transactional
	public CompanyResponse registerCompany(CompanyRegister companyRegister, User user) {
		Company company = companyMapper.toEntity(companyRegister);
		var fileResponse = fileService.storeAsHash(companyRegister.getCompanyLogo());
		company.changeLogo(fileResponse.getFileName());
		Member member = memberRepository.findByEmailOrThrow(user.getUsername());
		member.changeCompany(company);
		return companyMapper.toDto(companyRepository.save(company));
	}

	public Company findById(Long id) {
		return companyRepository.findById(id)
			.orElseThrow(() -> new EntityNotFoundException(ErrorCode.COMPANY_NOT_FOUND, id.toString()));
	}

	public CompanyResponse findOne(Long id) {
		return companyMapper.toDto(findById(id));
	}
}
