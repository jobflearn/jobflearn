package kr.binarybard.hireo.web.account.domain;

import java.util.List;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import kr.binarybard.hireo.api.bookmark.domain.CompanyBookmark;
import kr.binarybard.hireo.api.bookmark.domain.JobBookmark;
import kr.binarybard.hireo.web.company.domain.Company;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("PERSONNEL")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Personnel extends Account {

	@Builder
	public Personnel(String email, String password, String name, String tagLine, String nationality, String description,
		List<CompanyBookmark> companyBookmarks,
		List<JobBookmark> jobBookmarks, Company company) {
		super(email, password, name, tagLine, nationality, description, companyBookmarks, jobBookmarks);
		this.company = company;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "company_id")
	private Company company;

	public void changeCompany(Company company) {
		this.company = company;
	}

	@PostConstruct
	private void initType() {
		initAccountType(AccountType.PERSONNEL);
	}
}
