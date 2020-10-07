package ca.mcgill.ecse321.onlinegallery.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.CascadeType;
import javax.persistence.OneToOne;
import java.util.Set;
import javax.persistence.OneToMany;

@Entity
public class OnlineGallery{
   private Integer systemId;

public void setSystemId(Integer value) {
    this.systemId = value;
}
@Id
public Integer getSystemId() {
    return this.systemId;
}
private int daysUp;

public void setDaysUp(int value) {
    this.daysUp = value;
}
public int getDaysUp() {
    return this.daysUp;
}
   private PhysicalGallery physicalGallery;
   
   @OneToOne(mappedBy="onlineGallery" , cascade={CascadeType.ALL})
   public PhysicalGallery getPhysicalGallery() {
      return this.physicalGallery;
   }
   
   public void setPhysicalGallery(PhysicalGallery physicalGallery) {
      this.physicalGallery = physicalGallery;
   }
   
   private Set<GalleryRegistration> allRegistrations;
   
   @OneToMany(mappedBy="onlineGallery" , cascade={CascadeType.ALL})
   public Set<GalleryRegistration> getAllRegistrations() {
      return this.allRegistrations;
   }
   
   public void setAllRegistrations(Set<GalleryRegistration> allRegistrationss) {
      this.allRegistrations = allRegistrationss;
   }
   
   private Set<Purchase> allPurchases;
   
   @OneToMany(mappedBy="onlineGallery" , cascade={CascadeType.ALL})
   public Set<Purchase> getAllPurchases() {
      return this.allPurchases;
   }
   
   public void setAllPurchases(Set<Purchase> allPurchasess) {
      this.allPurchases = allPurchasess;
   }
   
   private Set<Shipment> allShipments;
   
   @OneToMany(mappedBy="onlineGallery" , cascade={CascadeType.ALL})
   public Set<Shipment> getAllShipments() {
      return this.allShipments;
   }
   
   public void setAllShipments(Set<Shipment> allShipmentss) {
      this.allShipments = allShipmentss;
   }
   
   }
