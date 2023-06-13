package kr.binarybard.hireo.auth.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import kr.binarybard.hireo.auth.dto.SignUpRequest;
import kr.binarybard.hireo.member.domain.Member;
import kr.binarybard.hireo.member.dto.MemberMapper;
import kr.binarybard.hireo.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoginService implements UserDetailsService {
	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;
	private final MemberMapper memberMapper;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		var member = memberRepository.findByEmailOrThrow(username);
		return User.builder()
			.username(member.getEmail())
			.password(member.getPassword())
			.build();
	}

	public boolean isAuthenticated(SignUpRequest memberDto) {
		Member found = memberRepository.findByEmailOrThrow(memberDto.getEmail());
		if (passwordEncoder.matches(memberDto.getPassword(), found.getPassword())) {
			return true;
		} else {
			throw new IllegalStateException("비밀번호가 틀립니다!");
		}
	}
}
