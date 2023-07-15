package kr.binarybard.hireo.web.review.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import kr.binarybard.hireo.common.BaseTimeEntity;
import kr.binarybard.hireo.web.account.domain.Account;
import kr.binarybard.hireo.web.company.domain.Company;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "reviews")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "review_id")
	private Long id;

	@Column(nullable = false)
	private String title;

	@Column(nullable = false)
	private String content;

	@Column(nullable = false)
	private int rating;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "account_id")
	private Account author;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "company_id")
	private Company company;

	@Builder
	public Review(String title, String content, int rating, Account author, Company company) {
		this.title = title;
		this.content = content;
		this.rating = rating;
		this.author = author;
		this.company = company;
	}

	public void assignAuthor(Account account) {
		this.author = account;
	}

	public void associateWithCompany(Company company) {
		this.company = company;
	}
}
