package kr.binarybard.hireo.web.job.dto;

import java.time.LocalDateTime;

import kr.binarybard.hireo.web.company.dto.CompanyListResponse;
import kr.binarybard.hireo.web.job.domain.JobType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class JobListResponse {
	private Long id;

	private String name;

	private String description;

	private JobType jobType;

	private int startSalary;

	private int endSalary;

	private LocalDateTime postedAt;

	private String elapsedDate;

	private CompanyListResponse company;

	public void cutDescription() {
		String[] splited = this.description.split("\\.");
		this.description = splited[0] + ". " + splited[1] + ".";
	}

	@Builder
	public JobListResponse(Long id, String name, String description, JobType jobType, int startSalary, int endSalary,
		LocalDateTime postedAt, CompanyListResponse company) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.jobType = jobType;
		this.startSalary = startSalary;
		this.endSalary = endSalary;
		this.postedAt = postedAt;
		this.company = company;

	}
}
