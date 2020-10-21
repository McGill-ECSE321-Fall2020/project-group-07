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
@Table(name="admin")
public class GalleryAdmin {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	@Column(name="id")
	private Long adminId;
	
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "admin")
	private GalleryRegistration galleryRegistration;

	public void setGalleryRegistration(GalleryRegistration value) {
		this.galleryRegistration = value;
	}

	public GalleryRegistration getGalleryRegistration() {
		return this.galleryRegistration;
	}

	public void setAdminId(Long value) {
		this.adminId = value;
	}

	public Long getAdminId() {
		return this.adminId;
	}



}
