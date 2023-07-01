package kr.binarybard.hireo.web.job.controller;

import kr.binarybard.hireo.common.CurrentUser;
import kr.binarybard.hireo.web.job.service.JobService;
import kr.binarybard.hireo.web.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/jobs")
@RequiredArgsConstructor
public class JobBrowseController {
	private final MemberService memberService;
	private final JobService jobService;

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
}
