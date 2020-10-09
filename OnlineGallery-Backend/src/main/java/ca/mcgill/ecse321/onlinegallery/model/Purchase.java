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

	@Column(name = "commission")
	private double commission;

	@Column(name = "shipment_type")
	@Enumerated
	private ShipmentType shipmentType;

	@Column(name = "payment_method")
	@Enumerated
	private PaymentMethod paymentMethod;

	@Column(name = "date")
	private Date date;

	@Column(name = "paid")
	private Boolean paid;

	public Purchase() {
	}

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "purchase")
	private Artwork artworkOrdered;

	public Artwork getArtworkOrdered() {
		return this.artworkOrdered;
	}

	public void setArtworkOrdered(Artwork artworkOrdered) {
		this.artworkOrdered = artworkOrdered;
	}

	@ManyToOne(fetch = FetchType.EAGER, optional = true)
	@JoinColumn(name = "customer_id")
	private Customer customer;

	public Customer getCustomer() {
		return this.customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	@ManyToOne(fetch = FetchType.EAGER, optional = true)
	@JoinColumn(name = "shipment_id")
	private Shipment shipment;

	public Shipment getShipment() {
		return this.shipment;
	}

	public void setShipment(Shipment shipment) {
		this.shipment = shipment;
	}

	@ManyToOne(fetch = FetchType.EAGER, optional = true)
	@JoinColumn(name = "system_id")
	private OnlineGallery onlineGallery;

	public OnlineGallery getOnlineGallery() {
		return this.onlineGallery;
	}

	public void setOnlineGallery(OnlineGallery onlineGallery) {
		this.onlineGallery = onlineGallery;
	}

	public void setPurchaseId(Long value) {
		this.purchaseId = value;
	}

	public Long getPurchaseId() {
		return this.purchaseId;
	}

	public void setCommission(double value) {
		this.commission = value;
	}

	public double getCommission() {
		return this.commission;
	}

	public void setPaymentMethod(PaymentMethod value) {
		this.paymentMethod = value;
	}

	public PaymentMethod getPaymentMethod() {
		return this.paymentMethod;
	}

	public void setDate(Date value) {
		this.date = value;
	}

	public Date getDate() {
		return this.date;
	}

	public void setShipmentType(ShipmentType value) {
		this.shipmentType = value;
	}

	public ShipmentType getShipmentType() {
		return this.shipmentType;
	}

	public void setPaid(boolean paid) {
		this.paid = paid;
	}

	public Boolean getPaid() {
		return this.paid;
	}

}