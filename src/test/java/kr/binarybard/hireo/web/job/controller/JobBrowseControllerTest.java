package kr.binarybard.hireo.web.job.controller;

import kr.binarybard.hireo.common.fixture.JobFixture;
import kr.binarybard.hireo.common.fixture.MemberFixture;
import kr.binarybard.hireo.web.job.dto.JobResponse;
import kr.binarybard.hireo.web.job.service.JobService;
import kr.binarybard.hireo.web.member.dto.MemberResponse;
import kr.binarybard.hireo.web.member.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = MemberFixture.TEST_EMAIL)
class JobBrowseControllerIntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private JobService jobService;

	@MockBean
	private MemberService memberService;

	@Test
	@DisplayName("채용공고 상세 페이지를 조회한다.")
	void testJobController() throws Exception {
		JobResponse jobResponse = JobFixture.createDataScientistJobResponse();
		MemberResponse memberResponse = MemberFixture.MEMBER_RESPONSE;

		when(jobService.findOne(1L)).thenReturn(jobResponse);
		when(memberService.findByEmail(MemberFixture.TEST_EMAIL)).thenReturn(memberResponse);

		mockMvc.perform(get("/jobs/1"))
			.andExpect(status().isOk())
			.andExpect(model().attribute("company", jobResponse.getCompany()))
			.andExpect(model().attribute("job", jobResponse))
			.andExpect(model().attribute("member", memberResponse))
			.andExpect(view().name("job/info"));
	}
}
