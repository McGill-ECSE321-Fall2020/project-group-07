package ca.mcgill.ecse321.onlinegallery.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class PhysicalGallery{
   private Integer galleryId;

public void setGalleryId(Integer value) {
    this.galleryId = value;
}
@Id
public Integer getGalleryId() {
    return this.galleryId;
}
private String address;

public void setAddress(String value) {
    this.address = value;
}
public String getAddress() {
    return this.address;
}
   private OnlineGallery onlineGallery;
   
   @OneToOne(optional=false)
   public OnlineGallery getOnlineGallery() {
      return this.onlineGallery;
   }
   
   public void setOnlineGallery(OnlineGallery onlineGallery) {
      this.onlineGallery = onlineGallery;
   }
   
   }
