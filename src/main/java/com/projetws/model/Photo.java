package com.projetws.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name="photo")
@NamedQuery(name="Photo.findAll", query="SELECT r FROM Photo r")
public class Photo implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="PHOTO_ID")
	@GeneratedValue
	private long photoId;

	@Column(name="PHOTO_TYPE")
	private PhotoType Type;
	
	@Column(name="IMAGE_NAME")
	private String name;
	
	@Column(name="DESCRIPTION")
	private String description;

	//@ElementCollection
	//@CollectionTable(name = "child_on_photo", joinColumns = {@JoinColumn(name = "CHILD_ID", referencedColumnName="CHILD_ID")})   
	//@JoinColumn(name="CHILD_ID")
	@JsonIgnore
	@ManyToMany
	private List<Child> childs;
	
//	Pour les types photo de classe
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="SCHOOL_CLASS_ID")
	private SchoolClass schoolClass;
 
	@Column(name="PHOTO_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;
	
	@Lob
	private byte[] data;
	
	@Column(name="PHOTO_EXTENSION")
	private String extension;
	
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

	public List<Child> getChilds() {
		return childs;
	}

	public void setChilds(List<Child> childs) {
		this.childs = childs;
	}

	public SchoolClass getSchoolClass() {
		return schoolClass;
	}

	public void setSchoolClass(SchoolClass schoolClass) {
		this.schoolClass = schoolClass;
	}
	
	public Date getDate() { 
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

}