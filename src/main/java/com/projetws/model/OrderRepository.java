package com.projetws.model;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "order", path = "order")
public interface OrderRepository extends PagingAndSortingRepository<Order, Long>
{
	public List<Order> findAll();
	public Order findByOrderId(long orderId);
	public List<Order> findByParent_UserId(long userId);
	//public List<Order> findByManager(User manager);
	
}