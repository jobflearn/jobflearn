package kr.binarybard.hireo.config.oauth;

import java.util.Optional;
import java.util.UUID;

import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import kr.binarybard.hireo.config.oauth.userinfo.CustomOAuth2UserInfo;
import kr.binarybard.hireo.web.account.domain.Account;
import kr.binarybard.hireo.web.account.domain.Employee;
import kr.binarybard.hireo.web.account.repository.AccountRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
	private final AccountRepository accountRepository;
	private final PasswordEncoder passwordEncoder;

	public CustomOAuth2UserService(AccountRepository accountRepository, @Lazy PasswordEncoder passwordEncoder) {
		this.accountRepository = accountRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		var user = super.loadUser(userRequest);

		String registrationId = userRequest.getClientRegistration().getRegistrationId();
		var userInfo = CustomOAuth2UserInfo.of(registrationId, user.getAttributes());

		Optional<Account> foundAccount = accountRepository.findByEmail(userInfo.getEmail());
		if (foundAccount.isEmpty()) {
			Account account = Employee.builder()
				.email(userInfo.getEmail())
				.password(createDummyPassword())
				.build();
			account.encodePassword(passwordEncoder);
			accountRepository.save(account);
			return new CustomOAuth2UserPrincipal(account, userInfo);
		}
		return new CustomOAuth2UserPrincipal(foundAccount.get(), userInfo);
	}

	private String createDummyPassword() {
		return UUID.randomUUID().toString().replace("-", "");
	}
}
