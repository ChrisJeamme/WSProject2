package com.projetws.tools;

import java.util.List;

import com.projetws.model.Photo;

public class OrderResponse {
	private Long orderId;
	private Long parentId;	
	private List<Photo> photos;
	
	public OrderResponse(Long orderId, Long parentId, List<Photo> photos) {
		this.orderId = orderId;
		this.parentId = parentId;
		this.photos = photos;
		
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public List<Photo> getPhotos() {
		return photos;
	}
	public void setPhotos(List<Photo> photos) {
		this.photos = photos;
	}

}
