package ca.mcgill.ecse321.onlinegallery.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import Date;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class Purchase{
   private Integer purchaseId;

public void setPurchaseId(Integer value) {
    this.purchaseId = value;
}
@Id
public Integer getPurchaseId() {
    return this.purchaseId;
}
private Date date;

public void setDate(Date value) {
    this.date = value;
}
public Date getDate() {
    return this.date;
}
private String customerName;

public void setCustomerName(String value) {
    this.customerName = value;
}
public String getCustomerName() {
    return this.customerName;
}
private String artistName;

public void setArtistName(String value) {
    this.artistName = value;
}
public String getArtistName() {
    return this.artistName;
}
private String artworkName;

public void setArtworkName(String value) {
    this.artworkName = value;
}
public String getArtworkName() {
    return this.artworkName;
}
private double price;

public void setPrice(double value) {
    this.price = value;
}
public double getPrice() {
    return this.price;
}
private String dimension;

public void setDimension(String value) {
    this.dimension = value;
}
public String getDimension() {
    return this.dimension;
}
private double commission;

public void setCommission(double value) {
    this.commission = value;
}
public double getCommission() {
    return this.commission;
}
private double weight;

public void setWeight(double value) {
    this.weight = value;
}
public double getWeight() {
    return this.weight;
}
   private Customer customer;
   
   @ManyToOne(optional=false)
   public Customer getCustomer() {
      return this.customer;
   }
   
   public void setCustomer(Customer customer) {
      this.customer = customer;
   }
   
   private Artwork artwork;
   
   @OneToOne(optional=false)
   public Artwork getArtwork() {
      return this.artwork;
   }
   
   public void setArtwork(Artwork artwork) {
      this.artwork = artwork;
   }
   
   private OnlineGallery onlineGallery;
   
   @ManyToOne(optional=false)
   public OnlineGallery getOnlineGallery() {
      return this.onlineGallery;
   }
   
   public void setOnlineGallery(OnlineGallery onlineGallery) {
      this.onlineGallery = onlineGallery;
   }
   
   private Shipment shipment;
   
   @ManyToOne
   public Shipment getShipment() {
      return this.shipment;
   }
   
   public void setShipment(Shipment shipment) {
      this.shipment = shipment;
   }
   
   }
