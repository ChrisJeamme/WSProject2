package com.projetws.tools;

import com.projetws.model.Child;

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

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	private String downloadUri;
    private String extension;
    private String description;

    public DisplayPhotoResponse(String name, String downloadUri, String extension, String description) {
        this.name = name;
        this.downloadUri = downloadUri;
        this.extension = extension;
        this.description = description;
    }
}
