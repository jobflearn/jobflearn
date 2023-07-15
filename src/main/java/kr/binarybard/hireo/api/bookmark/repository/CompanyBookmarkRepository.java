package kr.binarybard.hireo.api.bookmark.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.binarybard.hireo.api.bookmark.domain.CompanyBookmark;
import kr.binarybard.hireo.common.exceptions.EntityNotFoundException;
import kr.binarybard.hireo.common.exceptions.ErrorCode;

public interface CompanyBookmarkRepository extends JpaRepository<CompanyBookmark, Long> {

	Optional<CompanyBookmark> findByAccountIdAndCompanyId(Long accountId, Long companyId);

	default CompanyBookmark findByAccountIdAndCompanyIdOrThrow(Long accountId, Long companyId) {
		return findByAccountIdAndCompanyId(accountId, companyId)
			.orElseThrow(() -> new EntityNotFoundException(ErrorCode.COMPANY_BOOKMARK_NOT_FOUND));
	}
}
