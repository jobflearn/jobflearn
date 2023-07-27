package kr.binarybard.hireo.api.auth.dto;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import kr.binarybard.hireo.common.validation.constraints.FieldMatch;
import kr.binarybard.hireo.web.account.domain.AccountType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@FieldMatch(first = "password", second = "passwordConfirm")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SignUpRequest {

	@NotBlank
	@Email(message = "{invalid.email}", regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
	@Length(max = 32)
	private String email;

	@NotBlank
	@Length(min = 10, max = 20)
	private String password;

	private String passwordConfirm;

	private String name;
	private AccountType type;

	@Builder
	public SignUpRequest(String email, String password, String passwordConfirm, String name, AccountType type) {
		this.email = email;
		this.password = password;
		this.passwordConfirm = passwordConfirm;
		this.name = name;
		this.type = type;
	}
}
