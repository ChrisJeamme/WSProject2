package com.projetws.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name="school")
@NamedQuery(name="School.findAll", query="SELECT r FROM School r")
public class School implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="SCHOOL_ID")
	private long schoolId;

	@Column(name="SCHOOL_NAME")
	private String schoolName;
	
	@OneToOne
	@JoinColumn(name="USER_ID")
	private User manager;
	
	@OneToMany
	@JoinColumn(name="SCHOOL_CLASS_ID")
	private List<SchoolClass> schoolClasses;

	public School(){}

	public long getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(long schoolId) {
		this.schoolId = schoolId;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public User getManager() {
		return manager;
	}

	public void setManager(User manager) {
		this.manager = manager;
	}

	public List<SchoolClass> getSchoolClasses() {
		return schoolClasses;
	}

	public void setSchoolClasses(List<SchoolClass> schoolClasses) {
		this.schoolClasses = schoolClasses;
	}
	
	
}