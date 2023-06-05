package kr.binarybard.hireo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import kr.binarybard.hireo.domain.Member;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MemberRepository {
	private final EntityManager em;

	public void save(Member member) {
		em.persist(member);
	}

	public Member finOne(Long memberId) {
		return em.find(Member.class, memberId);
	}

	public Optional<Member> findOneByEmail(String email) {
		return em.createQuery("select m from Member m where m.email=:email")
			.setParameter("email", email).getResultList().stream().findAny();
	}

	public List<Member> findByEmail(String email) {
		return em.createQuery("select m from Member m where m.email =:email")
			.setParameter("email", email).getResultList();
	}

	public List<Member> findAll(){
		return em.createQuery("select m from Member m", Member.class)
			.getResultList();
	}



}
