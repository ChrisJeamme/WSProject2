package com.projetws.model;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "employee", path = "employee")
public interface UserRepository extends PagingAndSortingRepository<User, Long> {

	List<User> findAll();
	
	User findByEmployeeId(Long id);
	
	List<User> findByOrderBySalary();

	User findByUserName(String username);

}