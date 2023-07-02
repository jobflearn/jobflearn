package kr.binarybard.hireo.web.job.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import kr.binarybard.hireo.common.exceptions.EntityNotFoundException;
import kr.binarybard.hireo.common.exceptions.ErrorCode;
import kr.binarybard.hireo.web.job.domain.Job;

public interface JobRepository
	extends CrudRepository<Job, Long>, PagingAndSortingRepository<Job, Long>, JobRepositoryCustom {

	default Job findByIdOrThrow(Long id) {
		return findById(id)
			.orElseThrow(() -> new EntityNotFoundException(ErrorCode.JOB_NOT_FOUND));
	}
}
