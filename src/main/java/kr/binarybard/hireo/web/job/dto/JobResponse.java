package kr.binarybard.hireo.web.job.dto;

import kr.binarybard.hireo.web.company.dto.CompanyResponse;
import kr.binarybard.hireo.web.job.domain.Category;
import kr.binarybard.hireo.web.job.domain.JobType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
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

	@Builder
	public JobResponse(Long id, String name, String description, JobType jobType, int startSalary, int endSalary, LocalDateTime postedAt, Category category, CompanyResponse company) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.jobType = jobType;
		this.startSalary = startSalary;
		this.endSalary = endSalary;
		this.postedAt = postedAt;
		this.category = category;
		this.company = company;
	}
}
