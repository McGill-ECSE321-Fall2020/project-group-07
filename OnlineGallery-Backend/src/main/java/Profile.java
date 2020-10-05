import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.util.Set;
import javax.persistence.OneToMany;

@Entity
public class Profile{
   private Integer profileId;

public void setProfileId(Integer value) {
    this.profileId = value;
}
@Id
public Integer getProfileId() {
    return this.profileId;
}
private Artist artist;

@OneToOne(optional=false)
public Artist getArtist() {
   return this.artist;
}

public void setArtist(Artist artist) {
   this.artist = artist;
}

private double totalEarnings;

public void setTotalEarnings(double value) {
    this.totalEarnings = value;
}
public double getTotalEarnings() {
    return this.totalEarnings;
}
private int numSold;

public void setNumSold(int value) {
    this.numSold = value;
}
public int getNumSold() {
    return this.numSold;
}
private double rating;

public void setRating(double value) {
    this.rating = value;
}
public double getRating() {
    return this.rating;
}
private String selfDescription;

public void setSelfDescription(String value) {
    this.selfDescription = value;
}
public String getSelfDescription() {
    return this.selfDescription;
}
   private Set<Artwork> artworks;
   
   @OneToMany
   public Set<Artwork> getArtworks() {
      return this.artworks;
   }
   
   public void setArtworks(Set<Artwork> artworkss) {
      this.artworks = artworkss;
   }
   
   }
