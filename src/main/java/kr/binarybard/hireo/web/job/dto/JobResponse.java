package kr.binarybard.hireo.web.job.dto;

import kr.binarybard.hireo.web.company.dto.CompanyResponse;
import kr.binarybard.hireo.web.job.domain.Category;
import kr.binarybard.hireo.web.job.domain.JobType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class JobResponse {
	private Long id;

	private String name;

	private String description;

	private JobType jobType;

	private int startSalary;

	private int endSalary;

	private LocalDateTime postedAt;

	private Category category;

	private CompanyResponse company;
}
