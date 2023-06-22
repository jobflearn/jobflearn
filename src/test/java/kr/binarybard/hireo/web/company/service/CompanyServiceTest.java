package kr.binarybard.hireo.web.company.service;

import static kr.binarybard.hireo.web.fixture.CompanyFixture.*;
import static kr.binarybard.hireo.web.fixture.CompanyRegisterFixture.*;
import static kr.binarybard.hireo.web.fixture.CompanyResponseFixture.*;
import static kr.binarybard.hireo.web.fixture.MemberFixture.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;

import kr.binarybard.hireo.exception.CompanyNotFoundException;
import kr.binarybard.hireo.web.company.domain.Company;
import kr.binarybard.hireo.web.company.dto.CompanyMapper;
import kr.binarybard.hireo.web.company.dto.CompanyResponse;
import kr.binarybard.hireo.web.company.repository.CompanyRepository;
import kr.binarybard.hireo.web.fixture.CompanyFixture;
import kr.binarybard.hireo.web.member.repository.MemberRepository;

@ExtendWith(MockitoExtension.class)
class CompanyServiceTest {

	@Mock
	CompanyRepository companyRepository;

	@Mock
	MemberRepository memberRepository;

	@Mock
	CompanyMapper companyMapper;

	@Mock
	MessageSource messageSource;

	@InjectMocks
	CompanyService companyService;

	final Long REQUEST_MEMBER_ID = 1L;

	@Test
	@DisplayName("회사 등록 정상 동작 확인")
	void registerCompanyTest() {
		// given
		when(companyRepository.save(TEST_COMPANY)).thenReturn(TEST_COMPANY);
		when(companyMapper.toEntity(TEST_COMPANY_REGISTER)).thenReturn(TEST_COMPANY);
		when(companyMapper.toDto(TEST_COMPANY)).thenReturn(TEST_COMPANY_RESPONSE);
		when(memberRepository.findByEmail(anyString())).thenReturn(Optional.of(TEST_MEMBER));

		// when
		companyService.registerCompany(TEST_COMPANY_REGISTER, TEST_USER);

		// then
		verify(companyRepository, times(1)).save(TEST_COMPANY);
	}

	@Test
	@DisplayName("회사 ID로 조회")
	void findByIdTest() {
		// given
		when(companyRepository.findById(anyLong())).thenReturn(Optional.of(TEST_COMPANY));

		// when
		Company foundCompany = companyService.findById(EXISTING_COMPANY_ID);

		// then
		verify(companyRepository, times(1)).findById(EXISTING_COMPANY_ID);
		assertThat(TEST_COMPANY.getId()).isEqualTo(foundCompany.getId());
	}

	@Test
	@DisplayName("id로 회사 조회후 null 반환시, 예외 발생 확인")
	void findByIdNotFoundTest() {
		// given
		when(companyRepository.findById(NON_EXISTING_COMPANY_ID)).thenReturn(Optional.empty());

		// expected
		assertThatThrownBy(() -> companyService.findById(NON_EXISTING_COMPANY_ID))
			.isInstanceOf(CompanyNotFoundException.class);
	}

	@Test
	@DisplayName("회사 정보 가져오기")
	void findOneTest() {
		// given
		when(companyRepository.findById(any(Long.class))).thenReturn(Optional.of(CompanyFixture.TEST_COMPANY));
		when(messageSource.getMessage(any(String.class), any(), any())).thenReturn("CountryName");
		when(companyMapper.toDto(CompanyFixture.TEST_COMPANY)).thenReturn(TEST_COMPANY_RESPONSE);

		// when
		CompanyResponse foundCompanyResponse = companyService.findOne(1L);

		// then
		assertThat(foundCompanyResponse).isEqualTo(TEST_COMPANY_RESPONSE);
		assertThat(foundCompanyResponse.getCountryName()).isEqualTo("CountryName");
	}
}
