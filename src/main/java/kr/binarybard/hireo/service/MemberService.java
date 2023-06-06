package kr.binarybard.hireo.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import kr.binarybard.hireo.domain.Member;
import kr.binarybard.hireo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {

	MemberRepository memberRepository;
	PasswordEncoder passwordEncoder;

	public Long join(Member member) {
		validateDuplicateEmail(member.getEmail());
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
