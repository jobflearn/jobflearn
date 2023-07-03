package kr.binarybard.hireo.web.member.dto;

import kr.binarybard.hireo.api.bookmark.dto.CompanyBookmarkMapper;
import kr.binarybard.hireo.api.bookmark.dto.JobBookmarkMapper;
import kr.binarybard.hireo.web.auth.dto.SignUpRequest;
import kr.binarybard.hireo.web.member.domain.Member;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {JobBookmarkMapper.class, CompanyBookmarkMapper.class})
public interface MemberMapper {
	Member toEntity(SignUpRequest memberDto);

	@Mapping(source = "companyBookmarks", target = "companyBookmarks")
	@Mapping(source = "jobBookmarks", target = "jobBookmarks")
	MemberResponse toDto(Member member);
}
