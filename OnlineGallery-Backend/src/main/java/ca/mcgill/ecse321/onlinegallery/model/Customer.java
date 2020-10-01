package ca.mcgill.ecse321.onlinegallery.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Date;
import java.util.Set;
import javax.persistence.ManyToMany;

@Entity
public class Customer{
private String name;
   
   private void setName(String value) {
this.name = value;
    }
@Id
private String getName() {
return this.name;
    }
private Date dob;

private void setDob(Date value) {
this.dob = value;
    }
private Date getDob() {
return this.dob;
    }
private Set<Artwork> artwork;

@ManyToMany
public Set<Artwork> getArtwork() {
   return this.artwork;
}

public void setArtwork(Set<Artwork> artworks) {
   this.artwork = artworks;
}

}
