package ca.mcgill.ecse321.onlinegallery.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Set;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

@Entity
public class Artwork{
   private Integer artworkId;

public void setArtworkId(Integer value) {
    this.artworkId = value;
}
@Id
public Integer getArtworkId() {
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
private double price;

public void setPrice(double value) {
    this.price = value;
}
public double getPrice() {
    return this.price;
}
private ArtworkStatus status;

public void setStatus(ArtworkStatus value) {
    this.status = value;
}
public ArtworkStatus getStatus() {
    return this.status;
}
private int numViews;

public void setNumViews(int value) {
    this.numViews = value;
}
public int getNumViews() {
    return this.numViews;
}
private Boolean onSite;

public void setOnSite(Boolean value) {
    this.onSite = value;
}
public Boolean getOnSite() {
    return this.onSite;
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
private double shippingCost;

public void setShippingCost(double value) {
    this.shippingCost = value;
}
public double getShippingCost() {
    return this.shippingCost;
}
   private Artist artist;
   
   @ManyToOne(optional=false)
   public Artist getArtist() {
      return this.artist;
   }
   
   public void setArtist(Artist artist) {
      this.artist = artist;
   }
   
   private Set<Customer> viewers;
   
   @ManyToMany(mappedBy="browseArtworks" )
   public Set<Customer> getViewers() {
      return this.viewers;
   }
   
   public void setViewers(Set<Customer> viewerss) {
      this.viewers = viewerss;
   }
   
   private Purchase purchase;
   
   @OneToOne
   public Purchase getPurchase() {
      return this.purchase;
   }
   
   public void setPurchase(Purchase purchase) {
      this.purchase = purchase;
   }
   
   }
