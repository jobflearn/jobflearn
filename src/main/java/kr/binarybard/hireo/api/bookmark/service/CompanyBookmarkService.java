package kr.binarybard.hireo.api.bookmark.service;

import kr.binarybard.hireo.api.bookmark.domain.CompanyBookmark;
import kr.binarybard.hireo.api.bookmark.repository.CompanyBookmarkRepository;
import kr.binarybard.hireo.common.exceptions.ErrorCode;
import kr.binarybard.hireo.common.exceptions.InvalidValueException;
import kr.binarybard.hireo.web.company.repository.CompanyRepository;
import kr.binarybard.hireo.web.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanyBookmarkService {
	private final MemberRepository memberRepository;
	private final CompanyRepository companyRepository;
	private final CompanyBookmarkRepository companyBookmarkRepository;

	public Long bookmark(User user, Long companyId) {
		var company = companyRepository.findByIdOrThrow(companyId);
		var loggedInUser = memberRepository.findByEmailOrThrow(user.getUsername());
		var bookmark = CompanyBookmark.builder()
			.company(company)
			.member(loggedInUser)
			.build();

		try {
			return companyBookmarkRepository.save(bookmark).getId();
		} catch (DataIntegrityViolationException ex) {
			throw new InvalidValueException(ErrorCode.COMPANY_BOOKMARK_ALREADY_EXISTS,
				String.format("회사 ID: %d, 멤버 ID: %d", companyId, loggedInUser.getId()));
		}
	}

	public void deleteBookmark(User user, Long companyId) {
		var loggedInUser = memberRepository.findByEmailOrThrow(user.getUsername());
		var foundBookmark = companyBookmarkRepository.findByMemberIdAndCompanyIdOrThrow(loggedInUser.getId(), companyId);
		companyBookmarkRepository.delete(foundBookmark);
	}
}
