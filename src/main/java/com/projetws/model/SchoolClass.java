package com.projetws.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name="schoolClass")
@NamedQuery(name="SchoolClass.findAll", query="SELECT r FROM SchoolClass r")
public class SchoolClass implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="SCHOOL_CLASS_ID")
	@GeneratedValue
	private long schoolClassId;

	@Column(name="SCHOOL_CLASS_NAME")
	private String schoolClassName;
	
	@Column(name="YEAR")
	private int year;

	@ManyToOne
	@JoinColumn(name="SCHOOL_ID")
	private School school;
	
	@ManyToMany
	@JoinColumn(name="CHILDS_ID")
	private List<Child> children;

	public SchoolClass(){}

	public long getSchoolClassId() {
		return schoolClassId;
	}

	public void setSchoolClassId(long schoolClassId) {
		this.schoolClassId = schoolClassId;
	}

	public String getSchoolClassName() {
		return schoolClassName;
	}

	public void setSchoolClassName(String schoolClassName) {
		this.schoolClassName = schoolClassName;
	}

	public School getSchool() {
		return school;
	}

	public void setSchool(School school) {
		this.school = school;
	}

	public List<Child> getChildren() {
		return children;
	}

	public void setChildren(List<Child> children) {
		this.children = children;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}
}