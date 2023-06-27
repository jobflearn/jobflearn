package kr.binarybard.hireo.web.location.domain;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.util.Assert;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "locations")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Location {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "location_id")
	private Long id;

	private Double latitude;

	private Double longitude;

	@Embedded
	private Address address;

	private String countryCode;

	@Builder
	public Location(Double latitude, Double longitude, Address address, String countryCode) {
		Assert.notNull(latitude, "latitude should not be null");
		Assert.notNull(longitude, "longitude should not be null");
		Assert.notNull(address, "address should not be null");
		Assert.notNull(countryCode, "country code should not be null");

		this.latitude = latitude;
		this.longitude = longitude;
		this.address = address;
		this.countryCode = countryCode;
	}
}
