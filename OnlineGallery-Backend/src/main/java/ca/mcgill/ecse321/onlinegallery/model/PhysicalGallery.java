package ca.mcgill.ecse321.onlinegallery.model;

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
@Table(name="physical_gallery")
public class PhysicalGallery {
	
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "physical_id")
	private Long galleryId;
	private String address;
	
	public PhysicalGallery() {}
	
    @OneToOne(fetch = FetchType.LAZY, 
    		cascade=CascadeType.ALL, 
            mappedBy = "physicalGallery")
	private OnlineGallery onlineGallery;

	public OnlineGallery getOnlineGallery() {
		return this.onlineGallery;
	}

	public void setOnlineGallery(OnlineGallery onlineGallery) {
		this.onlineGallery = onlineGallery;
	}

	
	public void setGalleryId(Long value) {
		this.galleryId = value;
	}

	public Long getGalleryId() {
		return this.galleryId;
	}


	public void setAddress(String value) {
		this.address = value;
	}

	public String getAddress() {
		return this.address;
	}



}