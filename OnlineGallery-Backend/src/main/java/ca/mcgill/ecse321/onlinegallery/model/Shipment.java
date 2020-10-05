package ca.mcgill.ecse321.onlinegallery.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Set;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

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
private double totalAmount;

public void setTotalAmount(double value) {
    this.totalAmount = value;
}
public double getTotalAmount() {
    return this.totalAmount;
}
private double shippingCost;

public void setShippingCost(double value) {
    this.shippingCost = value;
}
public double getShippingCost() {
    return this.shippingCost;
}
private ShipmentType shipmentType;

public void setShipmentType(ShipmentType value) {
    this.shipmentType = value;
}
public ShipmentType getShipmentType() {
    return this.shipmentType;
}
private String shippingCompany;

public void setShippingCompany(String value) {
    this.shippingCompany = value;
}
public String getShippingCompany() {
    return this.shippingCompany;
}
private PaymentMethod paymentMethod;

public void setPaymentMethod(PaymentMethod value) {
    this.paymentMethod = value;
}
public PaymentMethod getPaymentMethod() {
    return this.paymentMethod;
}
private ShipmentStatus shipmentStatus;

public void setShipmentStatus(ShipmentStatus value) {
    this.shipmentStatus = value;
}
public ShipmentStatus getShipmentStatus() {
    return this.shipmentStatus;
}
   private OnlineGallery onlineGallery;
   
   @ManyToOne(optional=false)
   public OnlineGallery getOnlineGallery() {
      return this.onlineGallery;
   }
   
   public void setOnlineGallery(OnlineGallery onlineGallery) {
      this.onlineGallery = onlineGallery;
   }
   
   private Set<Purchase> purchases;
   
   @OneToMany(mappedBy="shipment" )
   public Set<Purchase> getPurchases() {
      return this.purchases;
   }
   
   public void setPurchases(Set<Purchase> purchasess) {
      this.purchases = purchasess;
   }
   
   private Address destinationAddress;
   
   @OneToOne(optional=false)
   public Address getDestinationAddress() {
      return this.destinationAddress;
   }
   
   public void setDestinationAddress(Address destinationAddress) {
      this.destinationAddress = destinationAddress;
   }
   
   private Address sourceAddress;
   
   @OneToOne(optional=false)
   public Address getSourceAddress() {
      return this.sourceAddress;
   }
   
   public void setSourceAddress(Address sourceAddress) {
      this.sourceAddress = sourceAddress;
   }
   
   }
