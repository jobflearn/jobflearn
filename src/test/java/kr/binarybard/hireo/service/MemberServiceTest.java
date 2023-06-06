package kr.binarybard.hireo.service;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import kr.binarybard.hireo.domain.Member;
import kr.binarybard.hireo.domain.MemberDto;
import kr.binarybard.hireo.repository.MemberRepository;

@SpringBootTest
@Transactional
class MemberServiceTest {

	@Autowired
	MemberService memberService;
	@Autowired
	MemberRepository memberRepository;

	@Test
	public void 회원가입() throws Exception {
		//given
		MemberDto memberDto = new MemberDto();
		memberDto.setEmail("abc@naver.com");
		memberDto.setPassword("1234");
		memberDto.setRole("FREELANCER");
		memberDto.setName("fff");
		//when
		Long joinedId = memberService.join(memberDto);
		//then
		Member member = memberService.findMember(joinedId);
		Assertions.assertThat(member.getId()).isEqualTo(joinedId);

	}

	@Test
	public void 이메일조회() throws Exception {
		//given
		MemberDto memberDto = new MemberDto();
		memberDto.setEmail("abc@naver.com");
		memberDto.setPassword("1234");
		memberDto.setRole("FREELANCER");
		memberDto.setName("fff");
		Long joinedId = memberService.join(memberDto);
		//when
		Member member = memberService.findMemberByEmail(memberDto.getEmail());
		//then
		Assertions.assertThat(joinedId).isEqualTo(member.getId());

	}

	@Test
	public void 전체_회원조회() throws Exception {
		//given
		MemberDto memberDtoA = getMemberDto("a@naver.com", "123", "fay", "FREELANCER");
		MemberDto memberDtoB = getMemberDto("b@naver.com", "1234", "fay", "FREELANCER");
		MemberDto memberDtoC = getMemberDto("c@naver.com", "1235", "fay", "FREELANCER");
		MemberDto memberDtoD = getMemberDto("d@naver.com", "1236", "fay", "FREELANCER");
		memberService.join(memberDtoA);
		memberService.join(memberDtoB);
		memberService.join(memberDtoC);
		memberService.join(memberDtoD);
		//when
		List<Member> allMembers = memberService.findAllMembers();
		//then
		Assertions.assertThat(allMembers.size()).isEqualTo(4);

	}

	public MemberDto getMemberDto(String email, String password, String name, String role) {
		MemberDto memberDto = new MemberDto();
		memberDto.setName(name);
		memberDto.setEmail(email);
		memberDto.setRole(role);
		memberDto.setPassword(password);
		return memberDto;
	}

}
