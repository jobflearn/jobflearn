package kr.binarybard.hireo.api.docs;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.snippet.Attributes;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.binarybard.hireo.common.fixture.AccountFixture;
import kr.binarybard.hireo.common.fixture.JobFixture;
import kr.binarybard.hireo.web.account.service.AccountService;
import kr.binarybard.hireo.web.job.dto.JobListResponse;
import kr.binarybard.hireo.web.job.dto.JobSearchCondition;
import kr.binarybard.hireo.web.job.service.JobService;

@WithMockUser(username = AccountFixture.TEST_EMAIL)
class JobBrowseControllerTest extends RestDocsConfiguration {
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private JobService jobService;

	@MockBean
	private AccountService accountService;

	private static Long TEST_JOB_ID = 1L;
	private static Integer DEFAULT_PAGE = 0;
	private static Integer TEST_PAGE_LIMIT = 4;

	@BeforeEach
	void setup() {
		when(jobService.findOne(1L)).thenReturn(JobFixture.createDataScientistJobResponse());
		when(accountService.findByEmail(AccountFixture.TEST_EMAIL)).thenReturn(AccountFixture.ACCOUNT_RESPONSE);
		when(jobService.findByPage(any(Pageable.class)))
			.thenReturn(JobFixture.createJobListByPage(DEFAULT_PAGE, TEST_PAGE_LIMIT));
		when(jobService.findByPageWithCondition(any(JobSearchCondition.class), any(Pageable.class)))
			.thenReturn(JobFixture.createJobListByPage(DEFAULT_PAGE, TEST_PAGE_LIMIT));
	}

	@Test
	@DisplayName("채용공고 상세 페이지를 조회한다.")
	void testJobController() throws Exception {
		mockMvc.perform(RestDocumentationRequestBuilders.get("/jobs/{jobId}", TEST_JOB_ID))
			.andDo(print())
			.andExpect(status().isOk())
			.andDo(document("job-detail",
				pathParameters(
					parameterWithName("jobId").description("채용공고 ID")
				),
				relaxedResponseFields(
					fieldWithPath("id").description("공고 ID"),
					fieldWithPath("name").description("공고 이름"),
					fieldWithPath("description").description("공고 설명"),
					fieldWithPath("startSalary").description("최소 기대 연봉"),
					fieldWithPath("endSalary").description("최고 기대 연봉"),
					fieldWithPath("postedAt").description("기재 일자"),
					fieldWithPath("jobType").description("직무"),
					fieldWithPath("category").description("산업군"),
					fieldWithPath("company").description("회사 정보")
						.attributes(Attributes.key("instructions").value("자세한 내용은 회사 테이블 참조"))
				)
			));
	}

	@Test
	@DisplayName("페이지 단위 구직 공고 조회")
	void searchJobListByPageTest() throws Exception {

		mockMvc.perform(RestDocumentationRequestBuilders.get("/jobs?page={page}", 1))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.content").isArray())
			.andExpect(jsonPath("$.number").value(DEFAULT_PAGE))
			.andExpect(jsonPath("$.size").value(TEST_PAGE_LIMIT))
			.andDo(document("job-list",
				queryParameters(
					parameterWithName("page").description("페이지 번호")),
				relaxedResponseFields(
					fieldWithPath("pageable.pageNumber").description("페이지 번호"),
					fieldWithPath("pageable.pageSize").description("페이지당 공고 수")
				)
			));
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
			.andExpect(jsonPath("$.size").value(4))
			.andDo(document("job-search-condition",
				queryParameters(
					parameterWithName("locationDto.latitude").description("회사 위도"),
					parameterWithName("locationDto.longitude").description("회사 경도"),
					parameterWithName("keyword").description("검색 키워드"),
					parameterWithName("category").description("산업 카테고리"),
					parameterWithName("jobType").description("검색 직무"),
					parameterWithName("minSalary").description("최소 희망 연봉"),
					parameterWithName("maxSalary").description("최대 희망 연봉")
				),
				relaxedResponseFields(
					fieldWithPath("pageable.pageNumber").description("페이지 번호"),
					fieldWithPath("pageable.pageSize").description("페이지당 공고 수")
				)));
	}

}
