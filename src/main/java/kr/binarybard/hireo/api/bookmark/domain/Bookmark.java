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
@Table(name = "bookmarks", uniqueConstraints = {
		@UniqueConstraint(columnNames = {"member_id", "company_id"})})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Bookmark extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "bookmark_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "company_id")
	private Company company;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id", nullable = false)
	private Member member;

	@Builder
	public Bookmark(Member member, Company company) {
		this.member = member;
		this.company = company;
	}
}
