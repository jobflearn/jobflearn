package kr.binarybard.hireo.web.company.dto;

import kr.binarybard.hireo.web.review.dto.ReviewResponse;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class CompanyReviewResponse {
	private List<ReviewResponse> reviews;
	private double averageRating = 0;

	@Builder
	public CompanyReviewResponse(List<ReviewResponse> reviews) {
		this.reviews = reviews;
		if (reviews != null) {
			this.averageRating = reviews.stream()
				.mapToDouble(ReviewResponse::getRating)
				.average()
				.orElse(0);
		}
	}
}
