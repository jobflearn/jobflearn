package kr.binarybard.hireo.config.oauth;

import kr.binarybard.hireo.config.oauth.userinfo.CustomOAuth2UserInfo;
import kr.binarybard.hireo.web.member.domain.Member;
import kr.binarybard.hireo.web.member.domain.Role;
import kr.binarybard.hireo.web.member.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;

	public CustomOAuth2UserService(MemberRepository memberRepository, @Lazy PasswordEncoder passwordEncoder) {
		this.memberRepository = memberRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		var user = super.loadUser(userRequest);

		String registrationId = userRequest.getClientRegistration().getRegistrationId();
		var userInfo = CustomOAuth2UserInfo.of(registrationId, user.getAttributes());

		Optional<Member> foundMember = memberRepository.findByEmail(userInfo.getEmail());
		if (foundMember.isEmpty()) {
			Member member = Member.builder()
				.email(userInfo.getEmail())
				.password(createDummyPassword())
				.role(Role.FREELANCER)
				.build();
			member.encodePassword(passwordEncoder);
			memberRepository.save(member);
			return new CustomOAuth2UserPrincipal(member, userInfo);
		}
		return new CustomOAuth2UserPrincipal(foundMember.get(), userInfo);
	}

	private String createDummyPassword() {
		return UUID.randomUUID().toString().replace("-", "");
	}
}
