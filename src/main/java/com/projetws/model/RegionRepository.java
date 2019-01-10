package com.projetws.model;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "region", path = "region")
public interface RegionRepository extends PagingAndSortingRepository<Region, Long>
{
	List<Region> findAll();

	Region findByRegionId(Long regionId);
}