package com.projetws.model;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "job", path = "job")
public interface JobRepository extends PagingAndSortingRepository<Job, Long>
{
//	Job findById(@Param("jobId") Integer id);
	List<Job> findAll();
	Job findByJobId(String id);
	List<Job> findAllByMinSalaryGreaterThanOrderByMaxSalaryDesc(BigDecimal minimumSalary);
	Boolean existsByJobId(String string);
}	