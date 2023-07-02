package kr.binarybard.hireo.web.review.controller;

import jakarta.validation.Valid;
import kr.binarybard.hireo.common.CurrentUser;
import kr.binarybard.hireo.web.review.dto.ReviewRequest;
import kr.binarybard.hireo.web.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ReviewController {
	private final ReviewService reviewService;

	@PostMapping("/companies/{id}/review")
	public String postReview(
		@CurrentUser User user,
		@PathVariable("id") Long companyId,
		@Valid @ModelAttribute("review") ReviewRequest request,
		BindingResult bindingResult
	) {
		if (bindingResult.hasErrors()) {
			log.info("bindingResult has errors: {}", bindingResult);
			return "redirect:/companies/{id}?error";
		}

		reviewService.post(user, companyId, request);
		return "redirect:/companies/{id}";
	}
}
