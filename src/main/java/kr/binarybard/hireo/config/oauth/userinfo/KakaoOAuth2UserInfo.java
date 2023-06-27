package kr.binarybard.hireo.config.oauth.userinfo;

import java.util.Map;

import static kr.binarybard.hireo.utils.ConvertUtils.uncheckedCast;

public class KakaoOAuth2UserInfo extends CustomOAuth2UserInfo {

	protected KakaoOAuth2UserInfo(Map<String, Object> attributes) {
		super(attributes);
	}

	@Override
	public void initProperties() {
		Map<String, Object> kakaoAccount = uncheckedCast(attributes.get("kakao_account"));
		email = kakaoAccount.get("email").toString();

		Map<String, Object> profile = uncheckedCast(kakaoAccount.get("profile"));
		name = profile.get("nickname").toString();
	}

	@Override
	public CustomOAuth2Provider getProvider() {
		return CustomOAuth2Provider.KAKAO;
	}

	@Override
	public String getEmail() {
		return email;
	}

	@Override
	public String getName() {
		return name;
	}
}
