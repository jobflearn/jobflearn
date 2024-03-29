package kr.binarybard.hireo.api.bookmark.service;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import kr.binarybard.hireo.api.bookmark.domain.JobBookmark;
import kr.binarybard.hireo.api.bookmark.repository.JobBookmarkRepository;
import kr.binarybard.hireo.common.fixture.AccountFixture;
import kr.binarybard.hireo.common.fixture.BookmarkFixture;
import kr.binarybard.hireo.common.fixture.JobFixture;
import kr.binarybard.hireo.web.account.repository.AccountRepository;
import kr.binarybard.hireo.web.job.domain.Job;
import kr.binarybard.hireo.web.job.repository.JobRepository;

@ExtendWith(MockitoExtension.class)
class JobBookmarkServiceTest {

	@Mock
	private JobBookmarkRepository bookmarkRepository;

	@Mock
	private JobRepository jobRepository;

	@Mock
	private AccountRepository accountRepository;

	@InjectMocks
	private JobBookmarkService bookmarkService;

	private final Job testJob = JobFixture.createDataScientistJob();

	@Test
	@DisplayName("사용자가 채용 공고를 북마크할 수 있다")
	void bookmarkJob() {
		// given
		when(jobRepository.findByIdOrThrow(anyLong())).thenReturn(testJob);
		when(accountRepository.findByEmailOrThrow(anyString())).thenReturn(AccountFixture.createAccount());
		when(bookmarkRepository.save(any(JobBookmark.class))).thenReturn(BookmarkFixture.createJobBookmarkWithId(1L));

		// when
		Long bookmarkId = bookmarkService.bookmark(AccountFixture.USER, testJob.getId());

		// then
		Assertions.assertThat(bookmarkId).isEqualTo(1L);
	}

	@Test
	@DisplayName("사용자가 채용 공고의 북마크를 삭제할 수 있다")
	void deleteJobBookmark() {
		// given
		when(accountRepository.findByEmailOrThrow(anyString())).thenReturn(AccountFixture.createAccountWithId(1L));
		when(bookmarkRepository.findByAccountIdAndJobIdOrThrow(anyLong(), anyLong())).thenReturn(
			BookmarkFixture.createJobBookmarkWithId(1L));

		// when
		bookmarkService.deleteBookmark(AccountFixture.USER, testJob.getId());

		// then
		verify(bookmarkRepository).delete(any(JobBookmark.class));
	}
}
