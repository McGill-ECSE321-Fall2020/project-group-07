package ca.mcgill.ecse321.onlinegallery.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import java.util.HashSet;

@Entity
@Table(name = "artwork")
public class Artwork {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	@Column(name = "art_id")
	private Long artworkId;

	public void setArtworkId(Long value) {
		this.artworkId = value;
	}

	public Long getArtworkId() {
		return this.artworkId;
	}

	private String name;

	public void setName(String value) {
		this.name = value;
	}

	public String getName() {
		return this.name;
	}

	private String description;

	public void setDescription(String value) {
		this.description = value;
	}

	public String getDescription() {
		return this.description;
	}

	private double price=0;

	public void setPrice(double value) {
		this.price = value;
	}

	public double getPrice() {
		return this.price;
	}

	private ArtworkStatus status=ArtworkStatus.AVAILABLE;

	public void setStatus(ArtworkStatus value) {
		this.status = value;
	}

	public ArtworkStatus getStatus() {
		return this.status;
	}


	private int numViews=0;

	public void setNumViews(int value) {
		this.numViews = value;
	}

	public int getNumViews() {
		return this.numViews;
	}

	private String dimension;

	public void setDimension(String value) {
		this.dimension = value;
	}

	public String getDimension() {
		return this.dimension;
	}

	private double weight;

	public void setWeight(double value) {
		this.weight = value;
	}

	public double getWeight() {
		return this.weight;
	}

	private double commission;
	
	public void setCommission(double commission) {
		this.commission=commission;
	}
	
	public double getComission() {
		return this.commission;
	}
	

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "artwork")
	private Purchase purchase;

	public void setPurchase(Purchase value) {
		this.purchase = value;
	}

	public Purchase getPurchase() {
		return this.purchase;
	}
	
	@ManyToOne(fetch = FetchType.EAGER, optional = true)
	@JoinColumn(name = "artist_id")
	private Artist artist;

	public Artist getArtist() {
		return this.artist;
	}

	public void setArtist(Artist artist) {
		this.artist = artist;
	}

}
