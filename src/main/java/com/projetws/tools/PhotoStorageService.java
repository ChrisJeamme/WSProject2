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
}
/*
 * import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class PhotoStorageService implements StorageService {


    private final Path rootLocation;

    @Autowired
    public PhotoStorageService(StorageProperties properties) {
        this.rootLocation = Paths.get(properties.getLocation());
    }

    @Override
    public void init() {
        try {
            Files.createDirectories(rootLocation);
        }
        catch (IOException e) {
            throw new StorageExceptionHandler("Could not initialize storage", e);
        }
    }

    @Override
    public void store(MultipartFile file) {
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            if (file.isEmpty()) {
                throw new PhotoStorageExceptionHandler("Failed to store empty file " + filename);
            }
            if (filename.contains("..")) {
                // This is a security check
                throw new PhotoStorageExceptionHandler(
                        "Cannot store file with relative path outside current directory "
                                + filename);
            }
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, this.rootLocation.resolve(filename),
                    StandardCopyOption.REPLACE_EXISTING);
            }
        }
        catch (IOException e) {
            throw new PhotoStorageExceptionHandler("Failed to store file " + filename, e);
        }
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.rootLocation, 1)
                .filter(path -> !path.equals(this.rootLocation))
                .map(this.rootLocation::relativize);
        }
        catch (IOException e) {
            throw new PhotoStorageExceptionHandler("Failed to read stored files", e);
        }

    }

    @Override
    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }

    @Override
    public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            }
            else {
                throw new PhotoStorageExceptionHandler(
                        "Could not read file: " + filename);

            }
        }
        catch (MalformedURLException e) {
            throw new PhotoStorageExceptionHandler("Could not read file: " + filename, e);
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }


}*/