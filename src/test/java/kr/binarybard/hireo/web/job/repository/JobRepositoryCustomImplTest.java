package kr.binarybard.hireo.web.job.repository;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import kr.binarybard.hireo.web.job.dto.JobListResponse;

@SpringBootTest
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
		Assertions.assertThat(content.size()).isEqualTo(4);
		Assertions.assertThat(jobListResponses.getNumber()).isEqualTo(0);
	}
}
