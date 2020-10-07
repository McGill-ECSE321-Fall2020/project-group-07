package ca.mcgill.ecse321.onlinegallery.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Id;
import java.util.Set;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
public class Customer{
   private GalleryRegistration galleryRegistration;
   
   @ManyToOne(optional=false)
   public GalleryRegistration getGalleryRegistration() {
      return this.galleryRegistration;
   }
   
   public void setGalleryRegistration(GalleryRegistration galleryRegistration) {
      this.galleryRegistration = galleryRegistration;
   }
   
   private Integer customerId;

public void setCustomerId(Integer value) {
    this.customerId = value;
}
@Id
public Integer getCustomerId() {
    return this.customerId;
}
private String bankInfo;

public void setBankInfo(String value) {
    this.bankInfo = value;
}
public String getBankInfo() {
    return this.bankInfo;
}
   private Set<Artwork> browseArtworks;
   
   @ManyToMany
   public Set<Artwork> getBrowseArtworks() {
      return this.browseArtworks;
   }
   
   public void setBrowseArtworks(Set<Artwork> browseArtworkss) {
      this.browseArtworks = browseArtworkss;
   }
   
   private Set<Purchase> purchases;
   
   @OneToMany(mappedBy="customer" )
   public Set<Purchase> getPurchases() {
      return this.purchases;
   }
   
   public void setPurchases(Set<Purchase> purchasess) {
      this.purchases = purchasess;
   }
   
   }
