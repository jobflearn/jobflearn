package kr.binarybard.hireo.web.job.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.binarybard.hireo.utils.DateUtils;
import kr.binarybard.hireo.web.job.dto.JobListResponse;
import kr.binarybard.hireo.web.job.dto.JobResponse;
import kr.binarybard.hireo.web.job.dto.JobSearchCondition;
import kr.binarybard.hireo.web.job.service.JobService;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/jobs")
@RequiredArgsConstructor
public class JobBrowseController {
	private final JobService jobService;

	@GetMapping("/{id}")
	public ResponseEntity<JobResponse> job(@PathVariable Long id) {
		return ResponseEntity.ok(jobService.findOne(id));
	}

	@GetMapping
	public ResponseEntity<Page<JobListResponse>> jobList(@RequestParam(defaultValue = "1") Integer page) {
		Page<JobListResponse> jobListByPage = jobService.findByPage(PageRequest.of(page - 1, 4));
		jobListByPage.stream().forEach(j -> j.setElapsedDate(DateUtils.getElapsedDateTime(j.getPostedAt())));
		return ResponseEntity.ok(jobListByPage);
	}

	@GetMapping("/search")
	public ResponseEntity<Page<JobListResponse>> jobSearchCondition(
		@RequestParam(defaultValue = "1") Integer page,
		@ModelAttribute JobSearchCondition condition
	) {
		return ResponseEntity
			.ok(jobService
				.findByPageWithCondition(
					condition,
					PageRequest.of(page - 1, 4)
				));

	}
}
