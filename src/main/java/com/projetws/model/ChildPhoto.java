package com.projetws.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity(name = "child_on_photo")
@Table(name = "child_on_photo")
public class ChildPhoto {
	 
	    @EmbeddedId
	    private CKChildPhoto id;
	    
	    public ChildPhoto(long childId, long photoId) {
			id = new CKChildPhoto(childId, photoId);
		}

		public CKChildPhoto getId() {
	        return id;
	    }
	 
	    public void setId(CKChildPhoto id) {
	        this.id = id;
	    }
	 
	 
	}

