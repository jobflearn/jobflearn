package kr.binarybard.hireo.web.company.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import kr.binarybard.hireo.exception.CompanyNotFoundException;
import kr.binarybard.hireo.fixture.CompanyFixture;
import kr.binarybard.hireo.fixture.CompanyRegisterFixture;
import kr.binarybard.hireo.fixture.CompanyReponseFixture;
import kr.binarybard.hireo.web.company.domain.Company;
import kr.binarybard.hireo.web.company.dto.CompanyMapper;
import kr.binarybard.hireo.web.company.dto.CompanyRegister;
import kr.binarybard.hireo.web.company.dto.CompanyResponse;
import kr.binarybard.hireo.web.company.repository.CompanyRepository;

@ExtendWith(MockitoExtension.class)
class CompanyServiceTest {
	@Mock
	CompanyRepository companyRepository;

	@Mock
	CompanyMapper companyMapper;

	@InjectMocks
	CompanyService companyService;

	@Test
	@DisplayName("회사 등록 정상 동작 확인")
	void 회사등록() throws Exception {
		//given
		CompanyRegister companyRegister = CompanyRegisterFixture.TEST_COMPANY_REGISTER;
		Company company = CompanyFixture.TEST_COMPANY;
		when(companyMapper.toEntity(any(CompanyRegister.class))).thenReturn(company);
		when(companyRepository.save(company)).thenReturn(company);
		//when
		companyService.registerCompany(companyRegister);
		//then
		verify(companyRepository, times(1)).save(company);
	}

	@Test
	@DisplayName("회사 id로 조회")
	void 회사조회() throws Exception {
		//given
		Company company = CompanyFixture.TEST_COMPANY;
		CompanyResponse companyReponse = CompanyReponseFixture.TEST_COMPANY_REPONSE;
		when(companyRepository.findById(anyLong())).thenReturn(Optional.of(company));
		//when
		Company foundCompany = companyService.findById(1L);
		//then
		verify(companyRepository, times(1)).findById(1L);
		assertThat(company.getId()).isEqualTo(foundCompany.getId());
	}

	@Test
	@DisplayName("id로 회사 조회후 null 반환시, 예외 발생 확")
	void 회사조회실패검증() throws Exception {
		//given
		Company company = CompanyFixture.TEST_COMPANY;
		CompanyResponse companyReponse = CompanyReponseFixture.TEST_COMPANY_REPONSE;
		//when
		when(companyRepository.findById(4L)).thenReturn(Optional.empty());
		//then
		Assertions.assertThrows(CompanyNotFoundException.class, () -> companyService.findById(4L));
	}
}
