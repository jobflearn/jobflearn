package kr.binarybard.hireo.web.location.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LocationCondition {
	private double latitude;
	private double longitude;

	@Builder
	public LocationCondition(double latitude, double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}
}
