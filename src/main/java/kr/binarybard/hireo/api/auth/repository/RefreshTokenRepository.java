package kr.binarybard.hireo.api.auth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.binarybard.hireo.api.auth.domain.RefreshToken;
import kr.binarybard.hireo.web.account.domain.Account;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
	Optional<RefreshToken> findByAccountAndToken(Account account, String token);

	Long deleteByAccount(Account account);
}
