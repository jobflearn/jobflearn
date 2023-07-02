package kr.binarybard.hireo.web.review.repository;

import kr.binarybard.hireo.web.review.domain.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
	Page<Review> findAllByCompanyId(Long companyId, Pageable pageable);
}
