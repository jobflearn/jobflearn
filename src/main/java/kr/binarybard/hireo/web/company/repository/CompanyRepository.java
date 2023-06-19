package kr.binarybard.hireo.web.company.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.binarybard.hireo.web.company.domain.Company;

public interface CompanyRepository extends JpaRepository<Company, Long> {
}
