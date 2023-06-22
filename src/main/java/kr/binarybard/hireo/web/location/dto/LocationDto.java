package kr.binarybard.hireo.web.location.dto;

import kr.binarybard.hireo.web.location.domain.Address;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LocationDto {
	private Double latitude;
	private Double longitude;

	private Address address;
	private String countryCode;

	@Builder
	public LocationDto(Double latitude, Double longitude, Address address, String countryCode) {
		this.latitude = latitude;
		this.longitude = longitude;
		this.address = address;
		this.countryCode = countryCode;
	}
}
