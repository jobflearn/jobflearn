package kr.binarybard.hireo.domain;

import java.time.LocalDateTime;

import org.springframework.security.crypto.password.PasswordEncoder;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Member클래스는 도메인 모델 패턴을 써야하는가
 */

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String email;
	private String password;
	private String name;

	@Enumerated(EnumType.STRING)
	private Role role;
	private final LocalDateTime createAt = LocalDateTime.now();
	private LocalDateTime updatedAt = LocalDateTime.now();

	public Member(String email, String password, String name, Role role) {
		this.email = email;
		this.password = password;
		this.role = role;
		this.name = name;
	}

	public void changeRole(Role role) {
		this.role = role;
	}

	public void changePassword(PasswordEncoder passwordEncoder, String newPassword) {
		this.password = newPassword;
		getEncodedPassword(passwordEncoder);
	}

	public void changeName(String newName) {
		this.name = newName;
	}

	public void getEncodedPassword(PasswordEncoder passwordEncoder) {
		this.password = passwordEncoder.encode(this.password);
	}
}
