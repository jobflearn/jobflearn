package kr.binarybard.hireo.web.job.repository;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.Objects;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import kr.binarybard.hireo.common.fixture.JobFixture;
import kr.binarybard.hireo.common.fixture.LocationFixture;
import kr.binarybard.hireo.web.job.domain.Category;
import kr.binarybard.hireo.web.job.domain.JobType;
import kr.binarybard.hireo.web.job.dto.JobListResponse;
import kr.binarybard.hireo.web.job.dto.JobSearchCondition;

@DataJpaTest
class JobRepositoryCustomImplTest {

	@Autowired
	JobRepository jobRepository;

	@Test
	@DisplayName("JobListResponse DTO 페이징 작동 테스트")
	void selectJobListResponseByPage() throws Exception {
		//given
		PageRequest pageRequest = PageRequest.of(0, 4);
		//when
		Page<JobListResponse> jobListResponses = jobRepository.listJobs(pageRequest);
		//then
		List<JobListResponse> content = jobListResponses.getContent();
		assertThat(content).hasSize(4);
		assertThat(jobListResponses.getNumber()).isZero();
	}

	@Test
	@DisplayName("주어진 모든 조건을 만족하는 직업을 찾을수 있다.")
	void listJobsWithAllConditionsTest() throws Exception {
		//given
		JobSearchCondition jobSearchCondition = JobFixture.createJobSearchCondition();
		PageRequest pageRequest = PageRequest.of(0, 4);
		//when
		Page<JobListResponse> jobListResponses = jobRepository.listJobsWithCondition(jobSearchCondition, pageRequest);
		List<JobListResponse> content = jobListResponses.getContent();
		//then
		assertThat(Objects.requireNonNull(content.stream().findAny().orElse(null)).getName()).contains("소프트");
		assertThat(content.stream().findAny().orElse(null).getCategory()).isEqualTo(Category.WEB_SOFT);
		assertThat(Objects.requireNonNull(content.stream().findAny().orElse(null)).getJobType()).isEqualTo(
			JobType.FULLTIME);
	}

	@Test
	@DisplayName("위치 조건에 따른 동적쿼리 페이징 테스트")
	void listJobsWithConditionTest() throws Exception {
		//given
		JobSearchCondition simpleCondition = JobSearchCondition.builder()
			.locationDto(LocationFixture.TEST_LOCATION_CONDITION_1)
			.build();
		PageRequest pageRequest = PageRequest.of(0, 4);
		//when
		Page<JobListResponse> jobListResponses = jobRepository.listJobsWithCondition(simpleCondition, pageRequest);

		//then
		List<JobListResponse> content = jobListResponses.getContent();
		assertThat(content.stream().findAny()).isPresent();
		assertThat(jobListResponses.getNumber()).isZero();
	}

	@Test
	@DisplayName("키워드 조건에 따른 동적쿼리 페이징 테스트")
	void listJobsWithKeywordTest() throws Exception {
		//given
		JobSearchCondition simpleCondition = JobSearchCondition.builder()
			.keyword("소프트")
			.build();
		PageRequest pageRequest = PageRequest.of(0, 4);
		//when
		Page<JobListResponse> jobListResponses = jobRepository.listJobsWithCondition(simpleCondition, pageRequest);
		//then
		List<JobListResponse> content = jobListResponses.getContent();
		assertThat(Objects.requireNonNull(content.stream().findAny().orElse(null)).getName()).contains("소프트");
		assertThat(jobListResponses.getNumber()).isZero();
	}

	@Test
	@DisplayName("직무 조건에 따른 동적쿼리 페이징 테스트")
	void listJobsWithCategoryTest() throws Exception {
		//given
		JobSearchCondition simpleCondition = JobSearchCondition.builder()
			.category(Category.SALE_MAR)
			.build();
		PageRequest pageRequest = PageRequest.of(0, 4);
		//when
		Page<JobListResponse> jobListResponses = jobRepository.listJobsWithCondition(simpleCondition, pageRequest);

		//then
		List<JobListResponse> content = jobListResponses.getContent();
		assertThat(content.stream().findAny().orElse(null).getCategory()).isEqualTo(Category.SALE_MAR);
		assertThat(jobListResponses.getNumber()).isZero();
	}

