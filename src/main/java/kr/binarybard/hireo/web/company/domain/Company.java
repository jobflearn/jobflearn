package kr.binarybard.hireo.web.company.domain;

import jakarta.persistence.*;
import org.springframework.util.Assert;

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

	public void changeLocation(Location location) {
		this.location = location;
	}

	public void changeDescription(String description) {
		this.description = description;
	}

	public void changeName(String name) {
		this.name = name;
	}

	@Builder
	public Company(String name, Boolean isVerified, String description, Location location) {
		Assert.notNull(name, "Company name cannot be null!");

		this.name = name;
		this.isVerified = isVerified;
		this.description = description;
		this.location = location;
	}
}
