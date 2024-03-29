package kr.binarybard.hireo.web.company.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.io.IOException;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import org.springframework.web.multipart.MultipartFile;

import kr.binarybard.hireo.api.file.service.FileService;
import kr.binarybard.hireo.common.exceptions.EntityNotFoundException;
import kr.binarybard.hireo.common.fixture.AccountFixture;
import kr.binarybard.hireo.common.fixture.CompanyFixture;
import kr.binarybard.hireo.web.account.repository.AccountRepository;
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
	AccountRepository accountRepository;

	@Mock
	FileService fileService;

	@Mock
	CompanyMapper companyMapper;

	@Mock
	MessageSource messageSource;

	@Mock
	MultipartFile file;

	@InjectMocks
	CompanyService companyService;

	private final Company testCompanyA = CompanyFixture.createTestCompanyA();
	private final CompanyRegister testCompanyARegister = CompanyFixture.createTestCompanyARegister();
	private final CompanyResponse testCompanyAResponse = CompanyFixture.createTestCompanyAResponse();

	@Test
	@DisplayName("회사 등록 정상 동작 확인")
	void registerCompanyTest() throws IOException {
		// given
		when(companyRepository.save(testCompanyA)).thenReturn(testCompanyA);
		when(companyMapper.toEntity(testCompanyARegister)).thenReturn(testCompanyA);
		when(accountRepository.findByEmailOrThrow(anyString())).thenReturn(AccountFixture.createAccount());
		// when
		companyService.registerCompany(testCompanyARegister, AccountFixture.USER);

		// then
		verify(companyRepository, times(1)).save(testCompanyA);
	}

	@Test
	@DisplayName("회사 ID로 조회")
	void findByIdTest() {
		// given
		when(companyRepository.findById(anyLong())).thenReturn(Optional.of(testCompanyA));

		// when
		Company foundCompany = companyService.findById(testCompanyA.getId());

		// then
		verify(companyRepository, times(1)).findById(testCompanyA.getId());
		assertThat(testCompanyA.getId()).isEqualTo(foundCompany.getId());
	}

	@Test
	@DisplayName("id로 회사 조회후 null 반환시, 예외 발생 확인")
	void findByIdNotFoundTest() {
		// given
		Long nonExistingCompanyId = Long.MAX_VALUE;
		when(companyRepository.findById(nonExistingCompanyId)).thenReturn(Optional.empty());

		// expected
		assertThatThrownBy(() -> companyService.findById(nonExistingCompanyId))
			.isInstanceOf(EntityNotFoundException.class);
	}

	@Test
	@DisplayName("회사 정보 가져오기")
	void findOneTest() {
		// given
		when(companyRepository.findById(any(Long.class))).thenReturn(Optional.of(testCompanyA));
		when(companyMapper.toDto(testCompanyA)).thenReturn(testCompanyAResponse);

		// when
		CompanyResponse foundCompanyResponse = companyService.findOne(testCompanyA.getId());

		// then
		assertThat(foundCompanyResponse).isEqualTo(testCompanyAResponse);
		assertThat(foundCompanyResponse.getCountryName()).isEqualTo("CountryA");
	}

}
