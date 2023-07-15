package kr.binarybard.hireo.web.account.service;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import kr.binarybard.hireo.common.exceptions.AuthenticationException;
import kr.binarybard.hireo.common.exceptions.EntityNotFoundException;
import kr.binarybard.hireo.common.exceptions.ErrorCode;
import kr.binarybard.hireo.web.account.domain.Account;
import kr.binarybard.hireo.web.account.dto.AccountMapper;
import kr.binarybard.hireo.web.account.dto.AccountResponse;
import kr.binarybard.hireo.web.account.repository.AccountRepository;
import kr.binarybard.hireo.web.auth.dto.SignUpRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountService {

	private final AccountRepository accountRepository;
	private final AccountMapper accountMapper;
	private final PasswordEncoder passwordEncoder;

	public Long save(SignUpRequest accountDto) {
		try {
			var account = accountMapper.resolve(accountDto);
			account.encodePassword(passwordEncoder);
			return accountRepository.save(account).getId();
		} catch (DataIntegrityViolationException e) {
			throw new AuthenticationException(ErrorCode.DUPLICATED_EMAIL, accountDto.getEmail());
		}
	}

	public Account findById(Long id) {
		return accountRepository.findById(id)
			.orElseThrow(() -> new EntityNotFoundException(ErrorCode.MEMBER_NOT_FOUND, id.toString()));
	}

	@Transactional
	public AccountResponse findByEmail(String email) {
		Account foundAccount = accountRepository.findByEmailOrThrow(email);
		return accountMapper.toDto(foundAccount);
	}

	public void deleteByEmail(String email) {
		accountRepository.deleteByEmail(email);
	}
}
