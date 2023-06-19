package kr.binarybard.hireo.location.domain;

import org.springframework.util.Assert;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "locations")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Location {
	@Id
	@GeneratedValue
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
		Assert.notNull(longitude, "longtitude should not be null");
		Assert.notNull(address, "address should not be null");
		Assert.notNull(countryCode, "country code should not be null");

		this.latitude = latitude;
		this.longitude = longitude;
		this.address = address;
		this.countryCode = countryCode;
	}
}
