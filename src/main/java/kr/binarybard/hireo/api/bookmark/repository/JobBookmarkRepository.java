package kr.binarybard.hireo.api.bookmark.repository;

import kr.binarybard.hireo.api.bookmark.domain.JobBookmark;
import kr.binarybard.hireo.common.exceptions.EntityNotFoundException;
import kr.binarybard.hireo.common.exceptions.ErrorCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JobBookmarkRepository extends JpaRepository<JobBookmark, Long> {

	Optional<JobBookmark> findByMemberIdAndJobId(Long memberId, Long jobId);

	default JobBookmark findByMemberIdAndJobIdOrThrow(Long memberId, Long jobId) {
		return findByMemberIdAndJobId(memberId, jobId)
			.orElseThrow(() -> new EntityNotFoundException(ErrorCode.JOB_BOOKMARK_NOT_FOUND));
	}
}
