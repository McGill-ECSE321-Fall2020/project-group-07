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

import java.util.Set;
import javax.persistence.OneToMany;

@Entity
@Table(name = "profiles")
public class Profile {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	@Column(name = "profile_id")
	private long profileId;

	@Column(name = "total_earnings")
	private double totalEarnings;

	@Column(name = "num_sold")
	private int numSold;

	@Column(name = "self_description")
	private String selfDescription;

	@Column(name = "rating")
	private double rating;

	public Profile() {
	}

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "profile")
	private Artist artist;

	public Artist getArtist() {
		return this.artist;
	}

	public void setArtist(Artist artist) {
		this.artist = artist;
	}

	public void setProfileId(Long value) {
		this.profileId = value;
	}

	public Long getProfileId() {
		return this.profileId;
	}

	public void setTotalEarnings(double value) {
		this.totalEarnings = value;
	}

	public double getTotalEarnings() {
		return this.totalEarnings;
	}

	public void setNumSold(int value) {
		this.numSold = value;
	}

	public int getNumSold() {
		return this.numSold;
	}

	public void setSelfDescription(String value) {
		this.selfDescription = value;
	}

	public String getSelfDescription() {
		return this.selfDescription;
	}

	public void setRating(double value) {
		this.rating = value;
	}

	public double getRating() {
		return this.rating;
	}

}
