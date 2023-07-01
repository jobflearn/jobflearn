package kr.binarybard.hireo.web.job.service;

import kr.binarybard.hireo.common.fixture.JobFixture;
import kr.binarybard.hireo.web.job.domain.Job;
import kr.binarybard.hireo.web.job.dto.JobMapper;
import kr.binarybard.hireo.web.job.dto.JobResponse;
import kr.binarybard.hireo.web.job.repository.JobRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

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
		Job mockJob = JobFixture.createSoftwareEngineerJob();
		JobResponse mockJobResponse = JobFixture.createSoftwareEngineerJobResponse();
		when(jobRepository.findByIdOrThrow(anyLong())).thenReturn(mockJob);
		when(jobMapper.toDto(mockJob)).thenReturn(mockJobResponse);

		// when
		JobResponse result = jobService.findOne(1L);

		// then
		assertThat(mockJobResponse).isEqualTo(result);
		verify(jobRepository, times(1)).findByIdOrThrow(anyLong());
		verify(jobMapper, times(1)).toDto(mockJob);
	}
}
