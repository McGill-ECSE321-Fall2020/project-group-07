package ca.mcgill.ecse321.onlinegallery.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import java.util.Set;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="artworks")
public class Artwork {
	
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "artwork_id")
	private Long artworkId;
    
    @Column(name="name")
	private String name;
    
    @Column(name="description")
	private String description;
    
    @Column(name="price")
	private double price;
    
    @Column(name="onsite")
	private Boolean onSite;
    
    @Column(name="numViews")
	private int numViews;
    
    @Column(name="dimension")
	private String dimension;
    
    @Column(name="weight")
	private double weight;
    
    @Column(name="shipping_cost")
	private double shippingCost;

    @Column(name="status")
	@Enumerated
	private ArtworkStatus status;
    
	public void setStatus(ArtworkStatus value) {
		this.status = value;
	}

	public ArtworkStatus getStatus() {
		return this.status;
	}
    
    
    public Artwork() {}
    
    
    @ManyToOne(fetch=FetchType.EAGER,optional = false)
    @JoinColumn(name = "artist_id", nullable = false)
	private Artist artist;

	public Artist getArtist() {
		return this.artist;
	}

	public void setArtist(Artist artist) {
		this.artist = artist;
	}
	
	
	@ManyToMany(fetch = FetchType.EAGER,
            cascade = {
                CascadeType.PERSIST,
                CascadeType.MERGE
            })
    @JoinTable(name = "post_tags",
            joinColumns = { @JoinColumn(name = "artwork_id") },
            inverseJoinColumns = { @JoinColumn(name = "customer_id") })
	private Set<Customer> viewers;

	public Set<Customer> getViewers() {
		return this.viewers;
	}

	public void setViewers(Set<Customer> viewerss) {
		this.viewers = viewerss;
	}
	
	
	

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL,optional=true)
	private Purchase purchase;

	public Purchase getPurchase() {
		return this.purchase;
	}

	public void setPurchase(Purchase purchase) {
		this.purchase = purchase;
	}
	
	
    
    

	public void setArtworkId(Long value) {
		this.artworkId = value;
	}

	public Long getArtworkId() {
		return this.artworkId;
	}


	public void setName(String value) {
		this.name = value;
	}

	public String getName() {
		return this.name;
	}


	public void setDescription(String value) {
		this.description = value;
	}

	public String getDescription() {
		return this.description;
	}


	public void setPrice(double value) {
		this.price = value;
	}

	public double getPrice() {
		return this.price;
	}


	public void setNumViews(int value) {
		this.numViews = value;
	}

	public int getNumViews() {
		return this.numViews;
	}


	public void setOnSite(Boolean value) {
		this.onSite = value;
	}

	public Boolean getOnSite() {
		return this.onSite;
	}


	public void setDimension(String value) {
		this.dimension = value;
	}

	public String getDimension() {
		return this.dimension;
	}


	public void setWeight(double value) {
		this.weight = value;
	}

	public double getWeight() {
		return this.weight;
	}


	public void setShippingCost(double value) {
		this.shippingCost = value;
	}

	public double getShippingCost() {
		return this.shippingCost;
	}


}