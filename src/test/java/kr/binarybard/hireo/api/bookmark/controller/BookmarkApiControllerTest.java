package kr.binarybard.hireo.api.bookmark.controller;

import kr.binarybard.hireo.web.company.service.CompanyService;
import kr.binarybard.hireo.web.member.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static kr.binarybard.hireo.web.fixture.CompanyRegisterFixture.TEST_COMPANY_REGISTER;
import static kr.binarybard.hireo.web.fixture.MemberFixture.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(TEST_EMAIL)
class BookmarkApiControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private MemberService memberService;

	@Autowired
	private CompanyService companyService;

	@BeforeEach
	void setup() {
		memberService.save(SIGNUP_REQUEST_MEMBER);
		companyService.registerCompany(TEST_COMPANY_REGISTER, USER);
	}

	@Test
	@DisplayName("로그인한 사용자가 회사를 북마크할 수 있다")
	void bookmarkCompanyTest() throws Exception {
		long companyId = 1L;
		mockMvc.perform(
				post("/api/companies/" + companyId + "/bookmarks")
					.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated())
			.andExpect(header().exists("Location"));
	}

	@Test
	@DisplayName("로그인한 사용자가 회사의 북마크를 삭제할 수 있다")
	void deleteCompanyBookmarkTest() throws Exception {
		long companyId = 1L;
		mockMvc.perform(
				post("/api/companies/" + companyId + "/bookmarks")
					.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated());

		mockMvc.perform(
				delete("/api/companies/" + companyId + "/bookmarks")
					.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isNoContent());
	}
}
