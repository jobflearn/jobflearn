package kr.binarybard.hireo.company.service;

import static org.mockito.BDDMockito.*;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import kr.binarybard.hireo.company.domain.Company;
import kr.binarybard.hireo.company.dto.CompanyMapper;
import kr.binarybard.hireo.company.dto.CompanyRegister;
import kr.binarybard.hireo.company.dto.CompanyResponse;
import kr.binarybard.hireo.company.repository.CompanyRepository;
import kr.binarybard.hireo.exception.CompanyNotFoundException;
import kr.binarybard.hireo.fixture.CompanyFixture;
import kr.binarybard.hireo.fixture.CompanyRegisterFixture;
import kr.binarybard.hireo.fixture.CompanyReponseFixture;

@ExtendWith(MockitoExtension.class)
class CompanyServiceTest {
	@Mock
	CompanyRepository companyRepository;

	@Mock
	CompanyMapper companyMapper;

	@InjectMocks
	CompanyService companyService;

	@Test
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
	void 회사조회() throws Exception {
		//given
		Company company = CompanyFixture.TEST_COMPANY;
		CompanyResponse companyReponse = CompanyReponseFixture.TEST_COMPANY_REPONSE;
		//when
		when(companyRepository.findById(anyLong())).thenReturn(Optional.of(company));
		when(companyMapper.toDto(any(Company.class))).thenReturn(companyReponse);
		//then
		companyService.findById(1L);
		verify(companyRepository, times(1)).findById(1L);
	}

	@Test
	void 회사조회실패검증() throws Exception {
		//given
		//given
		Company company = CompanyFixture.TEST_COMPANY;
		CompanyResponse companyReponse = CompanyReponseFixture.TEST_COMPANY_REPONSE;
		//when
		when(companyRepository.findById(4L)).thenReturn(Optional.empty());

		//then
		Assertions.assertThrows(CompanyNotFoundException.class, () -> companyService.findById(4L));
	}
}
