package ca.mcgill.ecse321.onlinegallery.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Artwork{
private String name;
   
   public void setName(String value) {
this.name = value;
    }
@Id
public String getName() {
return this.name;
    }
private double price;

public void setPrice(double value) {
this.price = value;
    }
public double getPrice() {
return this.price;
    }
private double xDim;

public void setXDim(double value) {
this.xDim = value;
    }
public double getXDim() {
return this.xDim;
    }
private double yDim;

public void setYDim(double value) {
this.yDim = value;
    }
public double getYDim() {
return this.yDim;
       }
   }
