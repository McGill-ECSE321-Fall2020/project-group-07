package ca.mcgill.ecse321.onlinegallery.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Set;
import javax.persistence.OneToMany;
import javax.persistence.ManyToOne;

@Entity
public class Shipment{
   private Integer shipmentId;

public void setShipmentId(Integer value) {
    this.shipmentId = value;
}
@Id
public Integer getShipmentId() {
    return this.shipmentId;
}
private Set<Purchase> purchases;

@OneToMany(mappedBy="shipment" )
public Set<Purchase> getPurchases() {
   return this.purchases;
}

public void setPurchases(Set<Purchase> purchasess) {
   this.purchases = purchasess;
}

private String sourceAddress;

public void setSourceAddress(String value) {
    this.sourceAddress = value;
}
public String getSourceAddress() {
    return this.sourceAddress;
}
private String destinationAddress;

public void setDestinationAddress(String value) {
    this.destinationAddress = value;
}
public String getDestinationAddress() {
    return this.destinationAddress;
}
private double totalAmount;

public void setTotalAmount(double value) {
    this.totalAmount = value;
}
public double getTotalAmount() {
    return this.totalAmount;
}
private String shippingCompany;

public void setShippingCompany(String value) {
    this.shippingCompany = value;
}
public String getShippingCompany() {
    return this.shippingCompany;
}
private ShipmentStatus status;

public void setStatus(ShipmentStatus value) {
    this.status = value;
}
public ShipmentStatus getStatus() {
    return this.status;
}
private String receipientName;

public void setReceipientName(String value) {
    this.receipientName = value;
}
public String getReceipientName() {
    return this.receipientName;
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
