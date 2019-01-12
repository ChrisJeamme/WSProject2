package com.projetws.model;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "schoolClass", path = "schoolClass")
public interface SchoolClassRepository extends PagingAndSortingRepository<SchoolClass, Long>
{
	public List<SchoolClass> findAll();
//	public List<Country> findByCountryName(String countryName);
//	public Country findByCountryId(String id);

	public boolean existsBySchoolClassId(int i);


	public SchoolClass findBySchoolClassId(int id);
	public SchoolClass findBySchool(School school);

	public List<SchoolClass> findAllBySchool(School school);
}