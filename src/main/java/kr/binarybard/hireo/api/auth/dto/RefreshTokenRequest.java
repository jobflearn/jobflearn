package kr.binarybard.hireo.api.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RefreshTokenRequest {
	@NotEmpty
	@JsonProperty("refresh_token")
	private String refreshToken;

	@Builder
	public RefreshTokenRequest(String refreshToken) {
		this.refreshToken = refreshToken;
	}
}
