package kr.binarybard.hireo.location.dto;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import kr.binarybard.hireo.location.domain.Location;

@Mapper(componentModel = "spring")
public interface LocationMapper {
	LocationMapper INSTANCE = Mappers.getMapper(LocationMapper.class);

	Location toEntity(LocationDto locationDto);

	LocationDto toDto(Location location);
}
