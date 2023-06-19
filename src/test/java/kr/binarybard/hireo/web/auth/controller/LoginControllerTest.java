package kr.binarybard.hireo.web.auth.controller;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class LoginControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	@DisplayName("회원가입 폼 요청")
	public void registerFormTest() throws Exception {
		mockMvc.perform(get("/auth/new"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(view().name("new"))
			.andExpect(model().attributeExists("member"));
	}

	@Test
	@DisplayName("회원가입 요청")
	public void registerMemberTest() throws Exception {
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

}
