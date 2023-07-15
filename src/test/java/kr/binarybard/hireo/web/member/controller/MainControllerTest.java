package kr.binarybard.hireo.web.account.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import kr.binarybard.hireo.common.AcceptanceTest;

class MainControllerTest extends AcceptanceTest {

	@Test
	@DisplayName("메인 페이지를 성공적으로 불러온다.")
	void testIndex() throws Exception {
		mockMvc.perform(get("/"))
			.andExpect(status().isOk())
			.andExpect(view().name("index"))
			.andReturn();
	}
}
