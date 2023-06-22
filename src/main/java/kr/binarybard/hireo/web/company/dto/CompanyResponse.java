package kr.binarybard.hireo.web.company.dto;

import kr.binarybard.hireo.web.company.domain.Industry;
import kr.binarybard.hireo.web.location.dto.LocationDto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CompanyResponse {
	private String name;
	private String description;
	private String countryName;
	private LocationDto locationDto;
	private Industry industry;
}
