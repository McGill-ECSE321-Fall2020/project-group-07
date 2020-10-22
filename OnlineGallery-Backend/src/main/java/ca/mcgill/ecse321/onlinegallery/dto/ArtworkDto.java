package ca.mcgill.ecse321.onlinegallery.dto;

import java.sql.Date;

import ca.mcgill.ecse321.onlinegallery.model.ArtworkStatus;
import ca.mcgill.ecse321.onlinegallery.model.PaymentMethod;
import ca.mcgill.ecse321.onlinegallery.model.ShipmentType;

public class ArtworkDto {

	private String name;
	private String description;
	private double price;
	private ArtworkStatus status;
	private boolean onSite;
	private int numViews;
	private String dimension;
	private double weight;
	private double shippingCost;

	public ArtworkDto(String name,
					  String description,
					  double price,
					  ArtworkStatus status,
					  boolean onSite,
					  int numViews,
					  String dimension,
					  double weight,
					  double shippingCost) {
		
		this.name=name;
		this.description=description;
		this.price=price;
		this.status=status;
		this.onSite=onSite;
		this.numViews=numViews;
		this.dimension=dimension;
		this.weight=weight;
		this.shippingCost=shippingCost;

	}

	public String getName() {
		return this.name;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public double getPrice() {
		return this.price;
	}
	
	public ArtworkStatus getStatus() {
		return this.status;
	}
	
	public boolean isOnSite() {
		return this.onSite;
	}
	
	public int getNumViews() {
		return this.numViews;
	}
	
	public String getDimension() {
		return this.dimension;
	}
	
	public double getWeight() {
		return this.weight;
	}
	
	public double getShippingcost() {
		return this.shippingCost;
	}

}
