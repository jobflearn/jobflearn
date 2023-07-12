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
		Page<JobListResponse> jobListResponses2 = jobRepository.listJobs(PageRequest.of(1, 4));
		//then
		System.out.println(jobListResponses.getNumber());
		System.out.println(jobListResponses2.getNumber());

		List<JobListResponse> content = jobListResponses.getContent();
		assertThat(content).hasSize(4);
		assertThat(jobListResponses.getNumber()).isZero();
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
		List<JobListResponse> content1 = jobListResponses.getContent();
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
			.salaryRange("3300,9999")
			.build();
		PageRequest pageRequest = PageRequest.of(0, 4);
		//when
		Page<JobListResponse> jobListResponses = jobRepository.listJobsWithCondition(simpleCondition, pageRequest);

		//then
		List<JobListResponse> content = jobListResponses.getContent();
		assertThat(content.stream().findAny()).isPresent();
		assertThat(jobListResponses.getNumber()).isZero();
	}
}
