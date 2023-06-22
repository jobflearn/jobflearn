package kr.binarybard.hireo.common.fixture;

import kr.binarybard.hireo.web.company.domain.Company;
import kr.binarybard.hireo.web.company.domain.Industry;
import kr.binarybard.hireo.web.company.dto.CompanyRegister;
import kr.binarybard.hireo.web.company.dto.CompanyResponse;
import kr.binarybard.hireo.web.location.domain.Location;
import kr.binarybard.hireo.web.location.dto.LocationDto;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;
import java.util.List;

public class CompanyFixture {

	private static final String TEST_COMPANY_A_NAME = "companyA";
	private static final String TEST_COMPANY_B_NAME = "companyB";

	private static final String TEST_COMPANY_A_DESC = "CompanyA description...";
	private static final String TEST_COMPANY_B_DESC = "CompanyB description...";

	private static final String TEST_COMPANY_A_COUNTRY = "CountryA";
	private static final String TEST_COMPANY_B_COUNTRY = "CountryB";

	private static final LocationDto TEST_LOCATION_DTO_1 = LocationFixture.TEST_LOCATION_DTO_1;
	private static final LocationDto TEST_LOCATION_DTO_2 = LocationFixture.TEST_LOCATION_DTO_1;

	public static Company createCompany(Long id, String name, Boolean isVerified, String description, Location location, Industry industry) {
		Company company = Company.builder()
			.name(name)
			.isVerified(isVerified)
			.description(description)
			.location(location)
			.industry(industry)
			.build();
		ReflectionTestUtils.setField(company, "id", id);
		return company;
	}

	public static CompanyResponse createCompanyResponse(Long id, String name, String description, String countryName, LocationDto locationDto, Industry industry) {
		return CompanyResponse.builder()
			.id(id)
			.name(name)
			.description(description)
			.countryName(countryName)
			.locationDto(locationDto)
			.industry(industry)
			.build();
	}

	public static CompanyRegister createCompanyRegister(String name, String description, LocationDto locationDto, Boolean isVerified, Industry industry) {
		return CompanyRegister.builder()
			.name(name)
			.description(description)
			.locationDto(locationDto)
			.isVerified(isVerified)
			.industry(industry)
			.build();
	}

	public static Company createTestCompanyA() {
		return createCompany(1L, TEST_COMPANY_A_NAME, true, TEST_COMPANY_A_DESC, LocationFixture.TEST_LOCATION_1, Industry.IT);
	}

	public static Company createTestCompanyB() {
		return createCompany(2L, TEST_COMPANY_B_NAME, false, TEST_COMPANY_B_DESC, LocationFixture.TEST_LOCATION_2, Industry.FINANCE);
	}

	public static List<Company> createTestCompanies() {
		return Arrays.asList(createTestCompanyA(), createTestCompanyB());
	}

	public static CompanyResponse createTestCompanyAResponse() {
		return createCompanyResponse(1L, TEST_COMPANY_A_NAME, TEST_COMPANY_A_DESC, TEST_COMPANY_A_COUNTRY, TEST_LOCATION_DTO_1, Industry.IT);
	}

	public static CompanyResponse createTestCompanyBResponse() {
		return createCompanyResponse(2L, TEST_COMPANY_B_NAME, TEST_COMPANY_B_DESC, TEST_COMPANY_B_COUNTRY, TEST_LOCATION_DTO_2, Industry.FINANCE);
	}

	public static CompanyRegister createTestCompanyARegister() {
		return createCompanyRegister(TEST_COMPANY_A_NAME, TEST_COMPANY_A_DESC, TEST_LOCATION_DTO_1, true, Industry.IT);
	}

	public static CompanyRegister createTestCompanyBRegister() {
		return createCompanyRegister(TEST_COMPANY_B_NAME, TEST_COMPANY_B_DESC, TEST_LOCATION_DTO_2, false, Industry.FINANCE);
	}
}


