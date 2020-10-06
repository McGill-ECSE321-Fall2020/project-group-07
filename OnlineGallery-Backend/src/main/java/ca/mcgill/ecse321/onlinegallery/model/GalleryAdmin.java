package ca.mcgill.ecse321.onlinegallery.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class GalleryAdmin extends UserRole{
   private Integer adminId;

public void setAdminId(Integer value) {
    this.adminId = value;
}
@Id
public Integer getAdminId() {
    return this.adminId;
}
}
