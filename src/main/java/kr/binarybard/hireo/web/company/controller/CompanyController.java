package kr.binarybard.hireo.web.company.controller;

import kr.binarybard.hireo.web.review.dto.ReviewResponse;
import kr.binarybard.hireo.web.review.service.ReviewService;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import kr.binarybard.hireo.common.CurrentUser;
import kr.binarybard.hireo.web.company.dto.CompanyRegister;
import kr.binarybard.hireo.web.company.dto.CompanyResponse;
import kr.binarybard.hireo.web.company.service.CompanyService;
import kr.binarybard.hireo.web.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/companies")
@RequiredArgsConstructor
public class CompanyController {
	private final CompanyService companyService;
	private final MemberService memberService;
	private final ReviewService reviewService;

	@GetMapping("/{id:\\d+}")
	public String profile(
		@CurrentUser User user,
		@PathVariable("id") Long id,
		@RequestParam(defaultValue = "1") int page,
		Model model
	) {
		CompanyResponse foundCompany = companyService.findOne(id);
		Page<ReviewResponse> reviews = reviewService.getReviewsByCompanyId(id, page);

		int beginPage = Math.max(0, Math.min(reviews.getNumber() - 2, reviews.getTotalPages() - 5));
		int endPage = Math.min(beginPage + 4, reviews.getTotalPages() - 1);

		model.addAttribute("company", foundCompany);
		model.addAttribute("reviews", reviews);
		model.addAttribute("beginPage", beginPage);
		model.addAttribute("endPage", endPage);
		model.addAttribute("member", memberService.findByEmail(user.getUsername()));
		return "company/profile";
	}

	@GetMapping("/new")
	public String registerForm(Model model) {
		var company = CompanyRegister.builder().build();
		model.addAttribute("company", company);
		return "company/company-form";
	}

	@PostMapping("/new")
	public String registerCompany(@CurrentUser User user, @ModelAttribute CompanyRegister companyRegister,
		Model model) {
		CompanyResponse companyResponse = companyService.registerCompany(companyRegister, user);
		model.addAttribute("company", companyResponse);
		return "redirect:/";
	}
}
