package kr.binarybard.hireo.repository;

import static org.assertj.core.api.Assertions.*;

import kr.binarybard.hireo.member.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import kr.binarybard.hireo.member.domain.Member;
import kr.binarybard.hireo.member.domain.Role;

@SpringBootTest
class MemberRepositoryTest {

	@Autowired
    MemberRepository memberRepository;
	@Autowired
	PasswordEncoder passwordEncoder;

	@Test
	@Transactional
	@Rollback(false)
	public void 회원저장() throws Exception {
		//given
		Member member = new Member("dbrkdals1994@google.com", "djdjdj", "gangmin", Role.FREELANCER);
		//when
		memberRepository.save(member);
		//then
		Member findMember = memberRepository.findOne(member.getId());
		assertThat(member).isEqualTo(findMember);
	}

	@Test
	@Transactional
	@Rollback(value = false)
	public void 이메일_단건조회() throws Exception {
		//given
		Member memberA = new Member("dbrkdals1994@google.com", "djdjdj", "gangmin", Role.FREELANCER);
		Member memberB = new Member("ygm@google.com", "djdjdj", "gangmin", Role.FREELANCER);
		Member memberC = new Member("derwrls1994@google.com", "djdjdj", "gangmin", Role.FREELANCER);
		Member memberD = new Member("d1231234@google.com", "djdjdj", "gangmin", Role.FREELANCER);
		memberRepository.save(memberA);
		memberRepository.save(memberB);
		memberRepository.save(memberC);
		memberRepository.save(memberD);
		//when
		String memberCEmail = memberC.getEmail();
		Member findMember = memberRepository.findOneByEmail(memberCEmail);
		Long findMemberId = findMember.getId();
		//then
		assertThat(findMemberId).isEqualTo(memberC.getId());
	}
}
