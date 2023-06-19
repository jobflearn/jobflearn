package kr.binarybard.hireo.web.auth.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import kr.binarybard.hireo.exception.DuplicateEmailException;
import kr.binarybard.hireo.web.member.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class LoginControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private MemberService memberService;

	@Test
	@DisplayName("회원가입 폼 요청")
	void registerFormTest() throws Exception {
		mockMvc.perform(get("/auth/new"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(view().name("new"))
			.andExpect(model().attributeExists("member"));
	}

	@Test
	@DisplayName("회원가입 요청")
	void registerMemberTest() throws Exception {
		mockMvc.perform(post("/auth/new")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("email", "test@test.com")
				.param("password", "password123")
				.param("passwordConfirm", "password123")
				.param("name", "testUser")
				.param("role", "FREELANCER"))
			.andDo(print())
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/auth/login"));
	}

	@Test
	@DisplayName("회원가입 요청 - 이메일 중복")
	void registerMemberWithDuplicateEmailTest() throws Exception {
		when(memberService.save(any())).thenThrow(new DuplicateEmailException("Duplicate email"));

		mockMvc.perform(post("/auth/new")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("email", "test@test.com")
				.param("password", "password123")
				.param("passwordConfirm", "password123")
				.param("name", "testUser")
				.param("role", "FREELANCER"))
			.andExpect(status().isOk())
			.andExpect(view().name("new"));
	}

	@Test
	@DisplayName("회원가입 요청 - BindingResult 에러")
	void registerMemberWithBindingResultError() throws Exception {
		mockMvc.perform(post("/auth/new")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("email", "test@test.com")
				.param("password", "")
				.param("passwordConfirm", "")
				.param("name", "testUser")
				.param("role", "FREELANCER"))
			.andExpect(status().isOk())
			.andExpect(view().name("new"));
	}

	@Test
	@DisplayName("로그인 폼 요청")
	void loginFormTest() throws Exception {
		mockMvc.perform(get("/auth/login"))
			.andExpect(status().isOk())
			.andExpect(view().name("login"));
	}
}
