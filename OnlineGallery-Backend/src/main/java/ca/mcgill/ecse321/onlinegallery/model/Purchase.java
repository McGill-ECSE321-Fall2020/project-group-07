package ca.mcgill.ecse321.onlinegallery.model;

import java.sql.Date;
import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "purchase")

public class Purchase {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	@Column(name = "purchase_id")
	private Long purchaseId;

	public void setPurchaseId(Long value) {
		this.purchaseId = value;
	}

	public Long getPurchaseId() {
		return this.purchaseId;
	}


	private ShipmentType shipmentType=ShipmentType.OFFSITE_DELIVERY;

	public void setShipmentType(ShipmentType value) {
		this.shipmentType = value;
	}

	public ShipmentType getShipmentType() {
		return this.shipmentType;
	}
	
	


	private Date date = Date.valueOf(LocalDate.now());

	public void setDate(Date value) {
		this.date = value;
	}

	public Date getDate() {
		return this.date;
	}

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, optional = true)
	private Artwork artwork;

	public void setArtwork(Artwork value) {
		this.artwork = value;
	}

	public Artwork getArtwork() {
		return this.artwork;
	}
	

	@ManyToOne(fetch = FetchType.EAGER, optional = true)
	@JoinColumn(name = "shipment_id")
	private Shipment shipment;

	public void setShipment(Shipment value) {
		this.shipment = value;
	}

	public Shipment getShipment() {
		return this.shipment;
	}
	
	@ManyToOne(fetch = FetchType.EAGER, optional = true)
	@JoinColumn(name = "customer_id")
	private Customer customer;

	public Customer getCustomer() {
		return this.customer;
	}
	
	public void setCustomer(Customer customer) {
		this.customer=customer;
	}

}
