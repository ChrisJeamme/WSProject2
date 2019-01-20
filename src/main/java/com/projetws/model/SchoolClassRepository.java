package com.projetws.model;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "schoolClass", path = "schoolClass")
public interface SchoolClassRepository extends PagingAndSortingRepository<SchoolClass, Long>
{
	public List<SchoolClass> findAll();
	public boolean existsBySchoolClassId(long i);


	public SchoolClass findBySchoolClassId(long id);
	public SchoolClass findBySchool(School school);

	public List<SchoolClass> findAllBySchool(School school);
	public boolean existsBySchoolClassName(String schoolClassName);
	public boolean existsBySchoolClassNameAndYear(String schoolClassName, int year);
}