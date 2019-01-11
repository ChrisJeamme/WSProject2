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
@Table(name="schoolClass")
@NamedQuery(name="SchoolClass.findAll", query="SELECT r FROM SchoolClass r")
public class SchoolClass implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="SCHOOL_CLASS_ID")
	private long schoolClassId;

	@Column(name="SCHOOL_CLASS_NAME")
	private String schoolClassName;

	@ManyToOne
	@JoinColumn(name="SCHOOL_ID")
	private School school;
}