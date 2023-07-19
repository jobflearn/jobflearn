package kr.binarybard.hireo.api.auth.domain;

import java.time.Instant;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import kr.binarybard.hireo.web.account.domain.Account;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity(name = "refresh_tokens")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RefreshToken {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne
	@JoinColumn(name = "account_id")
	private Account account;

	private String token;

	private Instant expiryDate;

	@Builder
	public RefreshToken(Account account, String token, Instant expiryDate) {
		this.account = account;
		this.token = token;
		this.expiryDate = expiryDate;
	}
}
