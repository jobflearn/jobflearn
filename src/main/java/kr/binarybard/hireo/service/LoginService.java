package kr.binarybard.hireo.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import kr.binarybard.hireo.domain.Member;
import kr.binarybard.hireo.domain.MemberDto;
import kr.binarybard.hireo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoginService {
	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;

	public Boolean login(MemberDto memberDto) {
		Member found = memberRepository.findOneByEmail(memberDto.getEmail());
		if (passwordEncoder.matches(memberDto.getPassword(), found.getPassword())) {
			return true;
		} else {
			throw new IllegalStateException("비밀번호가 틀립니다!");
		}
	}
}
