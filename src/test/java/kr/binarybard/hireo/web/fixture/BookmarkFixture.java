package kr.binarybard.hireo.web.fixture;

import kr.binarybard.hireo.api.bookmark.domain.Bookmark;
import kr.binarybard.hireo.api.bookmark.dto.BookmarkResponse;
import org.springframework.test.util.ReflectionTestUtils;

public class BookmarkFixture {

	public static Bookmark createBookmark() {
		return Bookmark.builder()
			.company(CompanyFixture.TEST_COMPANY)
			.member(MemberFixture.createMember())
			.build();
	}

	public static Bookmark createBookmarkWithId(Long id) {
		var bookmark = createBookmark();
		ReflectionTestUtils.setField(bookmark, "id", id);
		return bookmark;
	}

	public static final BookmarkResponse BOOKMARK_RESPONSE = BookmarkResponse.builder()
		.id(1L)
		.companyId(CompanyFixture.TEST_COMPANY.getId())
		.build();
}

