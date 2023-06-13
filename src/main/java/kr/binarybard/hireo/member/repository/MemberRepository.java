package kr.binarybard.hireo.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.binarybard.hireo.exception.MemberNotFoundException;
import kr.binarybard.hireo.member.domain.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

	Optional<Member> findByEmail(String email);

	default Member findByEmailOrThrow(String email) {
		return findByEmail(email)
			.orElseThrow(MemberNotFoundException::new);
	}
}
