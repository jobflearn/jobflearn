package kr.binarybard.hireo.api.docs;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.binarybard.hireo.common.fixture.AccountFixture;
import kr.binarybard.hireo.common.fixture.CompanyFixture;
import kr.binarybard.hireo.common.fixture.ReviewFixture;
import kr.binarybard.hireo.web.account.domain.Account;
import kr.binarybard.hireo.web.account.repository.AccountRepository;
import kr.binarybard.hireo.web.company.domain.Company;
import kr.binarybard.hireo.web.company.repository.CompanyRepository;
import kr.binarybard.hireo.web.review.dto.ReviewRequest;

@WithMockUser(username = AccountFixture.TEST_EMAIL)
class ReviewControllerTest extends RestDocsConfiguration {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private CompanyRepository companyRepository;

	@Autowired
	private AccountRepository accountRepository;

	private Long companyId;

	@BeforeEach
	void setUp() {
		Company testCompany = CompanyFixture.createTestCompanyA();
		companyRepository.save(testCompany);
		companyId = testCompany.getId();

		Account testAccount = AccountFixture.createAccountWithId(1L);
		accountRepository.save(testAccount);
	}

	@Test
	@DisplayName("리뷰를 성공적으로 등록한다.")
	void successfullyRegisterReview() throws Exception {
		ReviewRequest reviewRequest = ReviewFixture.createTestReviewRequest1();
		mockMvc.perform(post("/companies/{id}/review", companyId)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(reviewRequest)))
			.andExpect(status().isCreated())
			.andExpect(redirectedUrl("/companies/1/review/2701"))
			.andDo(document("review-register",
				requestFields(
					fieldWithPath("title").description("리뷰 제목"),
					fieldWithPath("rating").description("리뷰 점수"),
					fieldWithPath("content").description("리뷰 내용"),
					fieldWithPath("postedAt").description("리뷰 게시 일자")
				)));
	}
}
