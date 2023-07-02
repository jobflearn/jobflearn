package kr.binarybard.hireo.web.company.dto;

import kr.binarybard.hireo.web.location.domain.Address;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CompanyListResponse {

	private Long id;
	private String name;
	private String logoHash;
	private Address address;

	@Builder
	public CompanyListResponse(Long id, String name, String logoHash, Address address) {
		this.id = id;
		this.name = name;
		this.logoHash = logoHash;
		this.address = address;
	}
}
