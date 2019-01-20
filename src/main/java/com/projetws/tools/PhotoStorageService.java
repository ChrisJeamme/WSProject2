package com.projetws.tools;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.projetws.model.Child;
import com.projetws.model.Photo;
import com.projetws.model.PhotoRepository;
import com.projetws.model.PhotoType;
import com.projetws.model.SchoolClass;

@Service
public class PhotoStorageService {

	@Autowired
	private PhotoRepository photoRepository;

	public Photo storeFile(MultipartFile file, PhotoType type, 
			String description, Date date, List<Child> childs, SchoolClass schoolClass) {
		System.out.println("### Saving Photo");
		// Normalize file name
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());

		try {
			// Invalid file's name
			if(fileName.contains("..")) {
				throw new PhotoStorageExceptionHandler("Filename contains invalid path sequence : " + fileName);
			}

			Photo photo = new Photo();
			photo.setName(fileName);
			photo.setChilds(childs);
			photo.setData(file.getBytes());
			photo.setDate(date);
			photo.setDescription(description);
			photo.setSchoolClass(schoolClass);
			photo.setType(type);
			photo.setExtension(file.getContentType());

			return photoRepository.save(photo);
		} catch (IOException ex) {
			throw new PhotoStorageExceptionHandler("Could not store file " + fileName + ". Please try again!", ex);
		}
	}

	public Photo getFile(long fileId) {
		return photoRepository.findByPhotoId(fileId);
		//	.orElseThrow(() -> new MyFileNotFoundException("File not found with id " + fileId));
	}
	
	public List<Photo> getChildPhoto(long childId)
	{
		return photoRepository.findDistinctByChilds_ChildId(childId);
	}
}
