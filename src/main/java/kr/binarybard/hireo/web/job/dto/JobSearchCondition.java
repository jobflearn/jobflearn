package kr.binarybard.hireo.web.job.dto;

import kr.binarybard.hireo.web.job.domain.Category;
import kr.binarybard.hireo.web.job.domain.JobType;
import kr.binarybard.hireo.web.location.dto.LocationCondition;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class JobSearchCondition {
	private LocationCondition locationDto;
	private String keyword;
	private Category category;
	private JobType jobType;
	private Integer minSalary;
	private Integer maxSalary;

	@Builder
	public JobSearchCondition(LocationCondition locationDto, String keyword, Category category, JobType jobType,
		Integer minSalary, Integer maxSalary) {
		this.locationDto = locationDto;
		this.keyword = keyword;
		this.category = category;
		this.jobType = jobType;
		this.minSalary = minSalary;
		this.maxSalary = maxSalary;
	}
}
