package kr.binarybard.hireo.web.review.domain;

import jakarta.persistence.*;
import kr.binarybard.hireo.common.BaseTimeEntity;
import kr.binarybard.hireo.web.company.domain.Company;
import kr.binarybard.hireo.web.member.domain.Member;
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
	@JoinColumn(name = "member_id")
	private Member author;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "company_id")
	private Company company;

	@Builder
	public Review(String title, String content, int rating, Member author, Company company) {
		this.title = title;
		this.content = content;
		this.rating = rating;
		this.author = author;
		this.company = company;
	}

	public void assignAuthor(Member member) {
		this.author = member;
	}

	public void associateWithCompany(Company company) {
		this.company = company;
	}
}
