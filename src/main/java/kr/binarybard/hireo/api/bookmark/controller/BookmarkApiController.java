package kr.binarybard.hireo.api.bookmark.controller;

import kr.binarybard.hireo.api.bookmark.service.CompanyBookmarkService;
import kr.binarybard.hireo.api.bookmark.service.JobBookmarkService;
import kr.binarybard.hireo.common.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequiredArgsConstructor
public class BookmarkApiController {
	private final CompanyBookmarkService companyBookmarkService;
	private final JobBookmarkService jobBookmarkService;

	@PostMapping({"/api/companies/{id}/bookmarks", "/companies/{id}/bookmarks"})
	public ResponseEntity<Void> bookmarkCompany(
		@CurrentUser User user,
		@PathVariable("id") Long companyId
	) {
		Long bookmarkId = companyBookmarkService.bookmark(user, companyId);
		return ResponseEntity.created(URI.create("/api/companies/" + companyId + "/bookmarks/" + bookmarkId)).build();
	}

	@DeleteMapping({"/api/companies/{id}/bookmarks", "/companies/{id}/bookmarks"})
	public ResponseEntity<Void> deleteCompanyBookmark(
		@CurrentUser User user,
		@PathVariable("id") Long companyId
	) {
		companyBookmarkService.deleteBookmark(user, companyId);
		return ResponseEntity.noContent().build();
	}

	@PostMapping({"/api/jobs/{id}/bookmarks", "/jobs/{id}/bookmarks"})
	public ResponseEntity<Void> bookmarkJob(
		@CurrentUser User user,
		@PathVariable("id") Long jobId
	) {
		Long bookmarkId = jobBookmarkService.bookmark(user, jobId);
		return ResponseEntity.created(URI.create("/api/jobs/" + jobId + "/bookmarks/" + bookmarkId)).build();
	}

	@DeleteMapping({"/api/jobs/{id}/bookmarks", "/jobs/{id}/bookmarks"})
	public ResponseEntity<Void> deleteJobBookmark(
		@CurrentUser User user,
		@PathVariable("id") Long jobId
	) {
		jobBookmarkService.deleteBookmark(user, jobId);
		return ResponseEntity.noContent().build();
	}
}
