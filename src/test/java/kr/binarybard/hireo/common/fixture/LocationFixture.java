package kr.binarybard.hireo.common.fixture;

import kr.binarybard.hireo.web.location.domain.Address;
import kr.binarybard.hireo.web.location.domain.Location;
import kr.binarybard.hireo.web.location.dto.LocationDto;

public class LocationFixture {
	public static final Location TEST_LOCATION_1 = Location.builder()
		.latitude(37.4493638197085)
		.longitude(127.1275005197085)
		.address(Address.builder().city("안양").province("경기도").street("관악로 32").build())
		.countryCode("KR")
		.build();

	public static final Location TEST_LOCATION_2 = Location.builder()
		.latitude(37.5665)
		.longitude(126.9780)
		.address(Address.builder().city("서울").province("서울특별시").street("청계천로").build())
		.countryCode("KR")
		.build();

	public static final Location TEST_LOCATION_3 = Location.builder()
		.latitude(40.712776)
		.longitude(-74.005974)
		.address(Address.builder().city("New York").province("New York").street("Broadway").build())
		.countryCode("US")
		.build();

	public static final Location TEST_LOCATION_4 = Location.builder()
		.latitude(34.052235)
		.longitude(-118.243683)
		.address(Address.builder().city("Los Angeles").province("California").street("Hollywood Blvd").build())
		.countryCode("US")
		.build();

	public static final LocationDto TEST_LOCATION_DTO_1 = LocationDto.builder()
		.latitude(37.4493638197085)
		.longitude(127.1275005197085)
		.address(Address.builder().city("안양").province("경기도").street("관악로 32").build())
		.countryCode("KR")
		.build();

	public static final LocationDto TEST_LOCATION_DTO_2 = LocationDto.builder()
		.latitude(37.5665)
		.longitude(126.9780)
		.address(Address.builder().city("서울").province("서울특별시").street("청계천로").build())
		.countryCode("KR")
		.build();

	public static final LocationDto TEST_LOCATION_DTO_3 = LocationDto.builder()
		.latitude(40.712776)
		.longitude(-74.005974)
		.address(Address.builder().city("New York").province("New York").street("Broadway").build())
		.countryCode("US")
		.build();

	public static final LocationDto TEST_LOCATION_DTO_4 = LocationDto.builder()
		.latitude(34.052235)
		.longitude(-118.243683)
		.address(Address.builder().city("Los Angeles").province("California").street("Hollywood Blvd").build())
		.countryCode("US")
		.build();
}
