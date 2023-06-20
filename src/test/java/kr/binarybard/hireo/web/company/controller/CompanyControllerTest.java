package kr.binarybard.hireo.web.company.controller;

import kr.binarybard.hireo.exception.CompanyNotFoundException;
import kr.binarybard.hireo.web.company.service.CompanyService;
import kr.binarybard.hireo.web.fixture.CompanyResponseFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static kr.binarybard.hireo.web.fixture.CompanyFixture.EXISTING_COMPANY_ID;
import static kr.binarybard.hireo.web.fixture.CompanyFixture.NON_EXISTING_COMPANY_ID;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

	@BeforeEach
	void setup() {
		var companyResponse = CompanyResponseFixture.TEST_COMPANY_RESPONSE;
		when(companyService.findOne(EXISTING_COMPANY_ID)).thenReturn(companyResponse);
		when(companyService.findOne(NON_EXISTING_COMPANY_ID)).thenThrow(CompanyNotFoundException.class);
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
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/error/404"));
	}
}
