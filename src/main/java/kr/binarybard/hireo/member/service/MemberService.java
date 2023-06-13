package kr.binarybard.hireo.member.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import kr.binarybard.hireo.member.domain.Member;
import kr.binarybard.hireo.member.dto.MemberDto;
import kr.binarybard.hireo.member.dto.MemberMapper;
import kr.binarybard.hireo.exception.DuplicateEmailException;
import kr.binarybard.hireo.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {

	private final MemberRepository memberRepository;

	public Member findMember(Long id) {
		return memberRepository.findOne(id);
	}

	public Member findMemberByEmail(String email) {
		Member member = memberRepository.findOneByEmail(email);
		if (member == null) {
			throw new IllegalStateException("존재하지 않는 회원 입니다.");
		}
		return member;
	}
}
