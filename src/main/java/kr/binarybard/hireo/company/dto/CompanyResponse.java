package kr.binarybard.hireo.company.dto;

import kr.binarybard.hireo.location.dto.LocationDto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CompanyResponse {
	private String name;
	private String description;
	private LocationDto locationDto;
}
