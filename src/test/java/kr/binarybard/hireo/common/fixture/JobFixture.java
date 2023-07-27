package kr.binarybard.hireo.common.fixture;

import static kr.binarybard.hireo.common.fixture.LocationFixture.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.util.ReflectionTestUtils;

import kr.binarybard.hireo.web.company.dto.CompanyListResponse;
import kr.binarybard.hireo.web.job.domain.Category;
import kr.binarybard.hireo.web.job.domain.Job;
import kr.binarybard.hireo.web.job.domain.JobType;
import kr.binarybard.hireo.web.job.dto.JobListResponse;
import kr.binarybard.hireo.web.job.dto.JobResponse;
import kr.binarybard.hireo.web.job.dto.JobSearchCondition;
import kr.binarybard.hireo.web.location.domain.Address;

public class JobFixture {

	private static final String SOFTWARE_ENGINEER_NAME = "Software Engineer";
	private static final String DATA_SCIENTIST_NAME = "Data Scientist";
	private static final String MARKETING_MANAGER_NAME = "Marketing Manager";
	private static final String GRAPHIC_DESIGNER_NAME = "Graphic Designer";
	private static final String EDUCATION_TRAINER_NAME = "Education & Training";

	private static final String SOFTWARE_ENGINEER_DESCRIPTION = "Develop and maintain software applications";
	private static final String DATA_SCIENTIST_DESCRIPTION = "Analyze and interpret complex datasets";
	private static final String MARKETING_MANAGER_DESCRIPTION = "Plan and execute marketing strategies";
	private static final String GRAPHIC_DESIGNER_DESCRIPTION = "Design visual content for various media";
	private static final String EDUCATION_TRAINER_DESCRIPTION = "Deliver educational programs and workshops";

	public static JobResponse createSoftwareEngineerJobResponse() {
		return JobResponse.builder()
			.id(1L)
			.name(SOFTWARE_ENGINEER_NAME)
			.description(SOFTWARE_ENGINEER_DESCRIPTION)
			.jobType(JobType.FULLTIME)
			.startSalary(5000)
			.endSalary(10000)
			.postedAt(LocalDateTime.now())
			.category(Category.WEB_SOFT)
			.company(CompanyFixture.createTestCompanyAResponse())
			.build();
	}

	public static JobListResponse createJobListResposne() {
		return JobListResponse.builder()
			.jobType(JobType.FULLTIME)
			.startSalary(3000)
			.endSalary(4500)
			.postedAt(LocalDateTime.now())
			.description("3년차 소프트웨어 엔지니어 구합니다.")
			.name("소프트웨어 엔지니어")
			.category(Category.WEB_SOFT)
			.id(1L)
			.company(new CompanyListResponse(1L, "Google", "image.png",
				new Address("경기도", "안양시", "원골로", "노원구", "미상")
			))
			.build();
	}

	public static JobSearchCondition createJobSearchCondition() {
		JobSearchCondition condition = JobSearchCondition.builder()
			.keyword("소프트")
			.locationDto(TEST_LOCATION_CONDITION_1)
			.jobType(JobType.FULLTIME)
			.category(Category.WEB_SOFT)
			.maxSalary(9999)
			.minSalary(1500)
			.build();
		return condition;
	}

	public static Page<JobListResponse> createJobListByPage(int page, int limit) {
		List<JobListResponse> jobList = new ArrayList<>();
		for (int i = 0; i < 100; i++) {
			jobList.add(createJobListResponse((long)i, (long)i));
		}
		return new PageImpl<>(jobList, PageRequest.of(page, limit), 100);
	}

	public static JobListResponse createJobListResponse(Long jobId, Long companyId) {
		return JobListResponse.builder()
			.id(jobId)
			.name(SOFTWARE_ENGINEER_NAME)
			.description(SOFTWARE_ENGINEER_DESCRIPTION)
			.jobType(JobType.INTERNSHIP)
			.startSalary(5000)
			.endSalary(10000)
			.postedAt(LocalDateTime.now())
			.company(CompanyFixture.createCompanyListResponseWithId(companyId))
			.build();
	}

