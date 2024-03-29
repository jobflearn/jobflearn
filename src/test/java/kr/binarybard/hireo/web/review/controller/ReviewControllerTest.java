package kr.binarybard.hireo.web.review.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.binarybard.hireo.common.AcceptanceTest;
import kr.binarybard.hireo.common.fixture.AccountFixture;
import kr.binarybard.hireo.common.fixture.CompanyFixture;
import kr.binarybard.hireo.common.fixture.ReviewFixture;
import kr.binarybard.hireo.web.account.domain.Account;
import kr.binarybard.hireo.web.account.repository.AccountRepository;
import kr.binarybard.hireo.web.company.domain.Company;
import kr.binarybard.hireo.web.company.repository.CompanyRepository;
import kr.binarybard.hireo.web.review.dto.ReviewRequest;

@Transactional
@WithMockUser(username = AccountFixture.TEST_EMAIL)
class ReviewControllerTest extends AcceptanceTest {

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
			.andDo(print());
	}

	@Test
	@DisplayName("리뷰 내용이 10자 미만일 경우 리뷰 등록에 실패한다.")
	void failToRegisterReviewWhenContentIsLessThan10() throws Exception {
		ReviewRequest reviewRequest = ReviewFixture.createTestReviewRequestFail();
		mockMvc.perform(post("/companies/1/review")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(reviewRequest)))
			.andExpect(status().isBadRequest())
			.andDo(print());
	}
}
