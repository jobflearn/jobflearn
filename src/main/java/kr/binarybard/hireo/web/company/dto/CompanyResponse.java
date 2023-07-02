package kr.binarybard.hireo.web.company.dto;

import kr.binarybard.hireo.web.company.domain.Industry;
import kr.binarybard.hireo.web.location.dto.LocationDto;
import lombok.Builder;
import lombok.Data;

@Data
public class CompanyResponse {
	private Long id;
	private String name;
	private String description;
	private String countryName;
	private LocationDto locationDto;
	private Industry industry;
	private String logoHash;
	private CompanyReviewResponse reviews;

	@Builder
	public CompanyResponse(Long id, String name, String description, String countryName, LocationDto locationDto, Industry industry, String logoHash, CompanyReviewResponse reviews) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.countryName = countryName;
		this.locationDto = locationDto;
		this.industry = industry;
		this.logoHash = logoHash;
		this.reviews = reviews;
	}
}
