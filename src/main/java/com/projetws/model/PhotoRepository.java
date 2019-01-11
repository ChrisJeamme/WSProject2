package com.projetws.model;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "photo", path = "photo")
public interface PhotoRepository extends PagingAndSortingRepository<Photo, Long>
{
	public List<Photo> findAll();
//	public List<Country> findByCountryName(String countryName);
//	public Country findByCountryId(String id);
}