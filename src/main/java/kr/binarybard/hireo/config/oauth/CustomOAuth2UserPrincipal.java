package kr.binarybard.hireo.config.oauth;

import java.util.Collections;
import java.util.Map;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import kr.binarybard.hireo.config.oauth.userinfo.CustomOAuth2UserInfo;
import kr.binarybard.hireo.web.account.domain.Account;

public class CustomOAuth2UserPrincipal extends User implements OAuth2User {
	private transient CustomOAuth2UserInfo userInfo;

	public CustomOAuth2UserPrincipal(Account account) {
		super(account.getEmail(), account.getPassword(),
			Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
	}

	public CustomOAuth2UserPrincipal(Account account, CustomOAuth2UserInfo userInfo) {
		this(account);
		this.userInfo = userInfo;
	}

	@Override
	public Map<String, Object> getAttributes() {
		return userInfo.getAttributes();
	}

	@Override
	public String getName() {
		return userInfo.getName();
	}
}
