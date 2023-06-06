package kr.binarybard.hireo.repository;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import kr.binarybard.hireo.domain.Member;
import kr.binarybard.hireo.domain.Role;

@SpringBootTest
class MemberRepositoryTest {

	@Autowired
	MemberRepository memberRepository;

	@Test
	@Transactional
	@Rollback(false)
	public void 회원저장() throws Exception {
		//given
		Member member = new Member("dbrkdals1994@google.com", "djdjdj", "gangmin", Role.FREELANCER);
		//when
		memberRepository.save(member);
		//then
		Member findMember = memberRepository.finOne(member.getId());
		assertThat(member).isEqualTo(findMember);
	}

	@Test
	@Transactional
	@Rollback(false)
	public void 전체회원조회() throws Exception {
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
		List<Member> members = memberRepository.findAll();
		//then
		assertThat(members.size()).isEqualTo(4);
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
		assertThat(findMemberId).isEqualTo(memberB.getId());

	}

}
