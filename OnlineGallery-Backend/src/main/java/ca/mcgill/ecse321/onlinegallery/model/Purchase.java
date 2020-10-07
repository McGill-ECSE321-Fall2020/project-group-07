package ca.mcgill.ecse321.onlinegallery.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import Date;

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
private double commission;

public void setCommission(double value) {
    this.commission = value;
}
public double getCommission() {
    return this.commission;
}
private ShipmentType shipmentType;

public void setShipmentType(ShipmentType value) {
    this.shipmentType = value;
}
public ShipmentType getShipmentType() {
    return this.shipmentType;
}
private PaymentMethod paymentMethod;

public void setPaymentMethod(PaymentMethod value) {
    this.paymentMethod = value;
}
public PaymentMethod getPaymentMethod() {
    return this.paymentMethod;
}
private Customer customer;

@ManyToOne(optional=false)
public Customer getCustomer() {
   return this.customer;
}

public void setCustomer(Customer customer) {
   this.customer = customer;
}

private Artwork artworkOrdered;

@OneToOne(mappedBy="purchase" , optional=false)
public Artwork getArtworkOrdered() {
   return this.artworkOrdered;
}

public void setArtworkOrdered(Artwork artworkOrdered) {
   this.artworkOrdered = artworkOrdered;
}

private Date date;

public void setDate(Date value) {
    this.date = value;
}
public Date getDate() {
    return this.date;
}
   private Shipment shipment;
   
   @ManyToOne
   public Shipment getShipment() {
      return this.shipment;
   }
   
   public void setShipment(Shipment shipment) {
      this.shipment = shipment;
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
