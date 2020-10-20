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
@Table(name="online_gallery")
public class OnlineGallery {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	@Column(name="id")
	private long systemId;

	public void setSystemId(long value) {
		this.systemId = value;
	}

	public long getSystemId() {
		return this.systemId;
	}
	
	@Column(name="days_up")
	private int daysUp;

	public void setDaysUp(int value) {
		this.daysUp = value;
	}

	public int getDaysUp() {
		return this.daysUp;
	}

	/**
	 * <pre>
	 *           1..1     0..1
	 * OnlineGallery ------------------------> PhysicalGallery
	 *           onlineGallery        &gt;       physicalGallery
	 * </pre>
	 */
	
	
	@OneToOne(optional = true)
	private PhysicalGallery physicalGallery;

	public void setPhysicalGallery(PhysicalGallery value) {
		this.physicalGallery = value;
	}

	public PhysicalGallery getPhysicalGallery() {
		return this.physicalGallery;
	}

	/**
	 * <pre>
	 *           1..1     0..*
	 * OnlineGallery ------------------------> GalleryRegistration
	 *           onlineGallery        &gt;       allRegistrations
	 * </pre>
	 */
	
	@OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
	@JoinColumn(name="online_gallery_id")
	private Set<GalleryRegistration> allRegistrations;

	public Set<GalleryRegistration> getAllRegistrations() {
		if (this.allRegistrations == null) {
			this.allRegistrations = new HashSet<GalleryRegistration>();
		}
		return this.allRegistrations;
	}

}