	@Test
	@DisplayName("근무 조건에 따른 동적쿼리 페이징 테스트")
	void listJobsWithJobTypeTest() throws Exception {
		//given
		JobSearchCondition simpleCondition = JobSearchCondition.builder()
			.jobType(JobType.FULLTIME)
			.build();
		PageRequest pageRequest = PageRequest.of(0, 4);
		//when
		Page<JobListResponse> jobListResponses = jobRepository.listJobsWithCondition(simpleCondition, pageRequest);

		//then
		List<JobListResponse> content = jobListResponses.getContent();
		assertThat(Objects.requireNonNull(content.stream().findAny().orElse(null)).getJobType()).isEqualTo(
			JobType.FULLTIME);
		assertThat(jobListResponses.getNumber()).isZero();
	}

	@Test
	@DisplayName("연봉 조건에 따른 동적쿼리 페이징 테스트")
	void listJobsWithSalaryTest() throws Exception {
		//given
		JobSearchCondition simpleCondition = JobSearchCondition.builder()
			.minSalary(3300)
			.maxSalary(9999)
			.build();
		PageRequest pageRequest = PageRequest.of(0, 4);
		//when
		Page<JobListResponse> jobListResponses = jobRepository.listJobsWithCondition(simpleCondition, pageRequest);

		//then
		List<JobListResponse> content = jobListResponses.getContent();
		assertThat(content.stream().findAny()).isPresent();
		assertThat(jobListResponses.getNumber()).isZero();

	}

	@Test
	@DisplayName("Null 위치 조건에 대한 테스트")
	void testNullLocationCondition() {
		//given
		JobSearchCondition condition = JobSearchCondition.builder().build();
		PageRequest pageRequest = PageRequest.of(0, 4);

		//when
		Page<JobListResponse> jobListResponses = jobRepository.listJobsWithCondition(condition, pageRequest);

		//then
		assertThat(jobListResponses).isNotNull();
	}

	@Test
	@DisplayName("Null 키워드 조건에 대한 테스트")
	void testNullKeywordCondition() {
		//given
		JobSearchCondition condition = JobSearchCondition.builder().build();
		PageRequest pageRequest = PageRequest.of(0, 4);

		//when
		Page<JobListResponse> jobListResponses = jobRepository.listJobsWithCondition(condition, pageRequest);

		//then
		assertThat(jobListResponses).isNotNull();
	}

	@Test
	@DisplayName("Null 카테고리 조건에 대한 테스트")
	void testNullCategoryCondition() {
		//given
		JobSearchCondition condition = JobSearchCondition.builder().build();
		PageRequest pageRequest = PageRequest.of(0, 4);

		//when
		Page<JobListResponse> jobListResponses = jobRepository.listJobsWithCondition(condition, pageRequest);

		//then
		assertThat(jobListResponses).isNotNull();
	}

	@Test
	@DisplayName("Null 직무타입 조건에 대한 테스트")
	void testNullJobTypeCondition() {
		//given
		JobSearchCondition condition = JobSearchCondition.builder().build();
		PageRequest pageRequest = PageRequest.of(0, 4);

		//when
		Page<JobListResponse> jobListResponses = jobRepository.listJobsWithCondition(condition, pageRequest);

		//then
		assertThat(jobListResponses).isNotNull();
	}

	@Test
	@DisplayName("Null 연봉 조건에 대한 테스트")
	void testNullSalaryCondition() {
		//given
		JobSearchCondition condition = JobSearchCondition.builder().build();
		PageRequest pageRequest = PageRequest.of(0, 4);

		//when
		Page<JobListResponse> jobListResponses = jobRepository.listJobsWithCondition(condition, pageRequest);

		//then
		assertThat(jobListResponses).isNotNull();
	}
}
