package kr.binarybard.hireo.web.job.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.ResultActions;

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import kr.binarybard.hireo.common.fixture.JobFixture;
import kr.binarybard.hireo.common.fixture.MemberFixture;
import kr.binarybard.hireo.web.job.dto.JobListResponse;
import kr.binarybard.hireo.web.job.dto.JobResponse;
import kr.binarybard.hireo.web.job.service.JobService;
import kr.binarybard.hireo.web.member.dto.MemberResponse;
import kr.binarybard.hireo.web.member.service.MemberService;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = MemberFixture.TEST_EMAIL)
class JobBrowseControllerTest {

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

	@Test
	@DisplayName("페이지 단위 구직 공고 조회")
	void searchJobListByPageTest() throws Exception {
		//given
		Page<JobListResponse> jobListByPage = JobFixture.createJobListByPage(0, 4);
		when(jobService.findByPage(any(Pageable.class))).thenReturn(jobListByPage);

		//expected
		mockMvc.perform(MockMvcRequestBuilders.get("/jobs?page=1"))
			.andExpect(status().isOk())
			.andExpect(model().attribute("jobs", jobListByPage))
			.andExpect(view().name("job/joblist"));
	}


	@Test
	@DisplayName("페이지 단위로 조건을 추가해 조회한다.")
	void jobSearchConditionTest() throws Exception {
		//given
		ResultActions perform = mockMvc.perform(get("/jobs/search")
			.param("keyword", "소프트")
			.param("salaryRange", "1500,3500")
		);

		//expected
		perform.andExpect(status().isOk());
	}

}
