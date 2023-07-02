package kr.binarybard.hireo.web.review.dto;

import kr.binarybard.hireo.common.BaseMapper;
import kr.binarybard.hireo.web.review.domain.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReviewResponseMapper extends BaseMapper<ReviewResponse, Review> {

	@Mapping(source = "createdDate", target = "postedAt")
	ReviewResponse toDto(Review review);

	List<ReviewResponse> toDtoList(List<Review> reviews);
}
