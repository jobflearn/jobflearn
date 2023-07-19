package kr.binarybard.hireo.web.account.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ObjectFactory;

import kr.binarybard.hireo.api.bookmark.dto.CompanyBookmarkMapper;
import kr.binarybard.hireo.api.bookmark.dto.JobBookmarkMapper;
import kr.binarybard.hireo.web.account.domain.Account;
import kr.binarybard.hireo.web.account.domain.AccountType;
import kr.binarybard.hireo.web.account.domain.Employee;
import kr.binarybard.hireo.web.account.domain.JobSeeker;
import kr.binarybard.hireo.web.account.domain.Personnel;
import kr.binarybard.hireo.web.auth.dto.SignUpRequest;

@Mapper(componentModel = "spring", uses = {JobBookmarkMapper.class, CompanyBookmarkMapper.class})
public interface AccountMapper {
	Account toEntity(SignUpRequest accountDto);

	@Mapping(source = "companyBookmarks", target = "companyBookmarks")
	@Mapping(source = "jobBookmarks", target = "jobBookmarks")
	AccountResponse toDto(Account account);

	@ObjectFactory
	default Account resolve(SignUpRequest dto) {
		if (dto.getType().equals(AccountType.PERSONNEL)) {
			return Personnel.builder()
				.email(dto.getEmail())
				.password(dto.getPassword())
				.build();
		} else if (dto.getType() == AccountType.EMPLOYEE) {
			return Employee.builder()
				.email(dto.getEmail())
				.password(dto.getPassword())
				.build();
		} else if (dto.getType() == AccountType.JOBSEEKER) {
			return JobSeeker.builder()
				.email(dto.getEmail())
				.password(dto.getPassword())
				.build();
		} else {
			throw new IllegalArgumentException("Invalid account type");
		}
	}
}
