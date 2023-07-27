package kr.binarybard.hireo.web.company.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.test.context.support.WithMockUser;

import com.fasterxml.jackson.databind.ObjectMapper;

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

	@Autowired
	ObjectMapper objectMapper;

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
	@DisplayName("회사 등록 요청")
	void registerCompanyTest() throws Exception {
		CompanyRegister companyRegister = CompanyFixture.createTestCompanyARegister();
		mockMvc.perform(post("/companies/new")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(companyRegister)))
			.andDo(print())
			.andExpect(status().isCreated());
		verify(companyService).registerCompany(any(CompanyRegister.class), any(User.class));
	}

	@Test
	@DisplayName("존재하는 회사 프로필 요청")
	void existingCompanyProfileTest() throws Exception {
		mockMvc.perform(get("/companies/" + CompanyFixture.createTestCompanyAWithReviews().getId()))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.foundCompany.id").value(CompanyFixture.createTestCompanyAWithReviews().getId()))
			.andExpect(jsonPath("$.reviews.content").isArray());
	}

	@Test
	@DisplayName("존재하지 않는 회사 프로필 요청")
	void nonExistingCompanyProfileTest() throws Exception {
		mockMvc.perform(get("/companies/" + NON_EXISTING_COMPANY_ID))
			.andDo(print())
			.andExpect(status().isNotFound());
	}

	@Test
	@DisplayName("회사 로고 등록 요청")
	void registerLogoTest() throws Exception {
		mockMvc.perform(put("/companies/" + CompanyFixture.createTestCompanyAWithReviews().getId())
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.content(CompanyFixture.createMockCompanyLogo().getBytes()))
			.andExpect(status().isOk());
	}

}
