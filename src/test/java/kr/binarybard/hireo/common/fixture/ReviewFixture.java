package kr.binarybard.hireo.common.fixture;

import java.time.LocalDate;
import java.time.LocalDateTime;

import kr.binarybard.hireo.web.account.domain.Account;
import kr.binarybard.hireo.web.company.domain.Company;
import kr.binarybard.hireo.web.review.domain.Review;
import kr.binarybard.hireo.web.review.dto.ReviewRequest;
import kr.binarybard.hireo.web.review.dto.ReviewResponse;

public class ReviewFixture {
	private static final String TEST_REVIEW_TITLE_1 = "Review Title 1";
	private static final String TEST_REVIEW_CONTENT_1 = "Review Content 1";
	private static final String TEST_REVIEW_RESPONSE_NAME_1 = "Reviewer Name 1";
	private static final int TEST_REVIEW_RATING_1 = 5;

	private static final String TEST_REVIEW_TITLE_2 = "Review Title 2";
	private static final String TEST_REVIEW_CONTENT_2 = "Review Content 2";
	private static final String TEST_REVIEW_RESPONSE_NAME_2 = "Reviewer Name 2";
	private static final int TEST_REVIEW_RATING_2 = 4;

	private static final String TEST_REVIEW_TITLE_3 = "Review Title 3";
	private static final String TEST_REVIEW_CONTENT_3 = "Review Content 3";
	private static final String TEST_REVIEW_RESPONSE_NAME_3 = "Reviewer Name 3";
	private static final int TEST_REVIEW_RATING_3 = 3;

	public static Review createTestReview1(Company company) {
		Account author = AccountFixture.createAccountWithId(1L);
		return createReview(TEST_REVIEW_TITLE_1, TEST_REVIEW_CONTENT_1, TEST_REVIEW_RATING_1, author, company);
	}

	public static ReviewResponse createTestReviewResponse1() {
		return createReviewResponse(TEST_REVIEW_RESPONSE_NAME_1, ReviewFixture.TEST_REVIEW_TITLE_1,
			ReviewFixture.TEST_REVIEW_CONTENT_1, ReviewFixture.TEST_REVIEW_RATING_1, LocalDateTime.now());
	}

	public static ReviewRequest createTestReviewRequest1() {
		return createReviewRequest(TEST_REVIEW_TITLE_1, TEST_REVIEW_CONTENT_1, TEST_REVIEW_RATING_1, LocalDate.now());
	}

	public static Review createTestReview2(Company company) {
		Account author = AccountFixture.createAccountWithId(2L);
		return createReview(TEST_REVIEW_TITLE_2, TEST_REVIEW_CONTENT_2, TEST_REVIEW_RATING_2, author, company);
	}

	public static ReviewResponse createTestReviewResponse2() {
		return createReviewResponse(TEST_REVIEW_RESPONSE_NAME_2, ReviewFixture.TEST_REVIEW_TITLE_2,
			ReviewFixture.TEST_REVIEW_CONTENT_2, ReviewFixture.TEST_REVIEW_RATING_2, LocalDateTime.now());
	}

	public static ReviewRequest createTestReviewRequest2() {
		return createReviewRequest(TEST_REVIEW_TITLE_2, TEST_REVIEW_CONTENT_2, TEST_REVIEW_RATING_2, LocalDate.now());
	}

	public static Review createTestReview3(Company company) {
		Account author = AccountFixture.createAccountWithId(3L);
		return createReview(TEST_REVIEW_TITLE_3, TEST_REVIEW_CONTENT_3, TEST_REVIEW_RATING_3, author, company);
	}

	public static ReviewResponse createTestReviewResponse3() {
		return createReviewResponse(TEST_REVIEW_RESPONSE_NAME_3, ReviewFixture.TEST_REVIEW_TITLE_3,
			ReviewFixture.TEST_REVIEW_CONTENT_3, ReviewFixture.TEST_REVIEW_RATING_3, LocalDateTime.now());
	}

	public static ReviewRequest createTestReviewRequest3() {
		return createReviewRequest(TEST_REVIEW_TITLE_3, TEST_REVIEW_CONTENT_3, TEST_REVIEW_RATING_3, LocalDate.now());
	}

	private static Review createReview(String title, String content, int rating, Account author, Company company) {
		return Review.builder()
			.title(title)
			.content(content)
			.rating(rating)
			.author(author)
			.company(company)
			.build();
	}

	private static ReviewResponse createReviewResponse(String name, String title, String content, int rating,
		LocalDateTime postedAt) {
		ReviewResponse reviewResponse = new ReviewResponse();
		reviewResponse.setName(name);
		reviewResponse.setTitle(title);
		reviewResponse.setContent(content);
		reviewResponse.setRating(rating);
		reviewResponse.setPostedAt(postedAt);
		return reviewResponse;
	}

	private static ReviewRequest createReviewRequest(String title, String content, int rating, LocalDate postedAt) {
		return ReviewRequest.builder()
			.title(title)
			.content(content)
			.rating(rating)
			.postedAt(postedAt)
			.build();
	}
}
