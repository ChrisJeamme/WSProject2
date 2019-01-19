package com.projetws.controller;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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

import com.projetws.model.ChildRepository;
import com.projetws.model.Photo;
import com.projetws.model.PhotoType;
import com.projetws.model.SchoolClassRepository;
import com.projetws.tools.PhotoStorageService;
import com.projetws.tools.UploadPhotoResponse;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
public class UploadController {

	private static final Logger logger = LogManager.getLogger(UploadController.class);

	@Autowired
	private PhotoStorageService photoStorageService;

	@Autowired
	private ChildRepository childRepository;

	@Autowired
	private SchoolClassRepository schoolClassRepository;
	 
	@ApiOperation(value="Store a Photo into the database" , httpMethod="POST", response= UploadPhotoResponse.class)
	@PostMapping("/upload")
	public UploadPhotoResponse uploadFile(@ApiParam(value="Image file", required=false) @RequestParam("file") MultipartFile file,
			@ApiParam(value="PhotoType (individual, class, unknown)", required=false) @RequestParam("type") PhotoType type,
			@ApiParam(value="Photo description text", required=false) @RequestParam("description") String description,
			@ApiParam(value="Capture date", required=false) @RequestParam("date") String date,
			@ApiParam(value="TEST, should be suppr. later", required=false)@RequestParam("childId") long childId,
			@ApiParam(value="TEST, should be suppr. later", required=false)@RequestParam("schoolClassId") long schoolClassId)				
	{
		System.out.println("test");
		logger.info("Try store photo");
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		Date formatedDate;
		try {
			formatedDate = format.parse (date);
		} catch (ParseException e) {
			formatedDate = null;
		} 
		
		logger.info("Try store photo");
		Photo photo = photoStorageService.storeFile(file,type,
				description,formatedDate,childRepository.findByChildId(childId),
				schoolClassRepository.findBySchoolClassId(schoolClassId));

		logger.info("Photo stored");
		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
				.path("/downloadFile/")
				.path("" + photo.getPhotoId())
				.toUriString();

		return new UploadPhotoResponse(file.getName(), fileDownloadUri,
				file.getContentType(), file.getSize());
	}

	@ApiOperation(value="Get a Photo from the database" , httpMethod="GET", response= Resource.class, responseContainer= "List")
	@GetMapping("/download/{fileId}")
	public ResponseEntity<Resource> downloadFile(@ApiParam(value="Photo id")@PathVariable long fileId) {
		// Load file from database
		Photo photo = photoStorageService.getFile(fileId);

		return ResponseEntity.ok()
				.contentType(MediaType.parseMediaType(photo.getExtension()))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + photo.getName() + "\"")
				.body(new ByteArrayResource(photo.getData()));
	}

}