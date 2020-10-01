package ca.mcgill.ecse321.onlinegallery.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Order{
private Integer orderNum;
   
   private void setOrderNum(Integer value) {
this.orderNum = value;
    }
@Id
private Integer getOrderNum() {
return this.orderNum;
    }
private double comission;

private void setComission(double value) {
this.comission = value;
    }
private double getComission() {
return this.comission;
    }
private Customer customer;

@ManyToOne(optional=false)
public Customer getCustomer() {
   return this.customer;
}

public void setCustomer(Customer customer) {
   this.customer = customer;
}

private Artwork artwork;

@ManyToOne(optional=false)
public Artwork getArtwork() {
   return this.artwork;
}

public void setArtwork(Artwork artwork) {
   this.artwork = artwork;
}

}
