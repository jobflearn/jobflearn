package kr.binarybard.hireo.auth.service;

import java.util.List;
import kr.binarybard.hireo.exception.DuplicateEmailException;
import kr.binarybard.hireo.member.dto.MemberMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import kr.binarybard.hireo.member.domain.Member;
import kr.binarybard.hireo.member.dto.MemberDto;
import kr.binarybard.hireo.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoginService {
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
			throw new DuplicateEmailException("중복된 이메일 입니다.");
		}
	}

	public Boolean isAuthenticated(MemberDto memberDto) {
		Member found = memberRepository.findOneByEmail(memberDto.getEmail());
		if (passwordEncoder.matches(memberDto.getPassword(), found.getPassword())) {
			return true;
		} else {
			throw new IllegalStateException("비밀번호가 틀립니다!");
		}
	}
}
