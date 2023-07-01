package kr.binarybard.hireo.web.job.service;

import kr.binarybard.hireo.web.job.dto.JobMapper;
import kr.binarybard.hireo.web.job.dto.JobResponse;
import kr.binarybard.hireo.web.job.repository.JobRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class JobService {
	private final JobRepository jobRepository;

	private final JobMapper jobMapper;

	@Transactional(readOnly = true)
	public long count() {
		return jobRepository.count();
	}

	@Transactional(readOnly = true)
	public JobResponse findOne(Long id) {
		var foundJob = jobRepository.findByIdOrThrow(id);
		return jobMapper.toDto(foundJob);
	}
}
