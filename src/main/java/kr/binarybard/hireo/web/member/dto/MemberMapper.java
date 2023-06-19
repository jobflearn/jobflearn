package kr.binarybard.hireo.web.member.dto;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import kr.binarybard.hireo.web.auth.dto.SignUpRequest;
import kr.binarybard.hireo.web.member.domain.Member;

@Mapper(componentModel = "spring")
public interface MemberMapper {
	MemberMapper INSTANCE = Mappers.getMapper(MemberMapper.class);

	Member toEntity(SignUpRequest memberDto);
}
