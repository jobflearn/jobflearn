package kr.binarybard.hireo.web.review.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import kr.binarybard.hireo.web.account.repository.AccountRepository;
import kr.binarybard.hireo.web.company.repository.CompanyRepository;
import kr.binarybard.hireo.web.review.domain.Review;
import kr.binarybard.hireo.web.review.dto.ReviewRequest;
import kr.binarybard.hireo.web.review.dto.ReviewRequestMapper;
import kr.binarybard.hireo.web.review.dto.ReviewResponse;
import kr.binarybard.hireo.web.review.dto.ReviewResponseMapper;
import kr.binarybard.hireo.web.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewService {
	private final ReviewRepository reviewRepository;
	private final CompanyRepository companyRepository;
	private final AccountRepository accountRepository;

	private final ReviewRequestMapper reviewPostRequestMapper;
	private final ReviewResponseMapper reviewResponseMapper;

	public Long post(User user, Long companyId, ReviewRequest request) {
		var foundCompany = companyRepository.findByIdOrThrow(companyId);
		var foundAccount = accountRepository.findByEmailOrThrow(user.getUsername());

		var review = reviewPostRequestMapper.toEntity(request);
		review.assignAuthor(foundAccount);
		review.associateWithCompany(foundCompany);

		return reviewRepository.save(review).getId();
	}

	public Page<ReviewResponse> getReviewsByCompanyId(Long companyId, int page) {
		Page<Review> reviews = reviewRepository.findAllByCompanyId(companyId, PageRequest.of(Math.max(0, page - 1), 3));
		return reviews.map(reviewResponseMapper::toDto);
	}
}
