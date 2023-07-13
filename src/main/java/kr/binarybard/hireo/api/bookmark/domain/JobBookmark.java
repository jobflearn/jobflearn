package kr.binarybard.hireo.api.bookmark.domain;

import jakarta.persistence.*;
import kr.binarybard.hireo.common.BaseTimeEntity;
import kr.binarybard.hireo.web.job.domain.Job;
import kr.binarybard.hireo.web.member.domain.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "job_bookmarks", uniqueConstraints = {
	@UniqueConstraint(columnNames = {"member_id", "job_id"})})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class JobBookmark extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "job_bookmark_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "job_id", nullable = false)
	private Job job;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id", nullable = false)
	private Member member;

	@Builder
	public JobBookmark(Job job, Member member) {
		this.job = job;
		this.member = member;
	}
}

