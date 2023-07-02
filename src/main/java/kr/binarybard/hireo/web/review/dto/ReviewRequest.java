package kr.binarybard.hireo.web.review.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import java.time.LocalDate;

@Getter
@Setter
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
