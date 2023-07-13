package kr.binarybard.hireo.api.bookmark.service;

import kr.binarybard.hireo.api.bookmark.domain.CompanyBookmark;
import kr.binarybard.hireo.api.bookmark.repository.CompanyBookmarkRepository;
import kr.binarybard.hireo.common.fixture.BookmarkFixture;
import kr.binarybard.hireo.common.fixture.CompanyFixture;
import kr.binarybard.hireo.common.fixture.MemberFixture;
import kr.binarybard.hireo.web.company.domain.Company;

import kr.binarybard.hireo.web.company.repository.CompanyRepository;
import kr.binarybard.hireo.web.member.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CompanyBookmarkServiceTest {

	@Mock
	private CompanyBookmarkRepository bookmarkRepository;

	@Mock
	private CompanyRepository companyRepository;

	@Mock
	private MemberRepository memberRepository;

	@InjectMocks
	private CompanyBookmarkService bookmarkService;

	private final Company testCompanyA = CompanyFixture.createTestCompanyA();

	@Test
	@DisplayName("사용자가 회사를 북마크할 수 있다")
	void bookmarkCompany() {
		// given
		when(companyRepository.findByIdOrThrow(anyLong())).thenReturn(testCompanyA);
		when(memberRepository.findByEmailOrThrow(anyString())).thenReturn(MemberFixture.createMember());
		when(bookmarkRepository.save(any(CompanyBookmark.class))).thenReturn(BookmarkFixture.createCompanyBookmarkWithId(1L));

		// when
		Long bookmarkId = bookmarkService.bookmark(MemberFixture.USER, testCompanyA.getId());

		// then
		Assertions.assertThat(bookmarkId).isEqualTo(1L);
	}

	@Test
	@DisplayName("사용자가 회사의 북마크를 삭제할 수 있다")
	void deleteCompanyBookmark() {
		// given
		when(memberRepository.findByEmailOrThrow(anyString())).thenReturn(MemberFixture.createMemberWithId(1L));
		when(bookmarkRepository.findByMemberIdAndCompanyIdOrThrow(anyLong(), anyLong())).thenReturn(BookmarkFixture.createCompanyBookmarkWithId(1L));

		// when
		bookmarkService.deleteBookmark(MemberFixture.USER, testCompanyA.getId());

		// then
		verify(bookmarkRepository).delete(any(CompanyBookmark.class));
	}
}
