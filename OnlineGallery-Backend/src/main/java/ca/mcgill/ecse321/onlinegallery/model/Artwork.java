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
private Artist artist;

@ManyToOne(optional=false)
public Artist getArtist() {
   return this.artist;
}

public void setArtist(Artist artist) {
   this.artist = artist;
}

private String description;

public void setDescription(String value) {
    this.description = value;
}
public String getDescription() {
    return this.description;
}
private String name;

public void setName(String value) {
    this.name = value;
}
public String getName() {
    return this.name;
}
private double commission;

public void setCommission(double value) {
    this.commission = value;
}
public double getCommission() {
    return this.commission;
}
private double price;

public void setPrice(double value) {
    this.price = value;
}
public double getPrice() {
    return this.price;
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
private ArtworkStatus status;

public void setStatus(ArtworkStatus value) {
    this.status = value;
}
public ArtworkStatus getStatus() {
    return this.status;
}
   private Set<Customer> viewers;
   
   @ManyToMany(mappedBy="browsePieces" )
   public Set<Customer> getViewers() {
      return this.viewers;
   }
   
   public void setViewers(Set<Customer> viewerss) {
      this.viewers = viewerss;
   }
   
   private Purchase purchase;
   
   @OneToOne(mappedBy="artwork" )
   public Purchase getPurchase() {
      return this.purchase;
   }
   
   public void setPurchase(Purchase purchase) {
      this.purchase = purchase;
   }
   
   }
