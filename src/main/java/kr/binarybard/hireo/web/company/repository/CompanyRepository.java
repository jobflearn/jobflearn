package kr.binarybard.hireo.web.company.repository;

import kr.binarybard.hireo.common.exceptions.EntityNotFoundException;
import kr.binarybard.hireo.common.exceptions.ErrorCode;
import org.springframework.data.jpa.repository.JpaRepository;

import kr.binarybard.hireo.web.company.domain.Company;

public interface CompanyRepository extends JpaRepository<Company, Long> {

	default Company findByIdOrThrow(Long id) {
		return findById(id).orElseThrow(() -> new EntityNotFoundException(ErrorCode.COMPANY_NOT_FOUND, id.toString()));
	}
}
