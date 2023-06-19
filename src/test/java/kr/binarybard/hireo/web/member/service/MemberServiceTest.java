package kr.binarybard.hireo.web.member.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import kr.binarybard.hireo.web.auth.dto.SignUpRequest;
import kr.binarybard.hireo.exception.MemberNotFoundException;
import kr.binarybard.hireo.web.fixture.LoginFixture;
import kr.binarybard.hireo.web.fixture.MemberFixture;
import kr.binarybard.hireo.web.member.domain.Member;
import kr.binarybard.hireo.web.member.dto.MemberMapper;
import kr.binarybard.hireo.web.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

	@Mock
	MemberRepository memberRepository;

	@Mock
	MemberMapper memberMapper;

	@Mock
	PasswordEncoder passwordEncoder;

	@InjectMocks
	MemberService memberService;

	@Test
	@DisplayName("회원 정보 저장")
	public void testSave() {
		//given
		SignUpRequest request = LoginFixture.TEST_SIGNUP_REQUEST;
		Member member = MemberFixture.TEST_MEMBER;

		when(memberMapper.toEntity(any(SignUpRequest.class))).thenReturn(member);
		when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
		when(memberRepository.save(any(Member.class))).thenReturn(member);

		//when
		memberService.save(request);

		//then
		verify(memberRepository, times(1)).save(member);
	}

	@Test
	@DisplayName("회원 ID로 찾기")
	public void testFindById() {
		// given
		Member member = MemberFixture.TEST_MEMBER;
		when(memberRepository.findById(any(Long.class))).thenReturn(Optional.of(member));

		// when
		Member foundMember = memberService.findById(1L);

		// then
		assertThat(member).isEqualTo(foundMember);
	}

	@Test
	@DisplayName("회원 이메일로 찾기")
	public void testFindByEmail() {
		// given
		Member member = MemberFixture.TEST_MEMBER;
		when(memberRepository.findByEmail(any(String.class))).thenReturn(Optional.of(member));

		// when
		Member foundMember = memberService.findByEmail(member.getEmail());

		// then
		assertThat(member).isEqualTo(foundMember);
	}

	@Test
	@DisplayName("존재하지 않는 이메일로 찾기")
	public void testFindByEmailThrowsException() {
		// given
		when(memberRepository.findByEmail(anyString())).thenReturn(Optional.empty());

		// expected
		assertThatThrownBy(() -> memberService.findByEmail("test@test.com"))
			.isInstanceOf(MemberNotFoundException.class);
	}
}
