package ca.mcgill.ecse321.onlinegallery.dto;

import ca.mcgill.ecse321.onlinegallery.model.ArtworkStatus;

public class ArtworkDto {


	private Long id;
	public void setArtworkId(Long id) {
		this.id = id;
	}
	public Long getArtworkId() {
		return this.id;
	}
	
	private String url;
	public void setUrl(String url) {
		this.url=url;
	}
	public String getUrl() {
		return this.url;
	}
	
	private String medium;
	public void setMedium(String medium) {
		this.medium=medium;
	}
	public String getMedium() {
		return this.medium;
	}

	private String username;
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUsername() {
		return this.username;
	}
	private String name;
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return this.name;
	}
	private String description;
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return this.description;
	}
	private double price;
	public void setPrice(double price) {
		this.price = price;
	}
	public double getPrice() {
		return this.price;
	}
	private ArtworkStatus status;
	public void setStatus(ArtworkStatus status) {
		this.status = status;
	}
	public ArtworkStatus getStatus() {
		return this.status;
	}
	private int numViews;
	public void setNumViews(int numViews) {
		this.numViews = numViews;
	}
	public int getNumViews() {
		return this.numViews;
	}
	private String dimension;
	public void setDimension(String dimension) {
		this.dimension = dimension;
	}
	public String getDimension() {
		return this.dimension;
	}
	private double weight;
	public void setWeight(double weight) {
		this.weight = weight;
	}
	public double getWeight() {
		return this.weight;
	}
	private double commission;
	public void setCommission(double commission) {
		this.commission = commission;
	}
	public double getCommission() {
		return this.commission;
	}
}
