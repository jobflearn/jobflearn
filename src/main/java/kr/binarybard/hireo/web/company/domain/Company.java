package kr.binarybard.hireo.web.company.domain;

import org.springframework.util.Assert;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import kr.binarybard.hireo.web.location.domain.Location;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "companies")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Company {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "company_id")
	private Long id;

	@Column(nullable = false)
	private String name;

	private Boolean isVerified = false;

	@Lob
	private String description;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "location_id")
	private Location location;

	@Enumerated(value = EnumType.STRING)
	private Industry industry;

	private String logoHash;

	public void changeLocation(Location location) {
		this.location = location;
	}

	public void changeLogo(String logoHash) {
		this.logoHash = logoHash;
	}

	public void changeDescription(String description) {
		this.description = description;
	}

	public void changeName(String name) {
		this.name = name;
	}

	@Builder
	public Company(String name, Boolean isVerified, String description, String logoHash,
		Location location, Industry industry) {
		Assert.notNull(name, "Company name cannot be null!");

		this.name = name;
		this.isVerified = isVerified;
		this.description = description;
		this.logoHash = logoHash;
		this.location = location;
		this.industry = industry;
	}
}
