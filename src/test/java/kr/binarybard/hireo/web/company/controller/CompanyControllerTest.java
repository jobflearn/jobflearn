package kr.binarybard.hireo.web.company.controller;

import kr.binarybard.hireo.common.AcceptanceTest;
import kr.binarybard.hireo.common.exceptions.EntityNotFoundException;
import kr.binarybard.hireo.common.exceptions.ErrorCode;
import kr.binarybard.hireo.common.fixture.CompanyFixture;
import kr.binarybard.hireo.common.fixture.MemberFixture;
import kr.binarybard.hireo.web.company.dto.CompanyRegister;
import kr.binarybard.hireo.web.company.dto.CompanyResponse;
import kr.binarybard.hireo.web.company.service.CompanyService;
import kr.binarybard.hireo.web.member.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.test.context.support.WithMockUser;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WithMockUser
class CompanyControllerTest extends AcceptanceTest {

	@MockBean
	private CompanyService companyService;

	@MockBean
	private MemberService memberService;

	private static final Long NON_EXISTING_COMPANY_ID = Long.MAX_VALUE;

	@BeforeEach
	void setup() {
		CompanyResponse testCompanyResponse = CompanyFixture.createTestCompanyAResponse();

		when(companyService.findOne(CompanyFixture.createTestCompanyA().getId())).thenReturn(testCompanyResponse);
		when(companyService.findOne(NON_EXISTING_COMPANY_ID)).thenThrow(new EntityNotFoundException(
			ErrorCode.COMPANY_NOT_FOUND));
		when(memberService.findByEmail(anyString())).thenReturn(MemberFixture.MEMBER_RESPONSE);
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
		mockMvc.perform(get("/companies/" + CompanyFixture.createTestCompanyA().getId()))
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
