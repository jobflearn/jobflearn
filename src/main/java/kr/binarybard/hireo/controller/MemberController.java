package kr.binarybard.hireo.controller;

import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.binarybard.hireo.domain.Member;
import kr.binarybard.hireo.service.LoginService;
import kr.binarybard.hireo.service.MemberService;
import lombok.RequiredArgsConstructor;

@Controller("members")
@RequiredArgsConstructor
public class MemberController {
	private final MemberService memberService;
	private final LoginService loginService;

	@GetMapping("/new-form")
	public String registerForm(){
		return "new-form";
	}

	@PostMapping("/register")
	public String registerMember(@ModelAttribute Member member){
		memberService.join(member);
		return "redirect:/home";
	}

	@GetMapping("login-form")
	public String loginForm(){
		return "login-form";
	}

	@PostMapping("/login")
	public String login(@RequestParam String email, @RequestParam String password, HttpServletRequest request, Model model){
		Member member = Member.builder().email(email).password(password).build();
		if(loginService.ValidateMember(member)){
			HttpSession session = request.getSession();
			session.setAttribute("email", email);
			Member loginnedMember = memberService.findMemberByEmail(email);
			model.addAttribute("member", loginnedMember);
			return "/home";
		}else{
			return "redirect:/login-form";
		}
	}

	@PostMapping("/logout")
	public String logout(HttpSession httpSession){
		httpSession.invalidate();
		return "/login-form";
	}
}
