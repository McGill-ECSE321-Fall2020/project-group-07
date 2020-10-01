package ca.mcgill.ecse321.onlinegallery.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Id;

@Entity
public class Purchase{

private Integer id;
public void setId(Integer value) {
this.id = value;
    }
@Id
public Integer getId() {
return this.id;
    }

private Artwork artwork;
@ManyToOne(optional=false)
public Artwork getArtwork() {
   return this.artwork;
}

public void setArtwork(Artwork artwork) {
   this.artwork = artwork;
}


private Customer customer;

@ManyToOne(optional=false)
public Customer getCustomer() {
   return this.customer;
}

public void setCustomer(Customer customer) {
   this.customer = customer;
}

}
