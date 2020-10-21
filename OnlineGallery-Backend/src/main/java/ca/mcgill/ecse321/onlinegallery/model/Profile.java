package ca.mcgill.ecse321.onlinegallery.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="profile")
public class Profile {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	@Column(name="id")
	private Long profileId;

	public void setProfileId(Long value) {
		this.profileId = value;
	}

	public Long getProfileId() {
		return this.profileId;
	}

	private int numSold;

	public void setNumSold(int value) {
		this.numSold = value;
	}

	public int getNumSold() {
		return this.numSold;
	}

	private double totalEarnings;

	public void setTotalEarnings(double value) {
		this.totalEarnings = value;
	}

	public double getTotalEarnings() {
		return this.totalEarnings;
	}

	private String selfDescription;

	public void setSelfDescription(String value) {
		this.selfDescription = value;
	}

	public String getSelfDescription() {
		return this.selfDescription;
	}

	private double rating;

	public void setRating(double value) {
		this.rating = value;
	}

	public double getRating() {
		return this.rating;
	}

}
