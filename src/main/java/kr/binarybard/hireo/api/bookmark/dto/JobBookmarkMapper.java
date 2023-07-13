package kr.binarybard.hireo.api.bookmark.dto;

import kr.binarybard.hireo.api.bookmark.domain.CompanyBookmark;
import kr.binarybard.hireo.api.bookmark.domain.JobBookmark;
import kr.binarybard.hireo.common.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface JobBookmarkMapper extends BaseMapper<JobBookmarkResponse, CompanyBookmark> {

	@Mapping(source = "job.id", target = "jobId")
	JobBookmarkResponse toDto(JobBookmark bookmark);
}
