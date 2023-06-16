package kr.binarybard.hireo.config.oauth.userinfo;

import java.util.Map;

public class GoogleOAuth2UserInfo extends CustomOAuth2UserInfo {

	protected GoogleOAuth2UserInfo(Map<String, Object> attributes) {
		super(attributes);
	}

	@Override
	public void initProperties() {
		email = attributes.get("email").toString();
		name = attributes.get("name").toString();
	}

	@Override
	public CustomOAuth2Provider getProvider() {
		return CustomOAuth2Provider.GOOGLE;
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
