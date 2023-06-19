package kr.binarybard.hireo.web.company.service;

import org.springframework.stereotype.Service;

import kr.binarybard.hireo.exception.CompanyNotFoundException;
import kr.binarybard.hireo.web.company.domain.Company;
import kr.binarybard.hireo.web.company.dto.CompanyMapper;
import kr.binarybard.hireo.web.company.dto.CompanyRegister;
import kr.binarybard.hireo.web.company.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CompanyService {

	private final CompanyRepository companyRepository;
	/*결국 연관관계의 주인은 Company, 사실 위치만! 변경하거나 하는 일은 없을듯
	 * 그렇다면, Location저장을 CompanyService와 MemberService가 LocationRepository를 의존하는 방식
	 * 으로 해보면 어떨까 생각했습니다.*/
	private final CompanyMapper companyMapper;

	public Long registerCompany(CompanyRegister companyRegister) {
		/*DTO 범위에서 Validation할거기 때문에 입력사항에 예외는 처리할 필요
		 * 없어보임*/
		Company company = companyMapper.toEntity(companyRegister);
		return companyRepository.save(company).getId();
	}

	public Company findById(Long id) {
		return companyRepository.findById(id)
			.orElseThrow(CompanyNotFoundException::new);
	}
}
