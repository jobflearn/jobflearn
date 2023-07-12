package kr.binarybard.hireo.web.job.dto;

import java.time.LocalDateTime;

import kr.binarybard.hireo.web.company.dto.CompanyListResponse;

import kr.binarybard.hireo.web.job.domain.Category;

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

	private Category category;


	private JobType jobType;

	private int startSalary;

	private int endSalary;

	private LocalDateTime postedAt;

	private String elapsedDate;

	private CompanyListResponse company;

	public String shortenDescription() {
		if (this.description != null && this.description.contains("\\.")) {
			String[] splited = this.description.split("\\.");
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < splited.length || i < 2; i++) {
				sb.append(splited[i]).append(". ");
			}
			return sb.toString();
		}
		return this.description;
	}

	@Builder

	public JobListResponse(Long id, String name, String description, Category category, JobType jobType,
		int startSalary,
		int endSalary, LocalDateTime postedAt, CompanyListResponse company) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.category = category;

		this.jobType = jobType;
		this.startSalary = startSalary;
		this.endSalary = endSalary;
		this.postedAt = postedAt;
		this.company = company;
	}
}
