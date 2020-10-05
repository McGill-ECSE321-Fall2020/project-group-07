import javax.persistence.Entity;
import javax.persistence.Id;
import ca.mcgill.ecse321.onlinegallery.model.OnlineGallery;
import javax.persistence.ManyToOne;
import java.util.Set;
import javax.persistence.OneToMany;

@Entity
public class User{
   private Integer userId;

public void setUserId(Integer value) {
    this.userId = value;
}
@Id
public Integer getUserId() {
    return this.userId;
}
private String email;

public void setEmail(String value) {
    this.email = value;
}
public String getEmail() {
    return this.email;
}
private String name;

public void setName(String value) {
    this.name = value;
}
public String getName() {
    return this.name;
}
private String phoneNumber;

public void setPhoneNumber(String value) {
    this.phoneNumber = value;
}
public String getPhoneNumber() {
    return this.phoneNumber;
}
   private OnlineGallery onlineGallery;
   
   @ManyToOne(optional=false)
   public OnlineGallery getOnlineGallery() {
      return this.onlineGallery;
   }
   
   public void setOnlineGallery(OnlineGallery onlineGallery) {
      this.onlineGallery = onlineGallery;
   }
   
   private Set<Registration> registrations;
   
   @OneToMany(mappedBy="user" )
   public Set<Registration> getRegistrations() {
      return this.registrations;
   }
   
   public void setRegistrations(Set<Registration> registrationss) {
      this.registrations = registrationss;
   }
   
   private Set<UserRole> userRoles;
   
   @OneToMany(mappedBy="user" )
   public Set<UserRole> getUserRoles() {
      return this.userRoles;
   }
   
   public void setUserRoles(Set<UserRole> userRoless) {
      this.userRoles = userRoless;
   }
   
   }
