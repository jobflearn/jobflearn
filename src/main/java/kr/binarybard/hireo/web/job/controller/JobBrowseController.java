package kr.binarybard.hireo.web.job.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.binarybard.hireo.common.CurrentUser;
import kr.binarybard.hireo.utils.DateUtils;

import kr.binarybard.hireo.web.job.domain.JobType;
import kr.binarybard.hireo.web.job.dto.JobListResponse;
import kr.binarybard.hireo.web.job.dto.JobSearchCondition;

import kr.binarybard.hireo.web.job.service.JobService;
import kr.binarybard.hireo.web.member.service.MemberService;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/jobs")
@RequiredArgsConstructor
public class JobBrowseController {
	private final MemberService memberService;
	private final JobService jobService;

	@ModelAttribute("jobTypes")
	public JobType[] itemTypes() {
		/*배열로 넘겨준다.*/
		return JobType.values();
	}

	@GetMapping("/{id}")
	public String job(
		@CurrentUser User user,
		Model model,
		@PathVariable Long id
	) {
		var foundJob = jobService.findOne(id);
		model.addAttribute("company", foundJob.getCompany());
		model.addAttribute("job", foundJob);
		model.addAttribute("member", memberService.findByEmail(user.getUsername()));
		return "job/info";
	}

	@GetMapping
	public String jobList(@RequestParam(defaultValue = "1") Integer page, Model model) {
		Page<JobListResponse> jobListByPage = jobService.findByPage(PageRequest.of(page - 1, 4));
		jobListByPage.stream().forEach(j -> j.setElapsedDate(DateUtils.getElapsedDateTime(j.getPostedAt())));
		model.addAttribute("jobs", jobListByPage);
		model.addAttribute("condition", new JobSearchCondition());
		return "job/joblist";
	}

	@GetMapping("/search")
	public String jobSearchCondition(
		@RequestParam(defaultValue = "1") Integer page,
		@ModelAttribute JobSearchCondition condition,
		Model model) {
		Page<JobListResponse> filteredList = jobService.findByPageWithCondition(condition,
			PageRequest.of(page - 1, 4));
		model.addAttribute("jobs", filteredList);
		model.addAttribute("condition", new JobSearchCondition());
		return "job/joblist";
	}
}
