import javax.persistence.Entity;
import javax.persistence.Id;
import ca.mcgill.ecse321.onlinegallery.model.OnlineGallery;
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
   private OnlineGallery onlineGallery;
   
   @OneToOne(optional=false)
   public OnlineGallery getOnlineGallery() {
      return this.onlineGallery;
   }
   
   public void setOnlineGallery(OnlineGallery onlineGallery) {
      this.onlineGallery = onlineGallery;
   }
   
   private Address address;
   
   @OneToOne(optional=false)
   public Address getAddress() {
      return this.address;
   }
   
   public void setAddress(Address address) {
      this.address = address;
   }
   
   }
