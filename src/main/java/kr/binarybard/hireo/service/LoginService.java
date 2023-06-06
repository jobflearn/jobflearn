package kr.binarybard.hireo.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import kr.binarybard.hireo.domain.Member;
import kr.binarybard.hireo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoginService {
	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;

	public Boolean login(Member member) {
		member.getEncodedPassword(passwordEncoder);
		Member found = memberRepository.findOneByEmail(member.getEmail());
		if (found.getPassword().equals(member.getPassword())) {
			return true;
		} else {
			return false;
		}
	}
}
