package kr.binarybard.hireo.web.auth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import kr.binarybard.hireo.common.exceptions.AuthenticationException;
import kr.binarybard.hireo.web.account.domain.AccountType;
import kr.binarybard.hireo.web.account.service.AccountService;
import kr.binarybard.hireo.web.auth.dto.SignUpRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class LoginController {
	private final AccountService accountService;

	@GetMapping("/new")
	public String registerForm(Model model) {
		var account = SignUpRequest.builder()
			.build();
		AccountType[] types = AccountType.values();
		model.addAttribute("types", types);
		model.addAttribute("account", account);
		return "new";
	}

	@PostMapping("/new")
	public String registerAccount(@Valid @ModelAttribute("account") SignUpRequest accountDto,
		BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			log.info("bindingResult has errors: {}", bindingResult);
			return "new";
		}

		try {
			accountService.save(accountDto);
		} catch (AuthenticationException e) {
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
