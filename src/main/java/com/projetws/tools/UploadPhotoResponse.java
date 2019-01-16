package com.projetws.tools;

public class UploadPhotoResponse {

    private String name;
    private String downloadUri;
    private String extension;
    private long size;

    public UploadPhotoResponse(String name, String downloadUri, String extension, long size) {
        this.name = name;
        this.downloadUri = downloadUri;
        this.extension = extension;
        this.size = size;
    }

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

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }
}