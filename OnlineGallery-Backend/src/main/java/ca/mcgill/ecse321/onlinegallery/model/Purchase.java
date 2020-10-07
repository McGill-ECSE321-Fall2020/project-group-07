package ca.mcgill.ecse321.onlinegallery.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import java.sql.Date;

@Entity
@Table(name = "purchase")
public class Purchase {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "artwork_id")
	private Long purchaseId;
	
	@Column(name="commission")
	private double commission;
	
	@Column(name="shipment_type")
	@Enumerated
	private ShipmentType shipmentType;
	
	@Column(name="payment_method")
	@Enumerated
	private PaymentMethod paymentMethod;
	
	public Purchase() {}
	
	
	
    @OneToOne(fetch = FetchType.LAZY, 
    		cascade=CascadeType.ALL, 
            mappedBy = "purchase")
    private Artwork artworkOrdered;
    public Artwork getArtworkOrdered() {
       return this.artworkOrdered;
    }
    
    public void setArtworkOrdered(Artwork artworkOrdered) {
       this.artworkOrdered = artworkOrdered;
    }
	
	
	

	public void setPurchaseId(Long value) {
		this.purchaseId = value;
	}

	@Id
	public Long getPurchaseId() {
		return this.purchaseId;
	}


	public void setCommission(Long value) {
		this.commission = value;
	}

	public double getCommission() {
		return this.commission;
	}


//
//public void setShipmentType(ShipmentType value) {
//    this.shipmentType = value;
//}
//public ShipmentType getShipmentType() {
//    return this.shipmentType;
//}
//

//
//public void setPaymentMethod(PaymentMethod value) {
//    this.paymentMethod = value;
//}
//public PaymentMethod getPaymentMethod() {
//    return this.paymentMethod;
//}
//private Customer customer;
//
//@ManyToOne(optional=false)
//public Customer getCustomer() {
//   return this.customer;
//}
//
//public void setCustomer(Customer customer) {
//   this.customer = customer;
//}
//

//
//private Date date;
//
//public void setDate(Date value) {
//    this.date = value;
//}
//public Date getDate() {
//    return this.date;
//}
//   private Shipment shipment;
//   
//   @ManyToOne
//   public Shipment getShipment() {
//      return this.shipment;
//   }
//   
//   public void setShipment(Shipment shipment) {
//      this.shipment = shipment;
//   }
//   
//   private OnlineGallery onlineGallery;
//   
//   @ManyToOne(optional=false)
//   public OnlineGallery getOnlineGallery() {
//      return this.onlineGallery;
//   }
//   
//   public void setOnlineGallery(OnlineGallery onlineGallery) {
//      this.onlineGallery = onlineGallery;
//   }

}