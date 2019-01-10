package com.projetws.model;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "jobHistory", path = "jobHistory")
public interface JobHistoryRepository extends PagingAndSortingRepository<JobHistory, JobHistoryPK>
{
	List<JobHistory> findAll();
	
	JobHistory findByIdEmployeeIdAndIdStartDate(Long employeeId, Date startDate);
}	