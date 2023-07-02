package kr.binarybard.hireo.web.company.domain;

import jakarta.persistence.*;
import kr.binarybard.hireo.web.review.domain.Review;
import org.springframework.util.Assert;

import kr.binarybard.hireo.web.location.domain.Location;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

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

	@OneToMany(mappedBy = "company")
	private final List<Review> reviews = new ArrayList<>();

	private String logoHash;

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

	public void changeLogo(String logoHash) {
		this.logoHash = logoHash;
	}

	public double getAverageRating() {
		return reviews.stream()
			.mapToDouble(Review::getRating)
			.average()
			.orElse(0);
	}
}
