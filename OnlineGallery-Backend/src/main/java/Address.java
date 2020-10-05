import javax.persistence.Entity;
import javax.persistence.Id;
import ca.mcgill.ecse321.onlinegallery.model.OnlineGallery;
import javax.persistence.ManyToOne;

@Entity
public class Address{
   private Integer addressId;

public void setAddressId(Integer value) {
    this.addressId = value;
}
@Id
public Integer getAddressId() {
    return this.addressId;
}
private String street;

public void setStreet(String value) {
    this.street = value;
}
public String getStreet() {
    return this.street;
}
private int streetNumber;

public void setStreetNumber(int value) {
    this.streetNumber = value;
}
public int getStreetNumber() {
    return this.streetNumber;
}
private String city;

public void setCity(String value) {
    this.city = value;
}
public String getCity() {
    return this.city;
}
private String provinceState;

public void setProvinceState(String value) {
    this.provinceState = value;
}
public String getProvinceState() {
    return this.provinceState;
}
private String postalCode;

public void setPostalCode(String value) {
    this.postalCode = value;
}
public String getPostalCode() {
    return this.postalCode;
}
private String country;

public void setCountry(String value) {
    this.country = value;
}
public String getCountry() {
    return this.country;
}
   private OnlineGallery onlineGallery;
   
   @ManyToOne(optional=false)
   public OnlineGallery getOnlineGallery() {
      return this.onlineGallery;
   }
   
   public void setOnlineGallery(OnlineGallery onlineGallery) {
      this.onlineGallery = onlineGallery;
   }
   
   }
