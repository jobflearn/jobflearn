package kr.binarybard.hireo.location.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.binarybard.hireo.location.domain.Location;

public interface LocationRepository extends JpaRepository<Location, Long> {
}
