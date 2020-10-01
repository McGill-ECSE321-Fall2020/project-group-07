package ca.mcgill.ecse321.onlinegallery.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Set;
import javax.persistence.ManyToMany;

@Entity
public class Artwork{
private String name;
   
   private void setName(String value) {
this.name = value;
    }
@Id
private String getName() {
return this.name;
    }
private double price;

private void setPrice(double value) {
this.price = value;
    }
private double getPrice() {
return this.price;
    }
private double xDim;

private void setXDim(double value) {
this.xDim = value;
    }
private double getXDim() {
return this.xDim;
    }
private double yDim;

public void setYDim(double value) {
this.yDim = value;
    }
public double getYDim() {
return this.yDim;
    }
private Set<Customer> customer;

@ManyToMany(mappedBy="artwork")
public Set<Customer> getCustomer() {
   return this.customer;
}

public void setCustomer(Set<Customer> customers) {
   this.customer = customers;
}

private Location location;

private void setLocation(Location value) {
this.location = value;
    }
private Location getLocation() {
return this.location;
       }
   }
