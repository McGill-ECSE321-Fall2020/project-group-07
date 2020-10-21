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

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "shipment")
	private Set<Purchase> purchase;

	public Set<Purchase> getPurchase() {
		if (this.purchase == null) {
			this.purchase = new HashSet<Purchase>();
		}
		return this.purchase;
	}

}
