package kr.binarybard.hireo.api.auth.dto;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SignInRequest {
	@NotEmpty
	@Email
	@Length(max = 32)
	private String email;

	@NotEmpty
	@Length(min = 8, max = 20)
	private String password;

	@Builder
	public SignInRequest(String email, String password) {
		this.email = email;
		this.password = password;
	}
}
