package kr.binarybard.hireo.auth.controller;

import jakarta.validation.Valid;
import kr.binarybard.hireo.auth.dto.SignUpRequest;
import kr.binarybard.hireo.exception.DuplicateEmailException;
import kr.binarybard.hireo.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class LoginController {
	private final MemberService memberService;

	@GetMapping("/new")
	public String registerForm(Model model) {
		var member = SignUpRequest.builder()
			.build();
		model.addAttribute("member", member);
		return "new";
	}

	@PostMapping("/new")
	public String registerMember(@Valid @ModelAttribute("member") SignUpRequest memberDto,
		BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			log.info("bindingResult has errors: {}", bindingResult);
			return "new";
		}

		try {
			memberService.save(memberDto);
		} catch (DuplicateEmailException e) {
			log.error("Failed to create account", e);
			bindingResult.reject("exists.email");
			return "new";
		}
		return "redirect:/auth/login";
	}

	@GetMapping("/login")
	public String loginForm() {
		return "login";
	}
}