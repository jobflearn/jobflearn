package kr.binarybard.hireo.web.account.domain;

import java.util.List;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import kr.binarybard.hireo.api.bookmark.domain.CompanyBookmark;
import kr.binarybard.hireo.api.bookmark.domain.JobBookmark;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("JOBSEEKER")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class JobSeeker extends Account {
	private Integer salary;

	@Builder
	public JobSeeker(String email, String password, String name, String tagLine, String nationality, String description,
		List<CompanyBookmark> companyBookmarks, List<JobBookmark> jobBookmarks, Integer salary) {
		super(email, password, name, tagLine, nationality, description, companyBookmarks, jobBookmarks);
		this.salary = salary;
	}

	@PostConstruct
	private void initType() {
		initAccountType(AccountType.JOBSEEKER);
	}
}
