package ca.mcgill.ecse321.onlinegallery.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class GalleryRegistration{
   private Integer registrationId;

public void setRegistrationId(Integer value) {
    this.registrationId = value;
}
@Id
public Integer getRegistrationId() {
    return this.registrationId;
}
private String password;

public void setPassword(String value) {
    this.password = value;
}
public String getPassword() {
    return this.password;
}
private String userName;

public void setUserName(String value) {
    this.userName = value;
}
public String getUserName() {
    return this.userName;
}
private Boolean isLoggedIn;

public void setIsLoggedIn(Boolean value) {
    this.isLoggedIn = value;
}
public Boolean getIsLoggedIn() {
    return this.isLoggedIn;
}
   private OnlineGallery onlineGallery;
   
   @ManyToOne(optional=false)
   public OnlineGallery getOnlineGallery() {
      return this.onlineGallery;
   }
   
   public void setOnlineGallery(OnlineGallery onlineGallery) {
      this.onlineGallery = onlineGallery;
   }
   
   private GalleryUser user;
   
   @ManyToOne(optional=false)
   public GalleryUser getUser() {
      return this.user;
   }
   
   public void setUser(GalleryUser user) {
      this.user = user;
   }
   
   }
