package kr.binarybard.hireo.web.company.dto;

import kr.binarybard.hireo.web.company.domain.Industry;
import kr.binarybard.hireo.web.location.dto.LocationDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CompanyRegister {
	private String name;
	private String description;
	private LocationDto locationDto;
	private Boolean isVerified;
	private Industry industry;

	@Builder
	public CompanyRegister(String name, String description, LocationDto locationDto, Boolean isVerified,
		Industry industry) {
		this.name = name;
		this.description = description;
		this.locationDto = locationDto;
		this.isVerified = isVerified;
		this.industry = industry;
	}
}
