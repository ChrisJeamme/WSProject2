package com.projetws.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table(name="photo_order")
@NamedQuery(name="Order.findAll", query="SELECT r FROM Order r")
public class Order implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ORDER_ID")
	@GeneratedValue
	private long orderId;

	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	public User getParent() {
		return parent;
	}

	public void setParent(User parent) {
		this.parent = parent;
	}

	public List<Photo> getPhotos() {
		return photos;
	}

	public void setPhotos(List<Photo> photos) {
		this.photos = photos;
	}

	@ManyToOne
	@JoinColumn(name="USER_ID")
	private User parent;
	
	/*
	@ManyToOne
	@JoinColumn(name="USER_ID")
	private User manager;
	
	public User getManager() {
		return manager;
	}

	public void setManager(User manager) {
		this.manager = manager;
	}
*/
	@ManyToMany
	@JoinColumn(name="PHOTO_ID")
	private List<Photo> photos;

	public Order(){}

	public Order(User parent, List<Photo> photos)
	{
		this.parent = parent;
		this.photos = photos;
	}

}