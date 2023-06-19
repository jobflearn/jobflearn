package kr.binarybard.hireo.web.company.dto;

import kr.binarybard.hireo.web.location.dto.LocationDto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CompanyRegister {
	private String name;
	private String description;
	private LocationDto locationDto;
}
