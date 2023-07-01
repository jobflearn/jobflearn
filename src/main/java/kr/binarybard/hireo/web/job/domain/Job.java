package kr.binarybard.hireo.web.job.domain;

import jakarta.persistence.*;
import kr.binarybard.hireo.common.BaseTimeEntity;
import kr.binarybard.hireo.web.company.domain.Company;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Job extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "job_id")
	private Long id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private JobType jobType;

	@Column(nullable = false)
	private int startSalary;

	@Column(nullable = false)
	private int endSalary;

	private String description;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private Category category;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "company_id", nullable = false)
	private Company company;

	@Builder
	public Job(String name, JobType jobType, int startSalary, int endSalary, String description, Category category, Company company) {
		this.name = name;
		this.jobType = jobType;
		this.startSalary = startSalary;
		this.endSalary = endSalary;
		this.description = description;
		this.category = category;
		this.company = company;
	}
}
