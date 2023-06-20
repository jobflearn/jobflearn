package kr.binarybard.hireo.api.auth.domain;

import jakarta.persistence.*;
import kr.binarybard.hireo.web.member.domain.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Getter
@Entity(name = "refresh_tokens")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RefreshToken {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne
	@JoinColumn(name = "member_id")
	private Member member;

	private String token;

	private Instant expiryDate;

	@Builder
	public RefreshToken(Member member, String token, Instant expiryDate) {
		this.member = member;
		this.token = token;
		this.expiryDate = expiryDate;
	}
}
