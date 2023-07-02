package kr.binarybard.hireo.common.fixture;

import java.util.Arrays;
import java.util.List;

import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;

import kr.binarybard.hireo.web.company.domain.Company;
import kr.binarybard.hireo.web.company.domain.Industry;
import kr.binarybard.hireo.web.company.dto.CompanyListResponse;
import kr.binarybard.hireo.web.company.dto.CompanyRegister;
import kr.binarybard.hireo.web.company.dto.CompanyResponse;
import kr.binarybard.hireo.web.company.dto.CompanyReviewResponse;
import kr.binarybard.hireo.web.location.domain.Address;
import kr.binarybard.hireo.web.location.domain.Location;
import kr.binarybard.hireo.web.location.dto.LocationDto;
import kr.binarybard.hireo.web.review.domain.Review;
import kr.binarybard.hireo.web.review.dto.ReviewResponse;

public class CompanyFixture {
	private static final String TEST_COMPANY_A_NAME = "companyA";
	private static final String TEST_COMPANY_B_NAME = "companyB";

	private static final String TEST_COMPANY_A_DESC = "CompanyA description...";
	private static final String TEST_COMPANY_B_DESC = "CompanyB description...";

	private static final String TEST_COMPANY_A_COUNTRY = "CountryA";
	private static final String TEST_COMPANY_B_COUNTRY = "CountryB";

	private static final String TEST_COMPANY_A_LOGO_HASH = "0f14571edc52a4102a7f23ca33d3175a.png";
	private static final String TEST_COMPANY_B_LOGO_HASH = "78805a221a988e79ef3f42d7c5bfd418.png";

	private static final LocationDto TEST_LOCATION_DTO_1 = LocationFixture.TEST_LOCATION_DTO_1;
	private static final LocationDto TEST_LOCATION_DTO_2 = LocationFixture.TEST_LOCATION_DTO_1;

	private static final Address TEST_ADDRESS = Address.builder()
		.city("seoul")
		.district("jongro")
		.premise("premise")
		.street("3ga")
		.province("seoul")
		.build();

	private static final MockMultipartFile TEST_LOGO_FILE = new MockMultipartFile("test.png", "test.png",
		"application/x-www-form-urlencoded", "test.png".getBytes());

	public static Company createTestCompanyA() {
		return createCompany(1L, TEST_COMPANY_A_NAME, true, TEST_COMPANY_A_DESC, TEST_COMPANY_A_LOGO_HASH,
			LocationFixture.TEST_LOCATION_1,
			Industry.IT);
	}

	public static Company createTestCompanyAWithReviews() {
		List<Review> reviews = List.of(
			ReviewFixture.createTestReview1(createTestCompanyA()),
			ReviewFixture.createTestReview2(createTestCompanyA()),
			ReviewFixture.createTestReview3(createTestCompanyA())
		);
		return createCompany(1L, TEST_COMPANY_A_NAME, true, TEST_COMPANY_A_DESC, TEST_COMPANY_A_LOGO_HASH,
			LocationFixture.TEST_LOCATION_1, Industry.IT, reviews);
	}

	public static Company createTestCompanyB() {
		return createCompany(2L, TEST_COMPANY_B_NAME, false, TEST_COMPANY_B_DESC, TEST_COMPANY_B_LOGO_HASH,
			LocationFixture.TEST_LOCATION_2,
			Industry.FINANCE);
	}

	public static List<Company> createTestCompanies() {
		return Arrays.asList(createTestCompanyA(), createTestCompanyB());
	}

	public static CompanyResponse createTestCompanyAResponse() {
		CompanyReviewResponse reviewResponse = createTestCompanyReviewResponse();
		return createCompanyResponse(1L, TEST_COMPANY_A_NAME, TEST_COMPANY_A_DESC, TEST_COMPANY_A_LOGO_HASH,
			TEST_COMPANY_A_COUNTRY,
			TEST_LOCATION_DTO_1, Industry.IT, reviewResponse);
	}

	public static CompanyListResponse createCompanyListResponseWithId(Long id) {
		return createCompanyListReponse(id, TEST_COMPANY_A_NAME, TEST_ADDRESS, TEST_COMPANY_A_LOGO_HASH);
	}

	private static CompanyReviewResponse createTestCompanyReviewResponse() {
		List<ReviewResponse> reviews = List.of(
			ReviewFixture.createTestReviewResponse1(),
			ReviewFixture.createTestReviewResponse2(),
			ReviewFixture.createTestReviewResponse3()
		);
		return CompanyReviewResponse.builder()
			.reviews(reviews)
			.build();
	}

	public static CompanyResponse createTestCompanyBResponse() {
		CompanyReviewResponse reviewResponse = createTestCompanyReviewResponse();
		return createCompanyResponse(2L, TEST_COMPANY_B_NAME, TEST_COMPANY_B_DESC, TEST_COMPANY_B_LOGO_HASH,
			TEST_COMPANY_B_COUNTRY,
			TEST_LOCATION_DTO_2, Industry.FINANCE, reviewResponse);
	}

	public static CompanyRegister createTestCompanyARegister() {
		return createCompanyRegister(TEST_COMPANY_A_NAME, TEST_COMPANY_A_DESC, TEST_LOCATION_DTO_1, true, Industry.IT);
	}

	public static CompanyRegister createTestCompanyBRegister() {
		return createCompanyRegister(TEST_COMPANY_B_NAME, TEST_COMPANY_B_DESC, TEST_LOCATION_DTO_2, false,
			Industry.FINANCE);
	}

	private static Company createCompany(Long id, String name, Boolean isVerified, String description, String logoHash,
		Location location,
		Industry industry) {
		Company company = Company.builder()
			.name(name)
			.isVerified(isVerified)
			.description(description)
			.location(location)
			.industry(industry)
			.logoHash(logoHash)
			.build();
		ReflectionTestUtils.setField(company, "id", id);
		return company;
	}

	private static Company createCompany(Long id, String name, Boolean isVerified, String description, String logoHash,
		Location location, Industry industry, List<Review> reviews) {
		Company company = createCompany(id, name, isVerified, description, logoHash, location, industry);
		ReflectionTestUtils.setField(company, "reviews", reviews);
		return company;
	}

	private static CompanyResponse createCompanyResponse(Long id, String name, String description, String logoHash,
		String countryName, LocationDto locationDto, Industry industry, CompanyReviewResponse reviewResponse) {
		return CompanyResponse.builder()
			.id(id)
			.name(name)
			.description(description)
			.countryName(countryName)
			.locationDto(locationDto)
			.industry(industry)
			.logoHash(logoHash)
			.reviews(reviewResponse)
			.build();
	}

	private static CompanyRegister createCompanyRegister(String name, String description, LocationDto locationDto,
		Boolean isVerified, Industry industry) {
		return CompanyRegister.builder()
			.name(name)
			.description(description)
			.locationDto(locationDto)
			.isVerified(isVerified)
			.industry(industry)
			.companyLogo(TEST_LOGO_FILE)
			.build();
	}

	private static CompanyListResponse createCompanyListReponse(Long id, String name, Address address,
		String logoHash) {
		return CompanyListResponse.builder()
			.id(id)
			.name(name)
			.address(address)
			.logoHash(logoHash)
			.build();
	}
}
