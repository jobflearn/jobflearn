package kr.binarybard.hireo.common.fixture;

import kr.binarybard.hireo.api.bookmark.domain.CompanyBookmark;
import kr.binarybard.hireo.api.bookmark.domain.JobBookmark;
import kr.binarybard.hireo.api.bookmark.dto.CompanyBookmarkResponse;
import kr.binarybard.hireo.api.bookmark.dto.JobBookmarkResponse;
import org.springframework.test.util.ReflectionTestUtils;

public class BookmarkFixture {

	public static final CompanyBookmarkResponse COMPANY_BOOKMARK_RESPONSE = CompanyBookmarkResponse.builder()
		.id(1L)
		.companyId(CompanyFixture.createTestCompanyA().getId())
		.build();

	public static final JobBookmarkResponse JOB_BOOKMARK_RESPONSE = JobBookmarkResponse.builder()
		.id(1L)
		.jobId(JobFixture.createDataScientistJob().getId())
		.build();

	public static CompanyBookmark createCompanyBookmark() {
		return CompanyBookmark.builder()
			.company(CompanyFixture.createTestCompanyA())
			.member(MemberFixture.createMember())
			.build();
	}

	public static CompanyBookmark createCompanyBookmarkWithId(Long id) {
		var bookmark = createCompanyBookmark();
		ReflectionTestUtils.setField(bookmark, "id", id);
		return bookmark;
	}

	public static JobBookmark createJobBookmark() {
		return JobBookmark.builder()
			.job(JobFixture.createDataScientistJob())
			.member(MemberFixture.createMember())
			.build();
	}

	public static JobBookmark createJobBookmarkWithId(Long id) {
		var bookmark = createJobBookmark();
		ReflectionTestUtils.setField(bookmark, "id", id);
		return bookmark;
	}

}

