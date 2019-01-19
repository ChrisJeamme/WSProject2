package com.projetws.model;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "photo", path = "photo")
public interface PhotoRepository extends PagingAndSortingRepository<Photo, Long>
{
	public List<Photo> findAll();
	public Photo findByPhotoId(long photoId);
	public List<Photo> findAllBySchoolClass(long schoolClassId);
	//public List<Photo> findByChild_ChildId(long childId);
	//public List<Photo> findAllBySchoolClassAndChild_ChildId(long schoolClassId, long childId);
	//public List<Photo> findByChild_ChildIdAndDateBetween(long childId, Date before, Date after);
}