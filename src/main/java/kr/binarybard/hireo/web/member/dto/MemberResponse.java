package kr.binarybard.hireo.web.member.dto;

import kr.binarybard.hireo.api.bookmark.dto.BookmarkResponse;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class MemberResponse {
	private String email;

	private List<BookmarkResponse> bookmarks;

	public boolean isCompanyBookmarked(Long companyId) {
		return bookmarks.stream()
			.filter(bookmark -> bookmark.getCompanyId() != null)
			.anyMatch(bookmark -> bookmark.getCompanyId().equals(companyId));
	}
}
