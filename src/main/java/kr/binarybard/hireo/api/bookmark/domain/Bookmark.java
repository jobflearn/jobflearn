package kr.binarybard.hireo.api.bookmark.domain;

import jakarta.persistence.*;
import kr.binarybard.hireo.common.BaseTimeEntity;
import kr.binarybard.hireo.web.company.domain.Company;
import kr.binarybard.hireo.web.job.domain.Job;
import kr.binarybard.hireo.web.member.domain.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "bookmarks", uniqueConstraints = {
		@UniqueConstraint(columnNames = {"member_id", "company_id"}),
		@UniqueConstraint(columnNames = {"member_id", "job_id"})})
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
	@JoinColumn(name = "job_id")
	private Job job;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id", nullable = false)
	private Member member;

	@Builder
	public Bookmark(Member member, Job job, Company company) {
		this.member = member;
		this.job = job;
		this.company = company;
	}
}
