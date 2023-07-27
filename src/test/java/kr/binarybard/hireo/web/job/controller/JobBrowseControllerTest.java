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
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.binarybard.hireo.common.fixture.AccountFixture;
import kr.binarybard.hireo.common.fixture.JobFixture;
import kr.binarybard.hireo.web.account.dto.AccountResponse;
import kr.binarybard.hireo.web.account.service.AccountService;
import kr.binarybard.hireo.web.job.dto.JobListResponse;
import kr.binarybard.hireo.web.job.dto.JobResponse;
import kr.binarybard.hireo.web.job.dto.JobSearchCondition;
import kr.binarybard.hireo.web.job.service.JobService;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = AccountFixture.TEST_EMAIL)
class JobBrowseControllerTest {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private JobService jobService;

	@MockBean
	private AccountService accountService;

	@Test
	@DisplayName("채용공고 상세 페이지를 조회한다.")
	void testJobController() throws Exception {
		JobResponse jobResponse = JobFixture.createDataScientistJobResponse();
		AccountResponse accountResponse = AccountFixture.ACCOUNT_RESPONSE;

		when(jobService.findOne(1L)).thenReturn(jobResponse);
		when(accountService.findByEmail(AccountFixture.TEST_EMAIL)).thenReturn(accountResponse);

		mockMvc.perform(get("/jobs/1"))
			.andExpect(status().isOk());
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
			.andExpect(jsonPath("$.content").isArray())
			.andExpect(jsonPath("$.number").value(jobListByPage.getNumber()))
			.andExpect(jsonPath("$.size").value(4));
	}

	@Test
	@DisplayName("페이지 단위로 조건을 추가해 조회한다.")
	void jobSearchConditionTest() throws Exception {
		//given
		Page<JobListResponse> jobListByPage = JobFixture.createJobListByPage(0, 4);
		JobSearchCondition jobSearchCondition = JobFixture.createJobSearchCondition();
		when(jobService.findByPageWithCondition(any(JobSearchCondition.class), any(Pageable.class))).thenReturn(
			jobListByPage);
		ResultActions perform = mockMvc.perform(get("/jobs/search")
			.param("locationDto.latitude", String.valueOf(jobSearchCondition.getLocationDto().getLatitude()))
			.param("locationDto.longitude", String.valueOf(jobSearchCondition.getLocationDto().getLongitude()))
			.param("keyword", jobSearchCondition.getKeyword())
			.param("category", jobSearchCondition.getCategory().name())
			.param("jobType", jobSearchCondition.getJobType().name())
			.param("minSalary", String.valueOf(jobSearchCondition.getMinSalary()))
			.param("maxSalary", String.valueOf(jobSearchCondition.getMaxSalary())));

		//expected
		perform.andExpect(status().isOk())
			.andExpect(jsonPath("$.content").isArray())
			.andExpect(jsonPath("$.number").value(jobListByPage.getNumber()))
			.andExpect(jsonPath("$.size").value(4));
	}

}
