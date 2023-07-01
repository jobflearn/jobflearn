package kr.binarybard.hireo.web.job.dto;

import kr.binarybard.hireo.common.BaseMapper;
import kr.binarybard.hireo.web.company.dto.CompanyMapper;
import kr.binarybard.hireo.web.job.domain.Job;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {CompanyMapper.class})
public interface JobMapper extends BaseMapper<JobResponse, Job> {
	@Mapping(source = "createdDate", target = "postedAt")
	JobResponse toDto(Job job);
}
