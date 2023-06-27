package kr.binarybard.hireo.web.member.dto;

import kr.binarybard.hireo.api.bookmark.dto.BookmarkMapper;
import kr.binarybard.hireo.web.auth.dto.SignUpRequest;
import kr.binarybard.hireo.web.member.domain.Member;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {BookmarkMapper.class})
public interface MemberMapper {
	Member toEntity(SignUpRequest memberDto);

	@Mapping(source = "bookmarks", target = "bookmarks")
	MemberResponse toDto(Member member);
}
