package kr.binarybard.hireo.web.company.controller;

import kr.binarybard.hireo.exception.CompanyNotFoundException;
import kr.binarybard.hireo.web.company.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/companies")
@RequiredArgsConstructor
public class CompanyController {
	private final CompanyService companyService;

	@GetMapping("/{id}")
	public String profile(
		@PathVariable("id") Long id,
		Model model
	) {
		try {
			var foundCompany = companyService.findOne(id);
			model.addAttribute("company", foundCompany);
			return "company/profile";
		} catch (CompanyNotFoundException ex) {
			return "redirect:/error/404";
		}
	}
}
