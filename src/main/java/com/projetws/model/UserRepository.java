package com.projetws.model;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "user", path = "user")
public interface UserRepository extends PagingAndSortingRepository<User, Long> 
{

	List<User> findAll();

	User findByUserName(String username);

	boolean existsByUserName(String string);

	boolean existsByEmail(String string);

	User findByEmail(String string);
	
//	User findByEmployeeId(Long id);
//	
//	List<User> findByOrderBySalary();
//

}