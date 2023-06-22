package kr.binarybard.hireo.web.company.controller;

import kr.binarybard.hireo.web.member.service.MemberService;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.binarybard.hireo.common.CurrentUser;
import kr.binarybard.hireo.web.company.dto.CompanyRegister;
import kr.binarybard.hireo.web.company.dto.CompanyResponse;
import kr.binarybard.hireo.web.company.service.CompanyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/companies")
@RequiredArgsConstructor
public class CompanyController {
	private final CompanyService companyService;
	private final MemberService memberService;

	@GetMapping("/{id:\\d+}")
	public String profile(
		@CurrentUser User user,
		@PathVariable("id") Long id,
		Model model
	) {
		model.addAttribute("company", companyService.findOne(id));
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
