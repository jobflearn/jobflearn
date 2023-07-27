package kr.binarybard.hireo.web.review.dto;

import java.time.LocalDate;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import jakarta.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewRequest {

	@NotEmpty
	@Length(min = 1, max = 32)
	private String title;

	@Range(min = 1, max = 5)
	private int rating;

	@NotEmpty
	@Length(min = 10, max = 150)
	private String content;

	private LocalDate postedAt;

	@Builder
	public ReviewRequest(String title, int rating, String content, LocalDate postedAt) {
		this.title = title;
		this.rating = rating;
		this.content = content;
		this.postedAt = postedAt;
	}
}
