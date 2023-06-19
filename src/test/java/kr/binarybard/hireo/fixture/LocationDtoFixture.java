package kr.binarybard.hireo.fixture;

import kr.binarybard.hireo.location.domain.Address;
import kr.binarybard.hireo.location.dto.LocationDto;

public class LocationDtoFixture {
	public static final LocationDto TEST_LOCATION_DTO = LocationDto.builder()
		.latitude(37.4493638197085)
		.longitude(127.1275005197085)
		.address(Address.builder().city("안양").province("경기도").street("관악로 32").build())
		.countryCode("KR")
		.build();
}
