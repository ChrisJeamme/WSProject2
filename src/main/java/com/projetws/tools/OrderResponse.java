package com.projetws.tools;

import java.util.List;

import com.projetws.model.Photo;

public class OrderResponse {
	private Long orderId;
	private Long parentId;	
	private List<Long> photoIds;
	
	public OrderResponse(Long orderId, Long parentId, List<Long> photoIds) {
		this.orderId = orderId;
		this.parentId = parentId;
		this.photoIds = photoIds;
		
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
	public List<Long> getPhotoIds() {
		return photoIds;
	}
	public void setPhotos(List<Long> photoIds) {
		this.photoIds = photoIds;
	}

}
