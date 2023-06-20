package kr.binarybard.hireo.web.fixture;

import kr.binarybard.hireo.web.company.dto.CompanyRegister;

public class CompanyRegisterFixture {
	public static final CompanyRegister TEST_COMPANY_REGISTER = CompanyRegister.builder()
		.name("companyA")
		.description("""
				Leverage agile frameworks to provide a robust synopsis for high level overviews. Iterative approaches to corporate strategy foster collaborative thinking to further the overall value proposition. Organically grow the holistic world view of disruptive innovation via workplace diversity and empowerment.

				Capitalize on low hanging fruit to identify a ballpark value added activity to beta test. Override the digital divide with additional click throughs from DevOps. Nanotechnology immersion along the information highway will close the loop on focusing solely on the bottom line.""")
		.locationDto(LocationDtoFixture.TEST_LOCATION_DTO)
		.build();
}
