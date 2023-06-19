package kr.binarybard.hireo.location.domain;

import org.springframework.util.Assert;

import jakarta.persistence.Embeddable;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
public class Address {
	private String province;
	private String city;
	private String street;

	@Builder
	public Address(String province, String city, String street) {
		Assert.notNull(province, "province should not be null");
		Assert.notNull(city, "city should not be null");
		Assert.notNull(street, "street should not be null");
		this.province = province;
		this.city = city;
		this.street = street;
	}
}
