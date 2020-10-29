import java.util.Set;
import java.util.HashSet;

public class GalleryRegistration {
private String userName;

public void setUserName(String value) {
   this.userName = value;
}

public String getUserName() {
   return this.userName;
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

private String passWord;

public void setPassWord(String value) {
   this.passWord = value;
}

public String getPassWord() {
   return this.passWord;
}

private Boolean isLoggedIn;

public void setIsLoggedIn(Boolean value) {
   this.isLoggedIn = value;
}

public Boolean getIsLoggedIn() {
   return this.isLoggedIn;
}

/**
 * <pre>
 *           1..1     0..1
 * GalleryRegistration ------------------------- GalleryAdmin
 *           galleryRegistration        &gt;       admin
 * </pre>
 */
private GalleryAdmin admin;

public void setAdmin(GalleryAdmin value) {
   this.admin = value;
}

public GalleryAdmin getAdmin() {
   return this.admin;
}

/**
 * <pre>
 *           1..1     0..1
 * GalleryRegistration ------------------------- Artist
 *           galleryRegistration        &gt;       artist
 * </pre>
 */
private Artist artist;

public void setArtist(Artist value) {
   this.artist = value;
}

public Artist getArtist() {
   return this.artist;
}

/**
 * <pre>
 *           1..1     0..*
 * GalleryRegistration ------------------------- Customer
 *           galleryRegistration        &gt;       customer
 * </pre>
 */
private Set<Customer> customer;

public Set<Customer> getCustomer() {
   if (this.customer == null) {
this.customer = new HashSet<Customer>();
   }
   return this.customer;
}

}
