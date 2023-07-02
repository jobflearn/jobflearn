package kr.binarybard.hireo.web.review.dto;

import kr.binarybard.hireo.common.BaseMapper;
import kr.binarybard.hireo.web.review.domain.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReviewRequestMapper extends BaseMapper<ReviewRequest, Review> {

	@Mapping(source = "createdDate", target = "postedAt")
	ReviewRequest toDto(Review review);
}