	public static JobResponse createDataScientistJobResponse() {
		return JobResponse.builder()
			.id(2L)
			.name(DATA_SCIENTIST_NAME)
			.description(DATA_SCIENTIST_DESCRIPTION)
			.jobType(JobType.FULLTIME)
			.startSalary(6000)
			.endSalary(12000)
			.postedAt(LocalDateTime.now())
			.category(Category.DATA_SCI)
			.company(CompanyFixture.createTestCompanyBResponse())
			.build();
	}

	public static JobResponse createMarketingManagerJobResponse() {
		return JobResponse.builder()
			.id(3L)
			.name(MARKETING_MANAGER_NAME)
			.description(MARKETING_MANAGER_DESCRIPTION)
			.jobType(JobType.PARTTIME)
			.startSalary(4000)
			.endSalary(8000)
			.postedAt(LocalDateTime.now())
			.category(Category.SALE_MAR)
			.company(CompanyFixture.createTestCompanyBResponse())
			.build();
	}

	public static JobResponse createGraphicDesignerJobResponse() {
		return JobResponse.builder()
			.id(4L)
			.name(GRAPHIC_DESIGNER_NAME)
			.description(GRAPHIC_DESIGNER_DESCRIPTION)
			.jobType(JobType.PARTTIME)
			.startSalary(3000)
			.endSalary(6000)
			.postedAt(LocalDateTime.now())
			.category(Category.GRAP_DES)
			.company(CompanyFixture.createTestCompanyAResponse())
			.build();
	}

	public static JobResponse createEducationTrainerJobResponse() {
		return JobResponse.builder()
			.id(5L)
			.name(EDUCATION_TRAINER_NAME)
			.description(EDUCATION_TRAINER_DESCRIPTION)
			.jobType(JobType.INTERNSHIP)
			.startSalary(2000)
			.endSalary(4000)
			.postedAt(LocalDateTime.now())
			.category(Category.EDU_TRAI)
			.company(CompanyFixture.createTestCompanyBResponse())
			.build();
	}

	public static Job createSoftwareEngineerJob() {
		Job job = Job.builder()
			.name(SOFTWARE_ENGINEER_NAME)
			.jobType(JobType.FULLTIME)
			.startSalary(5000)
			.endSalary(10000)
			.description(SOFTWARE_ENGINEER_DESCRIPTION)
			.category(Category.WEB_SOFT)
			.company(CompanyFixture.createTestCompanyA())
			.build();
		ReflectionTestUtils.setField(job, "id", 1L);
		return job;
	}

	public static Job createDataScientistJob() {
		Job job = Job.builder()
			.name(DATA_SCIENTIST_NAME)
			.jobType(JobType.FULLTIME)
			.startSalary(6000)
			.endSalary(12000)
			.description(DATA_SCIENTIST_DESCRIPTION)
			.category(Category.DATA_SCI)
			.company(CompanyFixture.createTestCompanyB())
			.build();
		ReflectionTestUtils.setField(job, "id", 2L);
		return job;
	}

	public static Job createMarketingManagerJob() {
		Job job = Job.builder()
			.name(MARKETING_MANAGER_NAME)
			.jobType(JobType.PARTTIME)
			.startSalary(4000)
			.endSalary(8000)
			.description(MARKETING_MANAGER_DESCRIPTION)
			.category(Category.SALE_MAR)
			.company(CompanyFixture.createTestCompanyB())
			.build();
		ReflectionTestUtils.setField(job, "id", 3L);
		return job;
	}

	public static Job createGraphicDesignerJob() {
		Job job = Job.builder()
			.name(GRAPHIC_DESIGNER_NAME)
			.jobType(JobType.PARTTIME)
			.startSalary(3000)
			.endSalary(6000)
			.description(GRAPHIC_DESIGNER_DESCRIPTION)
			.category(Category.GRAP_DES)
			.company(CompanyFixture.createTestCompanyA())
			.build();
		ReflectionTestUtils.setField(job, "id", 4L);
		return job;
	}

	public static Job createEducationTrainerJob() {
		Job job = Job.builder()
			.name(EDUCATION_TRAINER_NAME)
			.jobType(JobType.INTERNSHIP)
			.startSalary(2000)
			.endSalary(4000)
			.description(EDUCATION_TRAINER_DESCRIPTION)
			.category(Category.EDU_TRAI)
			.company(CompanyFixture.createTestCompanyB())
			.build();
		ReflectionTestUtils.setField(job, "id", 5L);
		return job;
	}
}

