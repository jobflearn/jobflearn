package kr.binarybard.hireo.web.account.dto;

import java.util.List;

import kr.binarybard.hireo.api.bookmark.dto.CompanyBookmarkResponse;
import kr.binarybard.hireo.api.bookmark.dto.JobBookmarkResponse;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountResponse {
	private String email;

	private List<CompanyBookmarkResponse> companyBookmarks;

	private List<JobBookmarkResponse> jobBookmarks;

	public boolean isCompanyBookmarked(Long companyId) {
		return companyBookmarks.stream()
			.filter(bookmark -> bookmark.getCompanyId() != null)
			.anyMatch(bookmark -> bookmark.getCompanyId().equals(companyId));
	}

	public boolean isJobBookmarked(Long jobId) {
		return jobBookmarks.stream()
			.filter(bookmark -> bookmark.getJobId() != null)
			.anyMatch(bookmark -> bookmark.getJobId().equals(jobId));
	}
}
