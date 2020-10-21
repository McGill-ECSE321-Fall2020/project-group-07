package ca.mcgill.ecse321.onlinegallery.model;

import java.sql.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="purchase")

public class Purchase {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	@Column(name="purchase_id")
	private Long purchaseId;

	public void setPurchaseId(Long value) {
		this.purchaseId = value;
	}

	public Long getPurchaseId() {
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

	private PaymentMethod paymentType;

	public void setPaymentType(PaymentMethod value) {
		this.paymentType = value;
	}

	public PaymentMethod getPaymentType() {
		return this.paymentType;
	}

	private boolean paid;

	public void setPaid(boolean value) {
		this.paid = value;
	}

	public boolean isPaid() {
		return this.paid;
	}

	private Date date;

	public void setDate(Date value) {
		this.date = value;
	}

	public Date getDate() {
		return this.date;
	}

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "purchase")
	private Artwork artwork;

	public void setArtwork(Artwork value) {
		this.artwork = value;
	}

	public Artwork getArtwork() {
		return this.artwork;
	}

//	/**
//	 * <pre>
//	 *           1..*     0..1
//	 * Purchase ------------------------- Shipment
//	 *           purchase        &gt;       shipment
//	 * </pre>
//	 */
//	private Shipment shipment;
//
//	public void setShipment(Shipment value) {
//		this.shipment = value;
//	}
//
//	public Shipment getShipment() {
//		return this.shipment;
//	}

}
