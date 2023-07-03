package kr.binarybard.hireo.api.bookmark.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookmarkResponse {
	private Long id;

	private Long companyId;

	private Long jobId;
}
