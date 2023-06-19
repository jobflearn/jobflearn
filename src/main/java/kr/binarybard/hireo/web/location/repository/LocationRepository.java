package kr.binarybard.hireo.web.location.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.binarybard.hireo.web.location.domain.Location;

public interface LocationRepository extends JpaRepository<Location, Long> {
}
