package kr.binarybard.hireo.web.account.domain;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import kr.binarybard.hireo.api.bookmark.domain.CompanyBookmark;
import kr.binarybard.hireo.api.bookmark.domain.JobBookmark;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "accounts")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "account_type", discriminatorType = DiscriminatorType.STRING)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public abstract class Account {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "account_id")
	private Long id;

	@Column(unique = true, nullable = false)
	private String email;

	@Column(nullable = false)
	private String password;

	private String name;

	private String tagLine;

	private String nationality;

	private String description;

	@Enumerated(EnumType.STRING)
	@Column(name = "account_type", insertable = false, updatable = false)
	private AccountType type;

	protected void initAccountType(AccountType type) {
		this.type = type;
	}

	protected Account(String email, String password, String name, String tagLine, String nationality,
		String description,
		List<CompanyBookmark> companyBookmarks, List<JobBookmark> jobBookmarks) {
		this.email = email;
		this.password = password;
		this.name = name;
		this.tagLine = tagLine;
		this.nationality = nationality;
		this.description = description;
		this.companyBookmarks = companyBookmarks;
		this.jobBookmarks = jobBookmarks;
	}

	@OneToMany(mappedBy = "account")
	private List<CompanyBookmark> companyBookmarks = new ArrayList<>();

	@OneToMany(mappedBy = "account")
	private List<JobBookmark> jobBookmarks = new ArrayList<>();

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

