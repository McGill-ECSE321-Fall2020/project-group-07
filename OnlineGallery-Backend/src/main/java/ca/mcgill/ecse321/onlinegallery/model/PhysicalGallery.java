package ca.mcgill.ecse321.onlinegallery.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="physical_gallery")
public class PhysicalGallery {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	@Column(name="id")
	private long galleryId;

	public void setGalleryId(long value) {
		this.galleryId = value;
	}

	public long getGalleryId() {
		return this.galleryId;
	}

	private String address;

	public void setAddress(String value) {
		this.address = value;
	}

	public String getAddress() {
		return this.address;
	}

}
