package kr.binarybard.hireo.web.company.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import kr.binarybard.hireo.api.file.dto.FileResponse;
import kr.binarybard.hireo.api.file.service.FileService;
import kr.binarybard.hireo.common.exceptions.EntityNotFoundException;
import kr.binarybard.hireo.common.exceptions.ErrorCode;
import kr.binarybard.hireo.web.account.domain.Personnel;
import kr.binarybard.hireo.web.account.repository.AccountRepository;
import kr.binarybard.hireo.web.company.domain.Company;
import kr.binarybard.hireo.web.company.dto.CompanyMapper;
import kr.binarybard.hireo.web.company.dto.CompanyRegister;
import kr.binarybard.hireo.web.company.dto.CompanyResponse;
import kr.binarybard.hireo.web.company.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CompanyService {

	private final CompanyRepository companyRepository;
	private final AccountRepository accountRepository;
	private final CompanyMapper companyMapper;
	private final FileService fileService;

	@Transactional
	public Long registerCompany(CompanyRegister companyRegister, User user) {
		Company company = companyMapper.toEntity(companyRegister);
		Personnel personnel = (Personnel)accountRepository.findByEmailOrThrow(user.getUsername());
		personnel.changeCompany(company);
		return companyRepository.save(company).getId();
	}

	@Transactional
	public FileResponse uploadLogo(Long id, MultipartFile companyLogo) {
		Company foundCompany = companyRepository.findByIdOrThrow(id);
		var fileResponse = fileService.storeAsHash(companyLogo);
		foundCompany.changeLogo(fileResponse.getFileName());
		return fileResponse;
	}

	public Company findById(Long id) {
		return companyRepository.findById(id)
			.orElseThrow(() -> new EntityNotFoundException(ErrorCode.COMPANY_NOT_FOUND, id.toString()));
	}

	public CompanyResponse findOne(Long id) {
		return companyMapper.toDto(findById(id));
	}
}
