package kr.binarybard.hireo.member.dto;

import kr.binarybard.hireo.member.domain.Member;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface MemberMapper {
	MemberMapper INSTANCE = Mappers.getMapper(MemberMapper.class);

	Member memberDtoToMember(MemberDto memberDto);
}
