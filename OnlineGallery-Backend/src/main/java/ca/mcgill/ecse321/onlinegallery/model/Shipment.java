package ca.mcgill.ecse321.onlinegallery.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import java.util.HashSet;

@Entity
@Table(name = "shipment")
public class Shipment {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	@Column(name = "shipment_id")
	private long shipmentId;

	public void setShipmentId(long value) {
		this.shipmentId = value;
	}

	public long getShipmentId() {
		return this.shipmentId;
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
	
	private double shippingCost;
	public void setShippingCost(double cost) {
		this.shippingCost=cost;
	}
	
	public double getShippingCost() {
		return this.shippingCost;
	}

	private double totalAmount;

	public void setTotalAmount(double value) {
		this.totalAmount = value;
	}

	public double getTotalAmount() {
		return this.totalAmount;
	}

	private ShipmentStatus shipmentStatus;

	public void setShipmentStatus(ShipmentStatus value) {
		this.shipmentStatus = value;
	}

	public ShipmentStatus getShipmentStatus() {
		return this.shipmentStatus;
	}

	private String recipientName;

	public void setRecipientName(String value) {
		this.recipientName = value;
	}

	public String getRecipientName() {
		return this.recipientName;
	}

	private String creditCardNumber;
	public void setCreditCardNumber(String creditCardNumber) {
		this.creditCardNumber=creditCardNumber;
	}
		
	public String getCreditCardNumber() {
		return this.creditCardNumber;
	}
	
	private String creditCardFirstName;
	public void setCreditCardFirstName(String firstname) {
		this.creditCardFirstName=firstname;
	}
	public String getCreditCardFirstName() {
		return this.creditCardFirstName;
	}
	private String creditCardLastName;
	public void setCreditCardLastName(String lastname) {
		this.creditCardLastName=lastname;
	}
	
	public String getCreditCardLastName() {
		return this.creditCardLastName;
	}
	
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "shipment")
	private Set<Purchase> purchase;

	public Set<Purchase> getPurchase() {
		if (this.purchase == null) {
			this.purchase = new HashSet<Purchase>();
		}
		return this.purchase;
	}


}
