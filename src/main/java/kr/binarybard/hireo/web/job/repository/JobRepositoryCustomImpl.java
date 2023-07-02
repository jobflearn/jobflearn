package kr.binarybard.hireo.web.job.repository;

import static kr.binarybard.hireo.web.company.domain.QCompany.*;
import static kr.binarybard.hireo.web.job.domain.QJob.*;
import static kr.binarybard.hireo.web.location.domain.QLocation.*;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;
import kr.binarybard.hireo.web.company.dto.CompanyListResponse;
import kr.binarybard.hireo.web.job.domain.Job;
import kr.binarybard.hireo.web.job.dto.JobListResponse;

@Repository
public class JobRepositoryCustomImpl implements JobRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	public JobRepositoryCustomImpl(EntityManager entityManager) {
		queryFactory = new JPAQueryFactory(entityManager);
	}

	@Override
	public Page<JobListResponse> listJobs(Pageable pageable) {
		List<JobListResponse> jobLists = queryFactory.select(Projections.constructor(
				JobListResponse.class,
				job.id, job.name, job.description,
				job.jobType, job.startSalary, job.endSalary, job.modifiedDate
				, Projections.constructor(CompanyListResponse.class,
					company.id, company.name, company.logoHash, company.location.address)
			))
			.from(job)
			.join(job.company, company)
			.join(company.location, location)
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		JPAQuery<Job> countQuery = queryFactory
			.select(job)
			.from(job);

		return PageableExecutionUtils.getPage(jobLists, pageable, countQuery::fetchCount);
	}
}

