package com.projetws.model;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "employee", path = "employee")
public interface EmployeeRepository extends PagingAndSortingRepository<Employee, Long> {

	List<Employee> findAll();
	
	Employee findByEmployeeId(Long id);
	
	List<Employee> findByOrderBySalary();

	Employee findByUserName(String username);

}