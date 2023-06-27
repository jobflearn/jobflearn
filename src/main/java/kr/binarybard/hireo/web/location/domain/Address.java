package kr.binarybard.hireo.web.location.domain;

import org.springframework.util.Assert;

import jakarta.persistence.Embeddable;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class Address {
	private String province;
	private String city;
	private String street;
	private String district;
	private String premise;

	@Builder
	public Address(String province, String city, String street, String district, String premise) {
		Assert.notNull(province, "province should not be null");
		Assert.notNull(city, "city should not be null");
		Assert.notNull(street, "street should not be null");
		this.province = province;
		this.city = city;
		this.street = street;
		this.district = district;
		this.premise = premise;
	}
}
