package kr.binarybard.hireo.api.bookmark.dto;

import kr.binarybard.hireo.api.bookmark.domain.Bookmark;
import kr.binarybard.hireo.common.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookmarkMapper extends BaseMapper<BookmarkResponse, Bookmark> {

	@Mapping(source = "company.id", target = "companyId")
	@Mapping(source = "job.id", target = "jobId")
	BookmarkResponse toDto(Bookmark bookmark);
}
