package kr.binarybard.hireo.web.review.controller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.validation.Valid;
import kr.binarybard.hireo.common.CurrentUser;
import kr.binarybard.hireo.web.review.dto.ReviewRequest;
import kr.binarybard.hireo.web.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ReviewController {
	private final ReviewService reviewService;

	@PostMapping("/companies/{id}/review")
	public ResponseEntity<Void> postReview(
		@CurrentUser User user,
		@PathVariable("id") Long companyId,
		@Valid @RequestBody ReviewRequest request
	) {
		Long reviewId = reviewService.post(user, companyId, request);
		String uriString = UriComponentsBuilder.fromPath("/companies/{id}/review/{reviewId}")
			.buildAndExpand(companyId, reviewId)
			.toUriString();
		return ResponseEntity.created(
				URI.create(uriString))
			.build();
	}
}
