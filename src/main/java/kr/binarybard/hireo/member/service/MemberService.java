package kr.binarybard.hireo.member.service;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import kr.binarybard.hireo.auth.dto.SignUpRequest;
import kr.binarybard.hireo.exception.DuplicateEmailException;
import kr.binarybard.hireo.exception.MemberNotFoundException;
import kr.binarybard.hireo.member.domain.Member;
import kr.binarybard.hireo.member.dto.MemberMapper;
import kr.binarybard.hireo.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {

	private final MemberRepository memberRepository;
	private final MemberMapper memberMapper;
	private final PasswordEncoder passwordEncoder;

	public Long save(SignUpRequest memberDto) {
		try {
			Member member = memberMapper.memberDtoToMember(memberDto);
			member.getEncodedPassword(passwordEncoder);
			memberRepository.save(member);
			return member.getId();
		} catch (DataIntegrityViolationException e) {
			throw new DuplicateEmailException();
		}
	}

	public Member findMember(Long id) {
		return memberRepository.findById(id)
			.orElseThrow(MemberNotFoundException::new);
	}

	public Member findMemberByEmail(String email) {
		return memberRepository.findByEmailOrThrow(email);
	}
}
