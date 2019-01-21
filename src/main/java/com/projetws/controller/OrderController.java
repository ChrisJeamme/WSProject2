package com.projetws.controller;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.projetws.model.Child;
import com.projetws.model.ChildRepository;
import com.projetws.model.Order;
import com.projetws.model.OrderRepository;
import com.projetws.model.Photo;
import com.projetws.model.PhotoRepository;
import com.projetws.model.PhotoType;
import com.projetws.model.SchoolClassRepository;
import com.projetws.model.User;
import com.projetws.model.UserRepository;
import com.projetws.tools.DisplayPhotoResponse;
import com.projetws.tools.OrderResponse;
import com.projetws.tools.PhotoStorageService;
import com.projetws.tools.UploadPhotoResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(value="Handle requests related to photo ordering")
@RestController
public class OrderController {

	private static final Logger logger = LogManager.getLogger(OrderController.class);

	@Autowired
	private PhotoStorageService photoStorageService;

	@Autowired
	private ChildRepository childRepository;
	
	@Autowired
	private UserRepository parentRepository;
	
	@Autowired
	private OrderRepository orderRepository;
	 
	@ApiOperation(value="Provide photos related to current user" , httpMethod="GET", response= UploadPhotoResponse.class)
	@GetMapping("/display")
	public List<DisplayPhotoResponse> displayPhotos(Principal principal)				
	{
		logger.info("principal = " + principal);
		String username = principal.getName();
		if(!parentRepository.existsByUserName(username))
		{
			logger.error("User doesn't exist");
			return null;
		}
		logger.info("Getting parent");
		User parent = parentRepository.findByUserName(username);
		
		logger.info("Getting children");
		List<Child> children = childRepository.findByParent_UserId(parent.getUserId());		
		ArrayList<Photo> photoList = new ArrayList<>();
		
		logger.info("Getting photos");
		for(Child c : children)
		{
			photoList.addAll(photoStorageService.getChildPhoto(c.getChildId()));	
		}
		
		ArrayList<DisplayPhotoResponse> photoResponseList = new ArrayList<DisplayPhotoResponse>();
		logger.info("Building response");
		for(Photo p : photoList)
		{
			String downloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
					.path("/download/")
					.path("" + p.getPhotoId())
					.toUriString();
			photoResponseList.add(new DisplayPhotoResponse(p.getPhotoId(),p.getName(), downloadUri, p.getType(), p.getDescription(), p.getDate()));
		}
		
		return photoResponseList;
	}
	
	@ApiOperation(value="Register a photo order" , httpMethod="POST", response= UploadPhotoResponse.class)
	@PostMapping("/executeOrder")
	public OrderResponse executeOrder(Principal principal,@ApiParam(value="Ordered Photos", required=true) @RequestParam("photosId") String photosId)				
	{
		logger.info("principal = " + principal);
		String username = principal.getName();
		if(!parentRepository.existsByUserName(username))
		{
			logger.error("User doesn't exist");
			return null;
		}
		logger.info("Getting parent");
		User parent = parentRepository.findByUserName(username);
		
		
		String[] photos = photosId.split(",");
		ArrayList<Photo> photosList= new ArrayList<>();
		ArrayList<Long> photoIdsList= new ArrayList<>();
		
		if(photos.length > 0)
		{
			for(String photoId: photos)
			{
				photosList.add(photoStorageService.getFile(Long.parseLong(photoId)));
				photoIdsList.add(Long.parseLong(photoId));
			}
		}
		else
		{
			//send response order is empty
			return null;
		}
		
		Order order = new Order(parent, photosList);
		orderRepository.save(order);
		OrderResponse or = new OrderResponse(order.getOrderId(), parent.getUserId(), photoIdsList);
		
		return or;
	}
	
	@ApiOperation(value="Display Order with orderId matching param Id" , httpMethod="GET", response= UploadPhotoResponse.class)
	@GetMapping("/displayOrder")
	public Order getitem(@RequestParam("id") String orderId){
		Order o = orderRepository.findByOrderId(Long.parseLong(orderId));
	    
	    return o;
	}
}