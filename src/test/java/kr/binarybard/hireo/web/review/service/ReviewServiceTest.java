package kr.binarybard.hireo.web.review.service;

import kr.binarybard.hireo.common.fixture.CompanyFixture;
import kr.binarybard.hireo.common.fixture.MemberFixture;
import kr.binarybard.hireo.common.fixture.ReviewFixture;
import kr.binarybard.hireo.web.company.domain.Company;
import kr.binarybard.hireo.web.company.repository.CompanyRepository;
import kr.binarybard.hireo.web.member.domain.Member;
import kr.binarybard.hireo.web.member.repository.MemberRepository;
import kr.binarybard.hireo.web.review.domain.Review;
import kr.binarybard.hireo.web.review.dto.ReviewRequest;
import kr.binarybard.hireo.web.review.dto.ReviewRequestMapper;
import kr.binarybard.hireo.web.review.dto.ReviewResponse;
import kr.binarybard.hireo.web.review.dto.ReviewResponseMapper;
import kr.binarybard.hireo.web.review.repository.ReviewRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.User;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {
	@Mock
	private ReviewRepository reviewRepository;

	@Mock
	private MemberRepository memberRepository;

	@Mock
	private CompanyRepository companyRepository;

	@Mock
	private ReviewRequestMapper reviewRequestMapper;

	@Mock
	private ReviewResponseMapper reviewResponseMapper;

	@InjectMocks
	private ReviewService reviewService;

	@Test
	@DisplayName("회원과 회사가 있을 때, 리뷰를 성공적으로 등록할 수 있다.")
	void postReviewSuccessfully() {
		// given
		User testUser = MemberFixture.USER;
		Member testMember = MemberFixture.createMember();
		Company testCompany = CompanyFixture.createTestCompanyA();
		ReviewRequest testReviewRequest = ReviewFixture.createTestReviewRequest1();
		Review testReview = ReviewFixture.createTestReview1(testCompany);

		when(memberRepository.findByEmailOrThrow(testUser.getUsername())).thenReturn(testMember);
		when(companyRepository.findByIdOrThrow(testCompany.getId())).thenReturn(testCompany);
		when(reviewRequestMapper.toEntity(testReviewRequest)).thenReturn(testReview);
		when(reviewRepository.save(testReview)).thenReturn(testReview);

		// then
		assertDoesNotThrow(() -> reviewService.post(testUser, testCompany.getId(), testReviewRequest));
	}

	@Test
	@DisplayName("회사 ID에 해당하는 리뷰를 성공적으로 가져온다.")
	void getReviewsByCompanyIdSuccessfully() {
		// given
		Company testCompany = CompanyFixture.createTestCompanyA();
		Page<Review> reviews = new PageImpl<>(List.of(ReviewFixture.createTestReview1(testCompany)));
		ReviewResponse reviewResponse = ReviewFixture.createTestReviewResponse1();

		when(reviewRepository.findAllByCompanyId(testCompany.getId(), PageRequest.of(0, 3))).thenReturn(reviews);
		when(reviewResponseMapper.toDto(any())).thenReturn(reviewResponse);

		// when
		Page<ReviewResponse> result = reviewService.getReviewsByCompanyId(testCompany.getId(), 1);

		// then
		assertThat(result)
			.isNotNull()
			.isNotEmpty();
		assertThat(result.getContent().get(0)).isEqualTo(reviewResponse);
	}
}
