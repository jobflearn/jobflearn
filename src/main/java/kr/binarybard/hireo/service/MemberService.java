package kr.binarybard.hireo.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import kr.binarybard.hireo.domain.Member;
import kr.binarybard.hireo.domain.MemberDto;
import kr.binarybard.hireo.domain.MemberMapper;
import kr.binarybard.hireo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {

	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;
	private final MemberMapper memberMapper;

	public Long join(MemberDto memberDto) {
		validateDuplicateEmail(memberDto.getEmail());
		//MemberDto to Member
		Member member = memberMapper.memberDtoToMember(memberDto);
		member.getEncodedPassword(passwordEncoder);
		memberRepository.save(member);
		return member.getId();
	}

	private void validateDuplicateEmail(String email) {
		List<Member> isExist = memberRepository.findByEmail(email);
		if (isExist.size() > 0) {
			throw new IllegalStateException();
		}
	}

	public Member findMember(Long id) {
		return memberRepository.finOne(id);
	}

	public List<Member> findAllMembers() {
		return memberRepository.findAll();
	}

	public Member findMemberByEmail(String email) {
		Member member = memberRepository.findOneByEmail(email);
		if (member == null) {
			throw new IllegalStateException("존재하지 않는 회원 입니다.");
		}
		return member;
	}
}