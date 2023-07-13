package kr.binarybard.hireo.api.bookmark.domain;

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
@Table(name = "company_bookmarks", uniqueConstraints = {
	@UniqueConstraint(columnNames = {"member_id", "company_id"})})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CompanyBookmark extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "company_bookmark_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "company_id", nullable = false)
	private Company company;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id", nullable = false)
	private Member member;

	@Builder
	public CompanyBookmark(Company company, Member member) {
		this.company = company;
		this.member = member;
	}
}
