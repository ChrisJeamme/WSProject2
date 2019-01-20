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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.projetws.model.Child;
import com.projetws.model.ChildRepository;
import com.projetws.model.Photo;
import com.projetws.model.PhotoRepository;
import com.projetws.model.PhotoType;
import com.projetws.model.SchoolClassRepository;
import com.projetws.model.User;
import com.projetws.model.UserRepository;
import com.projetws.tools.DisplayPhotoResponse;
import com.projetws.tools.PhotoStorageService;
import com.projetws.tools.UploadPhotoResponse;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@Scope("session")
public class OrderController {

	private static final Logger logger = LogManager.getLogger(OrderController.class);

	@Autowired
	private PhotoStorageService photoStorageService;

	@Autowired
	private ChildRepository childRepository;
	
	@Autowired
	private UserRepository parentRepository;
	
	@Autowired
	private SchoolClassRepository schoolClassRepository;
	 
	@ApiOperation(value="Store a Photo into the database" , httpMethod="POST", response= UploadPhotoResponse.class)
	@GetMapping("/display")
	public List<DisplayPhotoResponse> uploadFile(Principal principal)				
	{
		String username = principal.getName();
		if(!parentRepository.existsByUserName(username))
		{
			logger.error("User doesn't exist");
			return null;
		}
		
		User parent = parentRepository.findByUserName(username);
		List<Child> children = childRepository.findByParent(parent.getUserId());		
		ArrayList<Photo> photoList = new ArrayList<>();
		
		for(Child c : children)
		{
			photoList.addAll(photoStorageService.getChildPhoto(c.getChildId()));	
		}
		
		ArrayList<DisplayPhotoResponse> photoResponseList = new ArrayList<DisplayPhotoResponse>();
		
		for(Photo p : photoList)
		{
			String downloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
					.path("/download/")
					.path("" + p.getPhotoId())
					.toUriString();
			photoResponseList.add(new DisplayPhotoResponse(p.getName(), downloadUri, p.getExtension(), p.getDescription()));
		}
		
		return photoResponseList;
	}
}