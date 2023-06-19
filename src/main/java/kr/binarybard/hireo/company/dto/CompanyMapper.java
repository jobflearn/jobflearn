package kr.binarybard.hireo.company.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import kr.binarybard.hireo.company.domain.Company;
import kr.binarybard.hireo.location.dto.LocationMapper;

@Mapper(componentModel = "spring", uses = LocationMapper.class)
public interface CompanyMapper {
	CompanyMapper INSTANCE = Mappers.getMapper(CompanyMapper.class);

	@Mapping(target = "location", source = "locationDto")
	Company toEntity(CompanyRegister companyDto);

	@Mapping(target = "locationDto", source = "location")
	CompanyResponse toDto(Company company);
}
