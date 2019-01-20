package com.projetws.model;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "school", path = "school")
public interface SchoolRepository extends PagingAndSortingRepository<School, Long>
{
	public List<School> findAll();
//	public School findByManagerId();

	public School findByManager(User manager);

	public boolean existsByManager(User manager);

	public boolean existsBySchoolName(String string);

	public School findBySchoolName(String string);
	
	public School findBySchoolId(long schoolId);

	public boolean existsBySchoolId(long schoolId);

//	public List<Country> findByCountryName(String countryName);
//	public Country findByCountryId(String id);
}