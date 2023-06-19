package kr.binarybard.hireo.web.member.service;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import kr.binarybard.hireo.exception.DuplicateEmailException;
import kr.binarybard.hireo.exception.MemberNotFoundException;
import kr.binarybard.hireo.web.auth.dto.SignUpRequest;
import kr.binarybard.hireo.web.member.domain.Member;
import kr.binarybard.hireo.web.member.dto.MemberMapper;
import kr.binarybard.hireo.web.member.repository.MemberRepository;
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
			var member = memberMapper.toEntity(memberDto);
			member.encodePassword(passwordEncoder);
			return memberRepository.save(member).getId();
		} catch (DataIntegrityViolationException e) {
			throw new DuplicateEmailException();
		}
	}

	public Member findById(Long id) {
		return memberRepository.findById(id)
			.orElseThrow(MemberNotFoundException::new);
	}

	public Member findByEmail(String email) {
		return memberRepository.findByEmail(email)
			.orElseThrow(MemberNotFoundException::new);
	}
}
