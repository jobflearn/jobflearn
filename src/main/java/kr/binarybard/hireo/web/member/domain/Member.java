package kr.binarybard.hireo.web.member.domain;

import jakarta.persistence.*;
import kr.binarybard.hireo.api.bookmark.domain.CompanyBookmark;
import kr.binarybard.hireo.api.bookmark.domain.JobBookmark;
import kr.binarybard.hireo.common.BaseTimeEntity;
import kr.binarybard.hireo.web.company.domain.Company;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "members")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "member_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "company_id")
	private Company company;

	private String email;

	private String password;

	private String name;

	@Enumerated(EnumType.STRING)
	private Role role;

	@OneToMany(mappedBy = "member")
	private List<CompanyBookmark> companyBookmarks = new ArrayList<>();

	@OneToMany(mappedBy = "member")
	private List<JobBookmark> jobBookmarks = new ArrayList<>();

	@Builder
	public Member(String email, String password, String name, Role role) {
		this.email = email;
		this.password = password;
		this.role = role;
		this.name = name;
	}

	public void changeCompany(Company company) {
		this.company = company;
	}

	public void changeRole(Role role) {
		this.role = role;
	}

	public void changePassword(PasswordEncoder passwordEncoder, String newPassword) {
		this.password = newPassword;
		encodePassword(passwordEncoder);
	}

	public void changeName(String newName) {
		this.name = newName;
	}

	public void encodePassword(PasswordEncoder passwordEncoder) {
		this.password = passwordEncoder.encode(this.password);
	}
}
