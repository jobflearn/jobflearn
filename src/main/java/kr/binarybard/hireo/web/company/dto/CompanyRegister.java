package kr.binarybard.hireo.web.company.dto;

import org.springframework.web.multipart.MultipartFile;

import kr.binarybard.hireo.web.company.domain.Industry;
import kr.binarybard.hireo.web.location.dto.LocationDto;
import lombok.Builder;
import lombok.Data;

@Data
public class CompanyRegister {
	private String name;
	private String description;
	private LocationDto locationDto;
	private Boolean isVerified;
	private Industry industry;
	private MultipartFile companyLogo;

	@Builder
	public CompanyRegister(String name, String description, LocationDto locationDto, Boolean isVerified,
		Industry industry,
		MultipartFile companyLogo) {
		this.name = name;
		this.description = description;
		this.locationDto = locationDto;
		this.isVerified = isVerified;
		this.industry = industry;
		this.companyLogo = companyLogo;
	}
}
