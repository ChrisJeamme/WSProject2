package com.projetws.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name="photo")
@NamedQuery(name="Photo.findAll", query="SELECT r FROM Photo r")
public class Photo implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="PHOTO_ID")
	private long photoId;

	@Column(name="PHOTO_TYPE")
	private PhotoType Type;
	
//	Pour les types individuels (Je sais faudrait des nouvelles classe qui extends etc. mais j'attends que tout marche avant de rajouter des sources d'erreur)
	@ManyToOne
	@JoinColumn(name="CHILD_ID")
	private Child child;

//	Pour les types photo de classe
	@ManyToOne
	@JoinColumn(name="SCHOOL_CLASS_ID")
	private SchoolClass schoolClass;

	public Photo(){}

	public long getPhotoId() {
		return photoId;
	}

	public void setPhotoId(long photoId) {
		this.photoId = photoId;
	}

	public PhotoType getType() {
		return Type;
	}

	public void setType(PhotoType type) {
		Type = type;
	}

	public Child getChild() {
		return child;
	}

	public void setChild(Child child) {
		this.child = child;
	}

	public SchoolClass getSchoolClass() {
		return schoolClass;
	}

	public void setSchoolClass(SchoolClass schoolClass) {
		this.schoolClass = schoolClass;
	}
	
	
}