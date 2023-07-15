package kr.binarybard.hireo.api.bookmark.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.binarybard.hireo.api.bookmark.domain.JobBookmark;
import kr.binarybard.hireo.common.exceptions.EntityNotFoundException;
import kr.binarybard.hireo.common.exceptions.ErrorCode;

public interface JobBookmarkRepository extends JpaRepository<JobBookmark, Long> {

	Optional<JobBookmark> findByAccountIdAndJobId(Long accountId, Long jobId);

	default JobBookmark findByAccountIdAndJobIdOrThrow(Long accountId, Long jobId) {
		return findByAccountIdAndJobId(accountId, jobId)
			.orElseThrow(() -> new EntityNotFoundException(ErrorCode.JOB_BOOKMARK_NOT_FOUND));
	}
}
