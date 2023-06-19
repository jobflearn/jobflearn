package kr.binarybard.hireo.web.location.dto;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import kr.binarybard.hireo.web.location.domain.Location;

@Mapper(componentModel = "spring")
public interface LocationMapper {
	LocationMapper INSTANCE = Mappers.getMapper(LocationMapper.class);

	Location toEntity(LocationDto locationDto);

	LocationDto toDto(Location location);
}
