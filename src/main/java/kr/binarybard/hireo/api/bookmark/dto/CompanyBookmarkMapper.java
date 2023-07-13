package kr.binarybard.hireo.api.bookmark.dto;

import kr.binarybard.hireo.api.bookmark.domain.CompanyBookmark;
import kr.binarybard.hireo.common.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CompanyBookmarkMapper extends BaseMapper<CompanyBookmarkResponse, CompanyBookmark> {

	@Mapping(source = "company.id", target = "companyId")
	CompanyBookmarkResponse toDto(CompanyBookmark bookmark);
}
