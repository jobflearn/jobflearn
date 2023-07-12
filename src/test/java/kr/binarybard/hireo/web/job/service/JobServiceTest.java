package kr.binarybard.hireo.web.job.service;

import static kr.binarybard.hireo.common.fixture.JobFixture.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.support.PageableExecutionUtils;

import kr.binarybard.hireo.common.fixture.JobFixture;
import kr.binarybard.hireo.web.job.domain.Job;
import kr.binarybard.hireo.web.job.dto.JobListResponse;
import kr.binarybard.hireo.web.job.dto.JobMapper;
import kr.binarybard.hireo.web.job.dto.JobResponse;
import kr.binarybard.hireo.web.job.repository.JobRepository;

@ExtendWith(MockitoExtension.class)
class JobServiceTest {
	@Mock
	private JobRepository jobRepository;

	@Mock
	private JobMapper jobMapper;

	@InjectMocks
	private JobService jobService;

	@Test
	@DisplayName("Job 개수 조회 테스트")
	void testCountJobs() {
		// Arrange
		when(jobRepository.count()).thenReturn(10L);

		// Act
		long jobCount = jobService.count();

		// Assert
		assertThat(jobCount).isEqualTo(10L);
		verify(jobRepository, times(1)).count();
	}

	@Test
	@DisplayName("특정 Job 조회 테스트")
	void testFindOne() {
		// given
		Job mockJob = createSoftwareEngineerJob();
		JobResponse mockJobResponse = createSoftwareEngineerJobResponse();
		when(jobRepository.findByIdOrThrow(anyLong())).thenReturn(mockJob);
		when(jobMapper.toDto(mockJob)).thenReturn(mockJobResponse);

		// when
		JobResponse result = jobService.findOne(1L);

		// then
		assertThat(mockJobResponse).isEqualTo(result);
		verify(jobRepository, times(1)).findByIdOrThrow(anyLong());
		verify(jobMapper, times(1)).toDto(mockJob);
	}

	@Test
	@DisplayName("정확한 조건으로 DAO에 요청을 보낼수 있다.")
	void findPageWithConditionTest() throws Exception {
		//given
		PageRequest pageRequest = PageRequest.of(0, 4);
		List<JobListResponse> list = new ArrayList<>();
		list.add(JobFixture.createJobListResposne());
		Page<JobListResponse> jobListResponses = PageableExecutionUtils.getPage(list, pageRequest, list::size);
		when(jobRepository.listJobsWithCondition(createJobSearchCondition(), pageRequest))
			.thenReturn(jobListResponses);
		//when
		Page<JobListResponse> result = jobService.findByPageWithCondition(createJobSearchCondition(),
			pageRequest);
		//then
		assertThat(jobListResponses).isEqualTo(result);
		verify(jobRepository, times(1)).listJobsWithCondition(createJobSearchCondition(), pageRequest);
	}

}
