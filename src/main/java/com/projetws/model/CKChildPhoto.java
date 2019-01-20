package com.projetws.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class CKChildPhoto implements Serializable {
 
    /**
	 * 
	 */
	private static final long serialVersionUID = -8184967513534841217L;

	@Column(name = "CHILD_ID")
    private Long childId;
 
    @Column(name = "PHOTO_ID")
    private Long photoId;
 
    public CKChildPhoto() {
		
	}
 
    public CKChildPhoto(Long childId, Long photoId) {
        this.childId = childId;
        this.photoId = photoId;
    }
 
    public Long getChildId() {
        return childId;
    }
 
    public Long getPhotoId() {
        return photoId;
    }
 
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CKChildPhoto)) return false;
        CKChildPhoto that = (CKChildPhoto) o;
        return Objects.equals(getChildId(), that.getChildId()) &&
                Objects.equals(getPhotoId(), that.getPhotoId());
    }
 
    @Override
    public int hashCode() {
        return Objects.hash(getChildId(), getPhotoId());
    }
}