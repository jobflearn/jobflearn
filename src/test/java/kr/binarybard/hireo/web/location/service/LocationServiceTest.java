package kr.binarybard.hireo.web.location.service;

import static kr.binarybard.hireo.web.fixture.LocationDtoFixture.*;
import static kr.binarybard.hireo.web.fixture.LocationFixture.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import kr.binarybard.hireo.common.exceptions.EntityNotFoundException;
import kr.binarybard.hireo.web.location.domain.Location;
import kr.binarybard.hireo.web.location.dto.LocationDto;
import kr.binarybard.hireo.web.location.dto.LocationMapper;
import kr.binarybard.hireo.web.location.repository.LocationRepository;

@ExtendWith(MockitoExtension.class)
class LocationServiceTest {

	@InjectMocks
	private LocationService locationService;

	@Mock
	private LocationRepository locationRepository;

	@Mock
	private LocationMapper locationMapper;

	private LocationDto locationDto;
	private Location location;

	@BeforeEach
	void setUp() {
		location = TEST_LOCATION;
		locationDto = TEST_LOCATION_DTO;
	}

	@Test
	@DisplayName("Location 저장 테스트")
	void saveTest() {
		when(locationMapper.toEntity(locationDto)).thenReturn(location);
		when(locationRepository.save(any(Location.class))).thenReturn(location);
		when(locationMapper.toDto(location)).thenReturn(locationDto);

		LocationDto savedLocationDto = locationService.save(locationDto);

		assertThat(savedLocationDto).usingRecursiveComparison().isEqualTo(locationDto);
	}

	@Test
	@DisplayName("ID를 이용한 Location 조회 테스트")
	void findByIdTest() {
		when(locationRepository.findById(any(Long.class))).thenReturn(Optional.of(location));

		Location foundLocation = locationService.findById(1L);

		assertThat(foundLocation).usingRecursiveComparison().isEqualTo(location);
	}

	@Test
	@DisplayName("존재하지 않는 ID를 이용한 Location 조회 테스트")
	void findByIdNotFoundTest() {
		when(locationRepository.findById(any(Long.class))).thenReturn(Optional.empty());

		assertThatThrownBy(() -> {
			locationService.findById(1L);
		}).isInstanceOf(EntityNotFoundException.class);
	}
}
