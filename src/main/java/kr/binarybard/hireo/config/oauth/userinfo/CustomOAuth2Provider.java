package kr.binarybard.hireo.config.oauth.userinfo;

import lombok.Getter;

@Getter
public enum CustomOAuth2Provider {
	KAKAO("kakao"),
	GOOGLE("google");

	private final String provider;

	CustomOAuth2Provider(String provider) {
		this.provider = provider;
	}

	public boolean equalsWith(String provider) {
		return this.provider.equalsIgnoreCase(provider);
	}
}
