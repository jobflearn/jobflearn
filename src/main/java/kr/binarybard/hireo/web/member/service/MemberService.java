package kr.binarybard.hireo.web.member.service;

import jakarta.transaction.Transactional;
import kr.binarybard.hireo.common.exceptions.AuthenticationException;
import kr.binarybard.hireo.common.exceptions.EntityNotFoundException;
import kr.binarybard.hireo.common.exceptions.ErrorCode;
import kr.binarybard.hireo.web.auth.dto.SignUpRequest;
import kr.binarybard.hireo.web.member.domain.Member;
import kr.binarybard.hireo.web.member.dto.MemberMapper;
import kr.binarybard.hireo.web.member.dto.MemberResponse;
import kr.binarybard.hireo.web.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
			throw new AuthenticationException(ErrorCode.DUPLICATED_EMAIL, memberDto.getEmail());
		}
	}

	public Member findById(Long id) {
		return memberRepository.findById(id)
			.orElseThrow(() -> new EntityNotFoundException(ErrorCode.MEMBER_NOT_FOUND, id.toString()));
	}

	@Transactional
	public MemberResponse findByEmail(String email) {
		Member foundMember = memberRepository.findByEmailOrThrow(email);
		return memberMapper.toDto(foundMember);
	}
}
