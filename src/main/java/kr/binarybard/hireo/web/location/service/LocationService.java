package kr.binarybard.hireo.web.location.service;

import org.springframework.stereotype.Service;

import kr.binarybard.hireo.exception.LocationNotFoundException;
import kr.binarybard.hireo.web.location.domain.Location;
import kr.binarybard.hireo.web.location.dto.LocationDto;
import kr.binarybard.hireo.web.location.dto.LocationMapper;
import kr.binarybard.hireo.web.location.repository.LocationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class LocationService {
	private final LocationRepository locationRepository;
	private final LocationMapper locationMapper;

	public LocationDto save(LocationDto locationDto) {
		Location location = locationMapper.toEntity(locationDto);
		return locationMapper.toDto(locationRepository.save(location));
	}

	public Location findById(Long id) {
		return locationRepository.findById(id).orElseThrow(LocationNotFoundException::new);
	}
}
