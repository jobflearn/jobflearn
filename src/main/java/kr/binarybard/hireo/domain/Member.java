package kr.binarybard.hireo.domain;

import java.time.LocalDateTime;

import org.springframework.security.crypto.password.PasswordEncoder;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * Member클래스는 도메인 모델 패턴을 써야하는가
 */


@Entity
@Getter
@Builder
@RequiredArgsConstructor
public class Member {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String email;
	private String password;
	private String name;

	@Enumerated(EnumType.STRING)
	private Role role;
	private LocalDateTime createAt;
	private LocalDateTime updatedAt;

	public void getEncodedPassword(PasswordEncoder passwordEncoder){
		this.password =  passwordEncoder.encode(this.password);
	}
}
