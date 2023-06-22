package kr.binarybard.hireo.web.member.service;

import kr.binarybard.hireo.common.exceptions.EntityNotFoundException;
import kr.binarybard.hireo.web.auth.dto.SignUpRequest;
import kr.binarybard.hireo.web.fixture.LoginFixture;
import kr.binarybard.hireo.web.fixture.MemberFixture;
import kr.binarybard.hireo.web.member.domain.Member;
import kr.binarybard.hireo.web.member.dto.MemberMapper;
import kr.binarybard.hireo.web.member.dto.MemberResponse;
import kr.binarybard.hireo.web.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

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
	void testSave() {
		//given
		SignUpRequest request = LoginFixture.TEST_SIGNUP_REQUEST;
		Member member = MemberFixture.createMember();

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
	void testFindById() {
		// given
		Member member = MemberFixture.createMember();
		when(memberRepository.findById(any(Long.class))).thenReturn(Optional.of(member));

		// when
		Member foundMember = memberService.findById(1L);

		// then
		assertThat(member).isEqualTo(foundMember);
	}

	@Test
	@DisplayName("회원 이메일로 찾기")
	void testFindByEmail() {
		// given
		Member member = MemberFixture.createMember();
		when(memberMapper.toDto(any(Member.class))).thenReturn(MemberFixture.MEMBER_RESPONSE);
		when(memberRepository.findByEmailOrThrow(any(String.class))).thenReturn(member);

		// when
		MemberResponse foundMember = memberService.findByEmail(member.getEmail());

		// then
		assertThat(member.getEmail()).isEqualTo(foundMember.getEmail());
	}

	@Test
	@DisplayName("존재하지 않는 이메일로 찾기")
	void testFindByEmailThrowsException() {
		// given
		when(memberRepository.findByEmailOrThrow(anyString())).thenThrow(EntityNotFoundException.class);

		// expected
		assertThatThrownBy(() -> memberService.findByEmail("test@test.com"))
			.isInstanceOf(EntityNotFoundException.class);
	}
}
