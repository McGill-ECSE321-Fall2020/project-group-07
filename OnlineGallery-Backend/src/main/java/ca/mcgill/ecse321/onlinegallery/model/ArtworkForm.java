package ca.mcgill.ecse321.onlinegallery.model;

public class ArtworkForm {
	

	private String userName;
	private String name;
	private String description;
	private double price;
	private ArtworkStatus status;
	private boolean onSite;
	private int numViews;
	private String dimension;
	private double weight;
	private double shippingCost;
	
	public String getUserName() {
		return this.userName;
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
