package kr.binarybard.hireo.web.job.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import kr.binarybard.hireo.web.job.dto.JobListResponse;
import kr.binarybard.hireo.web.job.dto.JobSearchCondition;

public interface JobRepositoryCustom {

	Page<JobListResponse> listJobs(Pageable pageable);

	Page<JobListResponse> listJobsWithCondition(JobSearchCondition condition, Pageable pageable);
}
