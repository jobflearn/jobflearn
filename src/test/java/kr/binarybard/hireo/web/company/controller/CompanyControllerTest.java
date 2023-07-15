package kr.binarybard.hireo.web.company.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.test.context.support.WithMockUser;

import kr.binarybard.hireo.common.AcceptanceTest;
import kr.binarybard.hireo.common.exceptions.EntityNotFoundException;
import kr.binarybard.hireo.common.exceptions.ErrorCode;
import kr.binarybard.hireo.common.fixture.AccountFixture;
import kr.binarybard.hireo.common.fixture.CompanyFixture;
import kr.binarybard.hireo.web.account.service.AccountService;
import kr.binarybard.hireo.web.company.dto.CompanyRegister;
import kr.binarybard.hireo.web.company.dto.CompanyResponse;
import kr.binarybard.hireo.web.company.service.CompanyService;

@WithMockUser
class CompanyControllerTest extends AcceptanceTest {

	@MockBean
	private CompanyService companyService;

	@MockBean
	private AccountService accountService;

	private static final Long NON_EXISTING_COMPANY_ID = Long.MAX_VALUE;

	@BeforeEach
	void setup() {
		CompanyResponse testCompanyResponse = CompanyFixture.createTestCompanyAResponse();

		when(companyService.findOne(CompanyFixture.createTestCompanyAWithReviews().getId())).thenReturn(
			testCompanyResponse);
		when(companyService.findOne(NON_EXISTING_COMPANY_ID)).thenThrow(new EntityNotFoundException(
			ErrorCode.COMPANY_NOT_FOUND));
		when(accountService.findByEmail(anyString())).thenReturn(AccountFixture.ACCOUNT_RESPONSE);
	}

	@Test
	@DisplayName("회사등록 폼 요청")
	void requestCompanyRegisterFormTest() throws Exception {
		mockMvc.perform(get("/companies/new"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(view().name("company/company-form"));
	}

	@Test
	@DisplayName("회사 등록 요청")
	void registerCompanyTest() throws Exception {
		CompanyRegister companyRegister = CompanyFixture.createTestCompanyARegister();
		mockMvc.perform(post("/companies/new")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("name", companyRegister.getName())
				.param("description", companyRegister.getDescription())
				.param("isVerified", companyRegister.getIsVerified().toString())
				.param("industry", companyRegister.getIndustry().toString())
				.param("latitude", companyRegister.getLocationDto().getLatitude().toString())
				.param("longitude", companyRegister.getLocationDto().getLongitude().toString())
				.param("countryCode", companyRegister.getLocationDto().getCountryCode())
				.param("province", companyRegister.getLocationDto().getAddress().getProvince())
				.param("city", companyRegister.getLocationDto().getAddress().getCity())
				.param("street", companyRegister.getLocationDto().getAddress().getStreet())
				.param("district", companyRegister.getLocationDto().getAddress().getDistrict())
				.param("premise", companyRegister.getLocationDto().getAddress().getPremise()))
			.andDo(print())
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/"));

		verify(companyService).registerCompany(any(CompanyRegister.class), any(User.class));
	}

	@Test
	@DisplayName("존재하는 회사 프로필 요청")
	void existingCompanyProfileTest() throws Exception {
		mockMvc.perform(get("/companies/" + CompanyFixture.createTestCompanyAWithReviews().getId()))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(view().name("company/profile"))
			.andExpect(model().attributeExists("company"));
	}

	@Test
	@DisplayName("존재하지 않는 회사 프로필 요청")
	void nonExistingCompanyProfileTest() throws Exception {
		mockMvc.perform(get("/companies/" + NON_EXISTING_COMPANY_ID))
			.andDo(print())
			.andExpect(status().isNotFound());
	}
}
