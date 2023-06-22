package kr.binarybard.hireo.web.company.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import kr.binarybard.hireo.web.company.domain.Company;
import kr.binarybard.hireo.web.location.dto.LocationMapper;

@Mapper(componentModel = "spring", uses = LocationMapper.class)
public interface CompanyMapper {
	CompanyMapper INSTANCE = Mappers.getMapper(CompanyMapper.class);

	@Mapping(target = "location", source = "locationDto")
	Company toEntity(CompanyRegister companyDto);

	@Mapping(target = "locationDto", source = "location")
	@Mapping(target = "countryName", ignore = true)
	CompanyResponse toDto(Company company);
}
