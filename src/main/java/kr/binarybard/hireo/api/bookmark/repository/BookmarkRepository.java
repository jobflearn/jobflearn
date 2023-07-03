package kr.binarybard.hireo.api.bookmark.repository;

import kr.binarybard.hireo.api.bookmark.domain.Bookmark;
import kr.binarybard.hireo.common.exceptions.EntityNotFoundException;
import kr.binarybard.hireo.common.exceptions.ErrorCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

	Optional<Bookmark> findByMemberIdAndCompanyId(Long memberId, Long companyId);

	Optional<Bookmark> findByMemberIdAndJobId(Long memberId, Long jobId);

	default Bookmark findByMemberIdAndCompanyIdOrThrow(Long memberId, Long companyId) {
		return findByMemberIdAndCompanyId(memberId, companyId)
			.orElseThrow(() -> new EntityNotFoundException(ErrorCode.COMPANY_BOOKMARK_NOT_FOUND));
	}

	default Bookmark findByMemberIdAndJobIdOrThrow(Long memberId, Long jobId) {
		return findByMemberIdAndJobId(memberId, jobId)
			.orElseThrow(() -> new EntityNotFoundException(ErrorCode.JOB_BOOKMARK_NOT_FOUND));
	}
}
