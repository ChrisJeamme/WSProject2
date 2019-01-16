package com.projetws.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.projetws.model.Child;
import com.projetws.model.ChildRepository;
import com.projetws.model.Photo;
import com.projetws.model.PhotoType;
import com.projetws.model.SchoolClass;
import com.projetws.model.SchoolClassRepository;
import com.projetws.tools.PhotoStorageService;
import com.projetws.tools.UploadPhotoResponse;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/transfer")
@RestController
public class UploadController {

    @Autowired
    private PhotoStorageService photoStorageService;

    @Autowired
    private ChildRepository childRepository;
    
    @Autowired
    private SchoolClassRepository schoolClassRepository;
    
    @GetMapping("/")
    public String uploadPage()
    {
    	return "upload";
    }
    
    @PostMapping("/upload")
    public UploadPhotoResponse uploadFile(@RequestParam("file") MultipartFile file,
    		@RequestParam("type") PhotoType type,
    		@RequestParam("description") String description,
    		@RequestParam("date") String date,
    		@RequestParam("childId") long childId,
    		@RequestParam("schoolClassId") long schoolClassId)				
    {
    	SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
    	Date formatDate;
		try {
			formatDate = format.parse (date);
		} catch (ParseException e) {
			formatDate = null;
		} 
    	
    
        Photo photo = photoStorageService.storeFile(file,type,
        		description,formatDate,childRepository.findByChildId(childId),
        		schoolClassRepository.findBySchoolClassId(schoolClassId));

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path("" + photo.getPhotoId())
                .toUriString();

        return new UploadPhotoResponse(file.getName(), fileDownloadUri,
                file.getContentType(), file.getSize());
    }


    @GetMapping("/download/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable long fileId) {
        // Load file from database
        Photo photo = photoStorageService.getFile(fileId);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(photo.getExtension()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + photo.getName() + "\"")
                .body(new ByteArrayResource(photo.getData()));
    }

}