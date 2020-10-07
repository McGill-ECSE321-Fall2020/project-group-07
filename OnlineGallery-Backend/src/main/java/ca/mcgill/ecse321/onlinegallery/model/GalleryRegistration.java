package ca.mcgill.ecse321.onlinegallery.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.CascadeType;
import javax.persistence.OneToOne;
import java.util.Set;
import javax.persistence.OneToMany;

@Entity
public class GalleryRegistration{
   private String userName;

public void setUserName(String value) {
    this.userName = value;
}
@Id
public String getUserName() {
    return this.userName;
}
private OnlineGallery onlineGallery;

@ManyToOne(optional=false)
public OnlineGallery getOnlineGallery() {
   return this.onlineGallery;
}

public void setOnlineGallery(OnlineGallery onlineGallery) {
   this.onlineGallery = onlineGallery;
}

private String firstName;

public void setFirstName(String value) {
    this.firstName = value;
}
public String getFirstName() {
    return this.firstName;
}
private String lastName;

public void setLastName(String value) {
    this.lastName = value;
}
public String getLastName() {
    return this.lastName;
}
private String email;

public void setEmail(String value) {
    this.email = value;
}
public String getEmail() {
    return this.email;
}
private String phoneNumber;

public void setPhoneNumber(String value) {
    this.phoneNumber = value;
}
public String getPhoneNumber() {
    return this.phoneNumber;
}
private String password;

public void setPassword(String value) {
    this.password = value;
}
public String getPassword() {
    return this.password;
}
private Boolean isLoggedIn;

public void setIsLoggedIn(Boolean value) {
    this.isLoggedIn = value;
}
public Boolean getIsLoggedIn() {
    return this.isLoggedIn;
}
   private GalleryAdmin galleryAdmin;
   
   @OneToOne(mappedBy="galleryRegistration" , cascade={CascadeType.ALL}, optional=false)
   public GalleryAdmin getGalleryAdmin() {
      return this.galleryAdmin;
   }
   
   public void setGalleryAdmin(GalleryAdmin galleryAdmin) {
      this.galleryAdmin = galleryAdmin;
   }
   
   private Set<Artist> galleryArtists;
   
   @OneToMany(mappedBy="galleryRegistration" , cascade={CascadeType.ALL})
   public Set<Artist> getGalleryArtists() {
      return this.galleryArtists;
   }
   
   public void setGalleryArtists(Set<Artist> galleryArtistss) {
      this.galleryArtists = galleryArtistss;
   }
   
   private Set<Customer> galleryCustomers;
   
   @OneToMany(mappedBy="galleryRegistration" , cascade={CascadeType.ALL})
   public Set<Customer> getGalleryCustomers() {
      return this.galleryCustomers;
   }
   
   public void setGalleryCustomers(Set<Customer> galleryCustomerss) {
      this.galleryCustomers = galleryCustomerss;
   }
   
   }
