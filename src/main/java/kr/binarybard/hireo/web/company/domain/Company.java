package kr.binarybard.hireo.web.company.domain;

import org.springframework.util.Assert;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
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
@Table(name = "companies")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Company {
	@Id
	@GeneratedValue
	private Long id;
	@Column(nullable = false)
	private String name;
	private Boolean isVerified;
	@Lob
	private String description;

	/*Location은 단독적으로 존재하고, Member와도 연관관계를 향후 가질수 있끼 떄문에, PERSIST*/
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "location_id")
	private Location location;

	public void changeLocation(Location location) {
		this.location = location;
	}

	public void changeDescription(String description) {
		this.description = description;
	}

	public void chanageName(String name) {
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
