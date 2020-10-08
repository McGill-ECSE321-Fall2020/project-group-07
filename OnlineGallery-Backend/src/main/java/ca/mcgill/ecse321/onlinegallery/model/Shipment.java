package ca.mcgill.ecse321.onlinegallery.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

import java.util.Set;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.ManyToOne;

@Entity
@Table(name="shipment")
public class Shipment {
	
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "shipment_id")
	private Long shipmentId;
    
    @Column(name="source")
	private String sourceAddress;
    
    @Column(name="destination")
	private String destinationAddress;
    
    @Column(name="total")
	private double totalAmount;
    
    @Column(name="shipper")
	private String shippingCompany;
	
    @Column(name="shipping_status")
	@Enumerated
	private ShipmentStatus status;
	
    @Column(name="recepient_name")
	private String receipientName;
    
    public Shipment() {}
    
    
    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "shipment"
            )
    private Set<Purchase> purchases;
    
	public Set<Purchase> getPurchases() {
		return this.purchases;
	}

	public void setPurchases(Set<Purchase> purchasess) {
		this.purchases = purchasess;
	}

    
    
    @ManyToOne(fetch=FetchType.LAZY,optional = false)
    @JoinColumn(name = "system_id", nullable = false)    
	private OnlineGallery onlineGallery;

	public OnlineGallery getOnlineGallery() {
		return this.onlineGallery;
	}

	public void setOnlineGallery(OnlineGallery onlineGallery) {
		this.onlineGallery = onlineGallery;
	}
    
    
       
      
    


	public void setShipmentId(Long value) {
		this.shipmentId = value;
	}

	public Long getShipmentId() {
		return this.shipmentId;
	}

	

	public void setSourceAddress(String value) {
		this.sourceAddress = value;
	}

	public String getSourceAddress() {
		return this.sourceAddress;
	}


	public void setDestinationAddress(String value) {
		this.destinationAddress = value;
	}

	public String getDestinationAddress() {
		return this.destinationAddress;
	}


	public void setTotalAmount(double value) {
		this.totalAmount = value;
	}

	public double getTotalAmount() {
		return this.totalAmount;
	}


	public void setShippingCompany(String value) {
		this.shippingCompany = value;
	}

	public String getShippingCompany() {
		return this.shippingCompany;
	}



	public void setStatus(ShipmentStatus value) {
		this.status = value;
	}

	public ShipmentStatus getStatus() {
		return this.status;
	}


	public void setReceipientName(String value) {
		this.receipientName = value;
	}

	public String getReceipientName() {
		return this.receipientName;
	}



}