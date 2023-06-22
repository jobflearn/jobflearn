package kr.binarybard.hireo.api.bookmark.service;

import kr.binarybard.hireo.api.bookmark.domain.Bookmark;
import kr.binarybard.hireo.api.bookmark.repository.BookmarkRepository;
import kr.binarybard.hireo.web.company.repository.CompanyRepository;
import kr.binarybard.hireo.web.member.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static kr.binarybard.hireo.web.fixture.BookmarkFixture.createBookmark;
import static kr.binarybard.hireo.web.fixture.BookmarkFixture.createBookmarkWithId;
import static kr.binarybard.hireo.web.fixture.CompanyFixture.EXISTING_COMPANY_ID;
import static kr.binarybard.hireo.web.fixture.CompanyFixture.TEST_COMPANY;
import static kr.binarybard.hireo.web.fixture.MemberFixture.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookmarkServiceTest {

	@Mock
	private BookmarkRepository bookmarkRepository;

	@Mock
	private CompanyRepository companyRepository;

	@Mock
	private MemberRepository memberRepository;

	@InjectMocks
	private BookmarkService bookmarkService;

	@Test
	@DisplayName("사용자가 회사를 북마크할 수 있다")
	void bookmarkCompany() {
		// given
		when(companyRepository.findByIdOrThrow(anyLong())).thenReturn(TEST_COMPANY);
		when(memberRepository.findByEmailOrThrow(anyString())).thenReturn(createMember());
		when(bookmarkRepository.save(any(Bookmark.class))).thenReturn(createBookmarkWithId(1L));

		// when
		Long bookmarkId = bookmarkService.bookmarkCompany(USER, EXISTING_COMPANY_ID);

		// then
		Assertions.assertThat(bookmarkId).isEqualTo(1L);
	}

	@Test
	@DisplayName("사용자가 회사의 북마크를 삭제할 수 있다")
	void deleteCompanyBookmark() {
		// given
		when(memberRepository.findByEmailOrThrow(anyString())).thenReturn(createMemberWithId(1L));
		when(bookmarkRepository.findByMemberIdAndCompanyIdOrThrow(anyLong(), anyLong())).thenReturn(createBookmarkWithId(1L));

		// when
		bookmarkService.deleteCompanyBookmark(USER, EXISTING_COMPANY_ID);

		// then
		verify(bookmarkRepository).delete(any(Bookmark.class));
	}
}
