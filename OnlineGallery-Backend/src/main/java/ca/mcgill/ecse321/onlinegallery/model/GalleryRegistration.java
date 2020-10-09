package ca.mcgill.ecse321.onlinegallery.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import java.util.Set;
import javax.persistence.OneToMany;

@Entity
@Table(name="registrations")
public class GalleryRegistration {
	
	@Id
	@Column(name="username")
	private String userName;
	
	@Column(name="firstname")
	private String firstName;
	
	@Column(name="lastname")
	private String lastName;
	
	@Column(name="email")
	private String email;
	
	@Column(name="number")
	private String phoneNumber;
	
	@Column(name="password")
	private String password;
	
	@Column(name="loggedin")
	private Boolean isLoggedIn;
	
	public GalleryRegistration() {}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String value) {
		this.userName = value;
	}

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL,optional=true)
	private GalleryAdmin galleryAdmin;

	public GalleryAdmin getGalleryAdmin() {
		return this.galleryAdmin;
	}

	public void setGalleryAdmin(GalleryAdmin galleryAdmin) {
		this.galleryAdmin = galleryAdmin;
	}

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL,optional=true)
	private Customer galleryCustomer;
	
	public Customer getGalleryCustomer() {
		return this.galleryCustomer;
	}

	public void setGalleryCustomer(Customer galleryCustomer) {
		this.galleryCustomer = galleryCustomer;
	}
	
	
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL,optional=true)
	private Artist galleryArtist;

	public Artist getGalleryArtist() {
		return this.galleryArtist;
	}

	public void setGalleryArtist(Artist galleryArtist) {
		this.galleryArtist = galleryArtist;
	}

    @ManyToOne(fetch=FetchType.EAGER,optional = false)
    @JoinColumn(name = "system_id", nullable = false)
	private OnlineGallery onlineGallery;
	public OnlineGallery getOnlineGallery() {
		return this.onlineGallery;
	}

	public void setOnlineGallery(OnlineGallery onlineGallery) {
		this.onlineGallery = onlineGallery;
	}

	
	
	
	
	
	
	
	
	
	public void setFirstName(String value) {
		this.firstName = value;
	}

	public String getFirstName() {
		return this.firstName;
	}


	public void setLastName(String value) {
		this.lastName = value;
	}

	public String getLastName() {
		return this.lastName;
	}


	public void setEmail(String value) {
		this.email = value;
	}

	public String getEmail() {
		return this.email;
	}


	public void setPhoneNumber(String value) {
		this.phoneNumber = value;
	}

	public String getPhoneNumber() {
		return this.phoneNumber;
	}


	public void setPassword(String value) {
		this.password = value;
	}

	public String getPassword() {
		return this.password;
	}


	public void setIsLoggedIn(Boolean value) {
		this.isLoggedIn = value;
	}

	public Boolean getIsLoggedIn() {
		return this.isLoggedIn;
	}


}