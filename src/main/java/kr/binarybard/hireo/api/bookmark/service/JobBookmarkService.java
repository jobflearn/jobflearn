package kr.binarybard.hireo.api.bookmark.service;

import kr.binarybard.hireo.api.bookmark.domain.JobBookmark;
import kr.binarybard.hireo.api.bookmark.repository.JobBookmarkRepository;
import kr.binarybard.hireo.common.exceptions.ErrorCode;
import kr.binarybard.hireo.common.exceptions.InvalidValueException;
import kr.binarybard.hireo.web.job.repository.JobRepository;
import kr.binarybard.hireo.web.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JobBookmarkService {
	private final JobRepository jobRepository;
	private final JobBookmarkRepository jobBookmarkRepository;
	private final MemberRepository memberRepository;

	public Long bookmark(User user, Long jobId) {
		var foundJob = jobRepository.findByIdOrThrow(jobId);
		var loggedInUser = memberRepository.findByEmailOrThrow(user.getUsername());
		var bookmark = JobBookmark.builder()
			.job(foundJob)
			.member(loggedInUser)
			.build();

		try {
			return jobBookmarkRepository.save(bookmark).getId();
		} catch (DataIntegrityViolationException ex) {
			throw new InvalidValueException(ErrorCode.JOB_BOOKMARK_ALREADY_EXISTS,
				String.format("채용공고 ID: %d, 멤버 ID: %d", jobId, loggedInUser.getId()));
		}
	}

	public void deleteBookmark(User user, Long jobId) {
		var loggedInUser = memberRepository.findByEmailOrThrow(user.getUsername());
		var foundBookmark = jobBookmarkRepository.findByMemberIdAndJobIdOrThrow(loggedInUser.getId(), jobId);
		jobBookmarkRepository.delete(foundBookmark);
	}
}
