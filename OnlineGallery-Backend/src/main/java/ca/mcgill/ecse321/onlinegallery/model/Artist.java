package ca.mcgill.ecse321.onlinegallery.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.CascadeType;
import javax.persistence.OneToOne;
import java.util.Set;
import javax.persistence.OneToMany;

@Entity
public class Artist extends UserRole{
   private Integer artistId;

public void setArtistId(Integer value) {
    this.artistId = value;
}
@Id
public Integer getArtistId() {
    return this.artistId;
}
   private Profile profile;
   
   @OneToOne(mappedBy="artist" , cascade={CascadeType.ALL}, optional=false)
   public Profile getProfile() {
      return this.profile;
   }
   
   public void setProfile(Profile profile) {
      this.profile = profile;
   }
   
   private Set<Artwork> artworks;
   
   @OneToMany(mappedBy="artist" , cascade={CascadeType.ALL})
   public Set<Artwork> getArtworks() {
      return this.artworks;
   }
   
   public void setArtworks(Set<Artwork> artworkss) {
      this.artworks = artworkss;
   }
   
   }
