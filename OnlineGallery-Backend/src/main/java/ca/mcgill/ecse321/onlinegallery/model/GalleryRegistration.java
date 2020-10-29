package ca.mcgill.ecse321.onlinegallery.model;

import java.util.Set;

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

import java.util.HashSet;

@Entity
@Table(name = "registrations")
public class GalleryRegistration {

	@Id
	@Column(name = "username")
	private String userName;

	public void setUserName(String value) {
		this.userName = value;
	}

	public String getUserName() {
		return this.userName;
	}

	private String firstName;

	public void setFirstName(String value) {
		this.firstName = value;
	}

	public String getFirstName() {
		return this.firstName;
	}

	private String lastName;

	public void setLastName(String value) {
		this.lastName = value;
	}

	public String getLastName() {
		return this.lastName;
	}

	private String email;

	public void setEmail(String value) {
		this.email = value;
	}

	public String getEmail() {
		return this.email;
	}
	
	private String passWord="changeThisDefaultPassword";

	public void setPassWord(String value) {
		this.passWord = value;
	}

	public String getPassWord() {
		return this.passWord;
	}

	private Boolean isLoggedIn=false;

	public void setIsLoggedIn(Boolean value) {
		this.isLoggedIn = value;
	}

	public Boolean getIsLoggedIn() {
		return this.isLoggedIn;
	}

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, optional = true)
	private GalleryAdmin admin;

	public void setAdmin(GalleryAdmin customer) {
		this.admin = customer;
	}

	public GalleryAdmin getAdmin() {
		return this.admin;
	}

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST, optional = true)
	private Customer customer;

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Customer getCustomer() {
		return this.customer;
	}

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, optional = true)
	private Artist artist;

	public void setArtist(Artist value) {
		this.artist = value;
	}

	public Artist getArtist() {
		return this.artist;
	}

	@Override
	public String toString() {
		String info = this.getUserName() + "\n" + this.getFirstName() + "\n" + this.getLastName() + "\n"
				+ this.getEmail() + "\n" + this.getIsLoggedIn();
		return info;
	}

}
