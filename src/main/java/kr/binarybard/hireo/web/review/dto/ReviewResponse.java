package kr.binarybard.hireo.web.review.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReviewResponse {
	private String name;

	private String title;

	private String content;

	private int rating;

	private LocalDateTime postedAt;
}
