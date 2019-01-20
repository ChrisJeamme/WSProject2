package com.projetws.model;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "child", path = "child")
public interface ChildRepository extends PagingAndSortingRepository<Child, Long>
{
	public List<Child> findAll();
	public Child findByChildId(long id);
	public boolean existsByChildFirstName(String fn);
	public Child findByChildFirstName(String string);
	public List<Child> findByParent(long userId);
}