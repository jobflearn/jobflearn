package kr.binarybard.hireo.location.dto;

import kr.binarybard.hireo.location.domain.Address;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LocationDto {
	private Double latitude;
	private Double longitude;

	private Address address;
	private String countryCode;
}
