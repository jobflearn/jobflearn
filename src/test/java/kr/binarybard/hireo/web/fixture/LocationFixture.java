package kr.binarybard.hireo.web.fixture;

import kr.binarybard.hireo.web.location.domain.Address;
import kr.binarybard.hireo.web.location.domain.Location;

public class LocationFixture {
	public static final Location TEST_LOCATION = Location.builder()
		.latitude(37.4493638197085)
		.longitude(127.1275005197085)
		.address(Address.builder().city("안양").province("경기도").street("관악로 32").build())
		.countryCode("KR")
		.build();
}
