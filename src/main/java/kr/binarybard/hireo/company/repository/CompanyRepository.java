package kr.binarybard.hireo.company.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.binarybard.hireo.company.domain.Company;

public interface CompanyRepository extends JpaRepository<Company, Long> {
}
