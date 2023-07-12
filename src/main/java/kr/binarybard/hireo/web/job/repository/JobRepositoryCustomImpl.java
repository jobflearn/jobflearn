package kr.binarybard.hireo.web.job.repository;

import static com.querydsl.core.types.dsl.MathExpressions.*;
import static kr.binarybard.hireo.web.company.domain.QCompany.*;
import static kr.binarybard.hireo.web.job.domain.QJob.*;
import static kr.binarybard.hireo.web.location.domain.QLocation.*;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;
import kr.binarybard.hireo.web.company.dto.CompanyListResponse;
import kr.binarybard.hireo.web.job.domain.Category;
import kr.binarybard.hireo.web.job.domain.Job;
import kr.binarybard.hireo.web.job.domain.JobType;
import kr.binarybard.hireo.web.job.dto.JobListResponse;
import kr.binarybard.hireo.web.job.dto.JobSearchCondition;
import kr.binarybard.hireo.web.location.dto.LocationCondition;

@Repository
public class JobRepositoryCustomImpl implements JobRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	public JobRepositoryCustomImpl(EntityManager entityManager) {
		queryFactory = new JPAQueryFactory(entityManager);
	}

	@Override
	public Page<JobListResponse> listJobs(Pageable pageable) {
		List<JobListResponse> jobLists = createJobListQuery()
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		JPAQuery<Job> countQuery = queryFactory
			.select(job)
			.from(job);

		return PageableExecutionUtils.getPage(jobLists, pageable, countQuery::fetchCount);
	}

	@Override
	public Page<JobListResponse> listJobsWithCondition(JobSearchCondition condition, Pageable pageable) {
		List<JobListResponse> jobList = createJobListQuery()
			.where(
				rangeQuery(condition.getLocationDto()),
				jobNameLike(condition.getKeyword()),
				categoryEq(condition.getCategory()),
				jobTypeEq(condition.getJobType()),
				salaryBetween(condition.getMinSalary(), condition.getMaxSalary())
			)
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		return PageableExecutionUtils.getPage(jobList, pageable, jobList::size);
	}

	public JPAQuery<JobListResponse> createJobListQuery() {
		return queryFactory.select(Projections.constructor(
				JobListResponse.class,
				job.id, job.name, job.description, job.category,
				job.jobType, job.startSalary, job.endSalary, job.modifiedDate
				, Projections.constructor(CompanyListResponse.class,
					company.id, company.name, company.logoHash, company.location.address)
			))
			.from(job)
			.innerJoin(job.company, company)
			.innerJoin(company.location, location);
	}

	private BooleanExpression rangeQuery(LocationCondition condition) {
		if (condition != null) {
			double latNow = condition.getLatitude();
			double lonNow = condition.getLongitude();
			if (latNow != 0.0 && lonNow != 0.0) {
				NumberPath<Double> lat = job.company.location.latitude;
				NumberPath<Double> lng = job.company.location.longitude;

				NumberExpression<Double> haversineExpression = acos(
					Expressions.asNumber(
						Expressions.asNumber(
							cos(radians(Expressions.constant(latNow))).multiply(
								cos(radians(lat)).multiply(
									cos(radians(lng).subtract(radians(Expressions.constant(lonNow))))
								)
							)
						).add(
							sin(radians(Expressions.constant(latNow))).multiply(
								sin(radians(lat))
							)
						)
					)
				).multiply(Expressions.constant(2 * 6371.0)); // Earth radius

				return haversineExpression.loe(20.0);
			}
		}
		return null;
	}

	private BooleanExpression jobNameLike(String keyword) {
		if ((StringUtils.hasText(keyword))) {
			return job.name.containsIgnoreCase(keyword);

		}
		return null;
	}

	private BooleanExpression categoryEq(Category category) {
		if (category != null) {
			return job.category.eq(category);
		}
		return null;
	}

	private BooleanExpression jobTypeEq(JobType jobType) {
		return jobType != null ? job.jobType.eq(jobType) : null;
	}

	private BooleanExpression salaryBetween(Integer salaryGoe, Integer salaryLoe) {
		if (salaryGoe != null && salaryLoe != null) {
			return salaryGoe(salaryGoe).and(salaryLoe(salaryLoe));
		}
		return null;
	}

	private BooleanExpression salaryGoe(Integer salaryGoe) {
		return salaryGoe != null ? job.startSalary.goe(salaryGoe) : null;
	}

	private BooleanExpression salaryLoe(Integer salaryLoe) {
		return salaryLoe != null ? job.endSalary.loe(salaryLoe) : null;
	}

}

