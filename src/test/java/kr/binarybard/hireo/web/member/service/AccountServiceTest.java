package kr.binarybard.hireo.web.member.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import kr.binarybard.hireo.api.auth.dto.SignUpRequest;
import kr.binarybard.hireo.common.exceptions.EntityNotFoundException;
import kr.binarybard.hireo.common.fixture.AccountFixture;
import kr.binarybard.hireo.common.fixture.LoginFixture;
import kr.binarybard.hireo.web.account.domain.Account;
import kr.binarybard.hireo.web.account.dto.AccountMapper;
import kr.binarybard.hireo.web.account.dto.AccountResponse;
import kr.binarybard.hireo.web.account.repository.AccountRepository;
import kr.binarybard.hireo.web.account.service.AccountService;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

	@Mock
	AccountRepository accountRepository;

	@Mock
	AccountMapper accountMapper;

	@Mock
	PasswordEncoder passwordEncoder;

	@InjectMocks
	AccountService accountService;

	@Test
	@DisplayName("회원 정보 저장")
	void testSave() {
		//given
		SignUpRequest request = LoginFixture.TEST_SIGNUP_REQUEST_JOBSEEKER;
		Account account = AccountFixture.createAccount();

		when(accountMapper.resolve(any(SignUpRequest.class))).thenReturn(account);
		when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
		when(accountRepository.save(any(Account.class))).thenReturn(account);

		//when
		accountService.save(request);

		//then
		verify(accountRepository, times(1)).save(account);
	}

	@Test
	@DisplayName("회원 ID로 찾기")
	void testFindById() {
		// given
		Account account = AccountFixture.createAccount();
		when(accountRepository.findById(any(Long.class))).thenReturn(Optional.of(account));

		// when
		Account foundAccount = accountService.findById(1L);

		// then
		assertThat(account).isEqualTo(foundAccount);
	}

	@Test
	@DisplayName("회원 이메일로 찾기")
	void testFindByEmail() {
		// given
		Account account = AccountFixture.createAccount();
		when(accountMapper.toDto(any(Account.class))).thenReturn(AccountFixture.ACCOUNT_RESPONSE);
		when(accountRepository.findByEmailOrThrow(any(String.class))).thenReturn(account);

		// when
		AccountResponse foundAccount = accountService.findByEmail(account.getEmail());

		// then
		assertThat(account.getEmail()).isEqualTo(foundAccount.getEmail());
	}

	@Test
	@DisplayName("존재하지 않는 이메일로 찾기")
	void testFindByEmailThrowsException() {
		// given
		when(accountRepository.findByEmailOrThrow(anyString())).thenThrow(EntityNotFoundException.class);

		// expected
		assertThatThrownBy(() -> accountService.findByEmail("test@test.com"))
			.isInstanceOf(EntityNotFoundException.class);
	}
}
