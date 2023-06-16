package kr.binarybard.hireo.config.oauth.userinfo;

import kr.binarybard.hireo.exception.UnsupportedOAuth2ProviderException;

import java.util.Map;

public abstract class CustomOAuth2UserInfo {
	protected final Map<String, Object> attributes;

	protected String email;
	protected String name;

	protected CustomOAuth2UserInfo(Map<String, Object> attributes) {
		this.attributes = attributes;
		initProperties();
	}

	/**
	 * 기본 인증 정보를 초기화합니다.
	 * <p>
	 * 생성자에서 자동으로 이 메서드를 호출합니다.
	 */
	public abstract void initProperties();

	/**
	 * 인증 정보에서 프로바이더를 반환합니다.
	 * <p>
	 * 프로바이더는 Google, Kakao, Naver 등과 같은 OAuth 서비스를 제공하는 제공자를 말합니다.
	 *
	 * @return 프로바이더 (null이 아님)
	 */
	public abstract CustomOAuth2Provider getProvider();

	public abstract String getEmail();

	public abstract String getName();

	public Map<String, Object> getAttributes() {
		return attributes;
	}

	/**
	 * 프로바이더에게 받은 인증 정보를 반환합니다.
	 * <p>
	 * {@code attributes}는 프로바이더가 보낸 인증된 사용자의 세부 정보를 담고 있습니다.
	 *
	 * @param registrationId 프로바이더 ID
	 * @param attributes 최종 사용자(리소스 소유자)의 속성
	 * @return 인증 정보
	 */
	public static CustomOAuth2UserInfo of(String registrationId, Map<String, Object> attributes) {
		if (CustomOAuth2Provider.KAKAO.equalsWith(registrationId)) {
			return new KakaoOAuth2UserInfo(attributes);
		} else if (CustomOAuth2Provider.GOOGLE.equalsWith(registrationId)) {
			return new GoogleOAuth2UserInfo(attributes);
		} else {
			throw new UnsupportedOAuth2ProviderException(registrationId);
		}
	}
}
