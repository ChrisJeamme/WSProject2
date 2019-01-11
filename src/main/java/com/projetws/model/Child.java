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
@Table(name="child")
@NamedQuery(name="Child.findAll", query="SELECT r FROM Child r")
public class Child implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CHILD_ID")
	private long childId;

	@Column(name="CHILD_FIRSTNAME")
	private String childFirstName;
	
	@Column(name="CHILD_LASTNAME")
	private String childLastName;
	
	@ManyToOne
	@JoinColumn(name="SCHOOL_CLASS_ID")
	private SchoolClass schoolClass;

}