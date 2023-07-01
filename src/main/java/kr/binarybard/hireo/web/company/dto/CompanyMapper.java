package kr.binarybard.hireo.web.company.dto;

import kr.binarybard.hireo.web.company.domain.Company;
import kr.binarybard.hireo.web.location.dto.LocationMapper;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Locale;

@Mapper(componentModel = "spring", uses = LocationMapper.class)
public abstract class CompanyMapper {
	@Autowired
	private MessageSource messageSource;

	@Mapping(target = "location", source = "locationDto")
	public abstract Company toEntity(CompanyRegister companyDto);

	@Mapping(target = "locationDto", source = "location")
	@Mapping(target = "countryName", ignore = true)
	@Mapping(target = "logoHash", ignore = true)
	public abstract CompanyResponse toDto(Company company);

	@AfterMapping
	protected void addCountryName(Company source, @MappingTarget CompanyResponse.CompanyResponseBuilder target) {
		Locale locale = LocaleContextHolder.getLocale();
		target.countryName(messageSource.getMessage("country." + source.getLocation().getCountryCode(), null, locale));
	}
}
