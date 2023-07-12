package kr.binarybard.hireo.api.bookmark.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JobBookmarkResponse {
	private Long id;

	private Long jobId;
}
