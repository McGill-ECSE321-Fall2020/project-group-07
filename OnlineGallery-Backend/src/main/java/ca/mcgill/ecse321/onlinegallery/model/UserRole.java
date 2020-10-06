package ca.mcgill.ecse321.onlinegallery.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Id;

@Entity
public abstract class UserRole{
   private GalleryUser user;
   
   @ManyToOne(optional=false)
   public GalleryUser getUser() {
      return this.user;
   }
   
   public void setUser(GalleryUser user) {
      this.user = user;
   }
   
   private Integer roleId;

public void setRoleId(Integer value) {
    this.roleId = value;
}
@Id
public Integer getRoleId() {
    return this.roleId;
}
}
