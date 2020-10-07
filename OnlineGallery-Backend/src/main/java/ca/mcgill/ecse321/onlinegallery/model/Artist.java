package ca.mcgill.ecse321.onlinegallery.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.CascadeType;
import javax.persistence.OneToOne;
import java.util.Set;
import javax.persistence.OneToMany;

@Entity
public class Artist{
   private Integer artistId;

public void setArtistId(Integer value) {
    this.artistId = value;
}
@Id
public Integer getArtistId() {
    return this.artistId;
}
private String bankInfo;

public void setBankInfo(String value) {
    this.bankInfo = value;
}
public String getBankInfo() {
    return this.bankInfo;
}
   private GalleryRegistration galleryRegistration;
   
   @ManyToOne(optional=false)
   public GalleryRegistration getGalleryRegistration() {
      return this.galleryRegistration;
   }
   
   public void setGalleryRegistration(GalleryRegistration galleryRegistration) {
      this.galleryRegistration = galleryRegistration;
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
