package kr.binarybard.hireo.api.bookmark.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import kr.binarybard.hireo.common.BaseTimeEntity;
import kr.binarybard.hireo.web.account.domain.Account;
import kr.binarybard.hireo.web.job.domain.Job;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "job_bookmarks", uniqueConstraints = {
	@UniqueConstraint(columnNames = {"account_id", "job_id"})})
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
	@JoinColumn(name = "account_id", nullable = false)
	private Account account;

	@Builder
	public JobBookmark(Job job, Account account) {
		this.job = job;
		this.account = account;
	}
}

