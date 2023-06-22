package kr.binarybard.hireo.web.company.controller;

import kr.binarybard.hireo.common.exceptions.EntityNotFoundException;
import kr.binarybard.hireo.common.exceptions.ErrorCode;
import kr.binarybard.hireo.web.company.dto.CompanyRegister;
import kr.binarybard.hireo.web.company.service.CompanyService;
import kr.binarybard.hireo.web.member.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static kr.binarybard.hireo.web.fixture.CompanyFixture.EXISTING_COMPANY_ID;
import static kr.binarybard.hireo.web.fixture.CompanyFixture.NON_EXISTING_COMPANY_ID;
import static kr.binarybard.hireo.web.fixture.CompanyResponseFixture.TEST_COMPANY_RESPONSE;
import static kr.binarybard.hireo.web.fixture.MemberFixture.TEST_MEMBER_RESPONSE;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WithMockUser
@SpringBootTest
@AutoConfigureMockMvc
class CompanyControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CompanyService companyService;

	@MockBean
	private MemberService memberService;

	@BeforeEach
	void setup() {
		when(companyService.findOne(EXISTING_COMPANY_ID)).thenReturn(TEST_COMPANY_RESPONSE);
		when(companyService.findOne(NON_EXISTING_COMPANY_ID)).thenThrow(new EntityNotFoundException(
			ErrorCode.COMPANY_NOT_FOUND));
		when(memberService.findByEmail(anyString())).thenReturn(TEST_MEMBER_RESPONSE);
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
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("name", "gangmin");
		params.add("description", "hahahah");
		params.add("isVerified", "true");
		params.add("industry", "IT");
		params.add("latitude", "13.2323");
		params.add("longitude", "123.2323");
		params.add("countryCode", "KR");
		params.add("province", "kyeongi");
		params.add("city", "anyang");
		params.add("street", "wondol");
		params.add("district", "gogildong");
		params.add("premise", "www");
		mockMvc.perform(post("/companies/new")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.params(params))
			.andDo(print())
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/"));

		verify(companyService).registerCompany(any(CompanyRegister.class), any(User.class));
	}

	@Test
	@DisplayName("존재하는 회사 프로필 요청")
	void existingCompanyProfileTest() throws Exception {
		mockMvc.perform(get("/companies/" + EXISTING_COMPANY_ID))
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
