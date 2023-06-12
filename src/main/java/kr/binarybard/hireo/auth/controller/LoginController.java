package kr.binarybard.hireo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kr.binarybard.hireo.domain.Member;
import kr.binarybard.hireo.domain.MemberDto;
import kr.binarybard.hireo.member.service.LoginService;
import kr.binarybard.hireo.member.service.MemberService;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/members")
@RequiredArgsConstructor
public class AuthenticationController {
	private final MemberService memberService;
	private final LoginService loginService;

	@GetMapping("/new-form")
	public String registerForm() {
		return "new-form";
	}

	@PostMapping("/register")
	public String registerMember(@ModelAttribute MemberDto memberDto) {
		memberService.join(memberDto);
		return "redirect:/home";
	}

	@GetMapping("login-form")
	public String loginForm() {
		return "login-form";
	}

	@PostMapping("/login")
	public String login(@ModelAttribute MemberDto memberDto, HttpServletRequest request, Model model) {
		if (!loginService.isAuthenticated(memberDto)) {
			return "redirect:/login-form";
		} else {
			HttpSession session = request.getSession();
			session.setAttribute("email", memberDto.getEmail());
			Member loginnedMember = memberService.findMemberByEmail(memberDto.getEmail());
			model.addAttribute("member", loginnedMember);
			return "/home";
		}
	}

	@PostMapping("/logout")
	public String logout(HttpSession httpSession) {
		httpSession.invalidate();
		return "/login-form";
	}
}
