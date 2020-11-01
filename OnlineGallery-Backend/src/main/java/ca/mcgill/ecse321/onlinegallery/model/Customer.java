package ca.mcgill.ecse321.onlinegallery.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import java.util.HashSet;

@Entity
@Table(name = "customer")
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	@Column(name = "customer_id")
	private Long customerId;

	public void setCustomerId(Long value) {
		this.customerId = value;
	}

	public Long getCustomerId() {
		return this.customerId;
	}

	private String bankInfo;

	public void setBankInfo(String value) {
		this.bankInfo = value;
	}

	public String getBankInfo() {
		return this.bankInfo;
	}

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "customer")
	private GalleryRegistration galleryRegistration;

	public void setGalleryRegistration(GalleryRegistration value) {
		this.galleryRegistration = value;
	}

	public GalleryRegistration getGalleryRegistration() {
		return this.galleryRegistration;
	}


	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "customer")
	private Set<Purchase> purchase;

	public Set<Purchase> getPurchase() {
		if (this.purchase == null) {
			this.purchase = new HashSet<Purchase>();
		}
		return this.purchase;
	}

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "customer_id")
	private Set<Shipment> shipment;

	public Set<Shipment> getShipment() {
		if (this.shipment == null) {
			this.shipment = new HashSet<Shipment>();
		}
		return this.shipment;
	}

}
