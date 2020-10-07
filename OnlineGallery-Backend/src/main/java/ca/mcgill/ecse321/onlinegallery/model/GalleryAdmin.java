package ca.mcgill.ecse321.onlinegallery.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class GalleryAdmin{
   private Integer adminId;

public void setAdminId(Integer value) {
    this.adminId = value;
}
@Id
public Integer getAdminId() {
    return this.adminId;
}
   private GalleryRegistration galleryRegistration;
   
   @OneToOne(optional=false)
   public GalleryRegistration getGalleryRegistration() {
      return this.galleryRegistration;
   }
   
   public void setGalleryRegistration(GalleryRegistration galleryRegistration) {
      this.galleryRegistration = galleryRegistration;
   }
   
   }
