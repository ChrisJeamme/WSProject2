package com.projetws.tools;

import java.util.Date;

import com.projetws.model.Child;
import com.projetws.model.PhotoType;

public class DisplayPhotoResponse {
	private String name;
    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDownloadUri() {
		return downloadUri;
	}

	public void setDownloadUri(String downloadUri) {
		this.downloadUri = downloadUri;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	private String downloadUri;
    private String description;
    private Long id;
    private Date date;
    private String type;
    
    public DisplayPhotoResponse(Long id,String name, String downloadUri, PhotoType photoType, String description, Date date) {
        this.name = name;
        this.downloadUri = downloadUri;
        this.description = description;
        this.date = date;
        
        switch (photoType) {
		case INDIVIDUAL_PHOTO:
			type = "individual";
			break;
		case CLASS_PHOTO:
			type = "class";
			break;
		default:
			type = "not_tagged";
			break;
		}
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
