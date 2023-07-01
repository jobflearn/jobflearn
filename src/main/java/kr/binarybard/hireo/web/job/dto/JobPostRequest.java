package kr.binarybard.hireo.web.job.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class JobPostRequest {
	@Length(min = 1, max = 32)
	private String name;

	@NotEmpty
	private String type;

	@NotEmpty
	private String category;

	@Min(1)
	private int min;

	@Min(1)
	private int max;

	private Long companyId;
}
