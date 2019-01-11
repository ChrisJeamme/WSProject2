package com.projetws.model;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "school", path = "school")
public interface SchoolRepository extends PagingAndSortingRepository<School, Long>
{
	public List<School> findAll();
//	public School findByManagerId();

//	public List<Country> findByCountryName(String countryName);
//	public Country findByCountryId(String id);
}