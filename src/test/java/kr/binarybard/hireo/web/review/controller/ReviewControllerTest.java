package kr.binarybard.hireo.web.review.controller;

import kr.binarybard.hireo.common.AcceptanceTest;
import kr.binarybard.hireo.common.fixture.CompanyFixture;
import kr.binarybard.hireo.common.fixture.MemberFixture;
import kr.binarybard.hireo.web.company.domain.Company;
import kr.binarybard.hireo.web.company.repository.CompanyRepository;
import kr.binarybard.hireo.web.member.domain.Member;
import kr.binarybard.hireo.web.member.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithMockUser(username = MemberFixture.TEST_EMAIL)
class ReviewControllerTest extends AcceptanceTest {

	@Autowired
	private CompanyRepository companyRepository;

	@Autowired
	private MemberRepository memberRepository;

	private Long companyId;

	@BeforeEach
	void setUp() {
		Company testCompany = CompanyFixture.createTestCompanyA();
		companyRepository.save(testCompany);
		companyId = testCompany.getId();

		Member testMember = MemberFixture.createMemberWithId(1L);
		memberRepository.save(testMember);
	}

	@Test
	@DisplayName("리뷰를 성공적으로 등록한다.")
	void successfullyRegisterReview() throws Exception {
		mockMvc.perform(post("/companies/{id}/review", companyId)
			.param("title", "리뷰 제목")
			.param("rating", "5")
			.param("content", "리뷰 내용은 10자를 넘어야 한다."))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/companies/1"))
			.andDo(print());
	}

	@Test
	@DisplayName("리뷰 내용이 10자 미만일 경우 리뷰 등록에 실패한다.")
	void failToRegisterReviewWhenContentIsLessThan10() throws Exception {
		mockMvc.perform(post("/companies/1/review")
			.param("title", "리뷰 제목")
			.param("rating", "5")
			.param("content", "10자 미만"))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/companies/1?error"))
			.andDo(print());
	}
}
