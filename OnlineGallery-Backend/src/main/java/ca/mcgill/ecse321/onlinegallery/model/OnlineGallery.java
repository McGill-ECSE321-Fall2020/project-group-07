package ca.mcgill.ecse321.onlinegallery.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import ca.mcgill.ecse321.onlinegallery.model.GalleryRegistration;

import java.util.Set;
import javax.persistence.OneToMany;

@Entity
@Table(name="online_gallery")
public class OnlineGallery {
	
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "system_id")
	private Long systemId;
    
    @Column(name="days_up")
	private int daysUp;

    public OnlineGallery() {}
    
	
    
    
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL,optional=true)
    private PhysicalGallery physicalGallery;
	
	public PhysicalGallery getPhysicalGallery() {
		return this.physicalGallery;
	}

	public void setPhysicalGallery(PhysicalGallery physicalGallery) {
		this.physicalGallery = physicalGallery;
	}
    

	@OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "onlineGallery")    
	private Set<GalleryRegistration> allRegistrations;
	
	public Set<GalleryRegistration> getAllRegistrations() {
		return this.allRegistrations;
	}
	
	public void setAllRegistrations(Set<GalleryRegistration> allRegistrationss) {
		this.allRegistrations = allRegistrationss;
	}
	
	
	@OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "onlineGallery")  
	private Set<Purchase> allPurchases;

	public Set<Purchase> getAllPurchases() {
		return this.allPurchases;
	}

	public void setAllPurchases(Set<Purchase> allPurchasess) {
		this.allPurchases = allPurchasess;
	}
	
	
	@OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "onlineGallery")  
	private Set<Shipment> allShipments;

	public Set<Shipment> getAllShipments() {
		return this.allShipments;
	}

	public void setAllShipments(Set<Shipment> allShipmentss) {
		this.allShipments = allShipmentss;
	}
    
	
	
	
	
	
	public void setSystemId(Long value) {
		this.systemId = value;
	}

	public Long getSystemId() {
		return this.systemId;
	}


	public void setDaysUp(int value) {
		this.daysUp = value;
	}

	public int getDaysUp() {
		return this.daysUp;
	}



	
	
	
	
	
	





}