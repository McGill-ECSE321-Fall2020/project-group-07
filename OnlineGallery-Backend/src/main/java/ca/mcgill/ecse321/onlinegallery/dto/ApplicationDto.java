package ca.mcgill.ecse321.onlinegallery.dto;

import java.sql.Date;

public class ApplicationDto {

	private Double commission;
	public void setCommission(Double commission) {
		this.commission = commission;
	}
	public Double getComission() {
		return this.commission;
	}
	
	private Double price;
	public void setPrice(Double price) {
		this.price = price;
	}
	public Double getPrice() {
		return this.price;
	}
	
	private Date datePurchased;
	public void setDatePurchased(Date datePurchased) {
		this.datePurchased = datePurchased;
	}
	public Date getDatePurchased() {
		return this.datePurchased;
	}
	
	private String name;
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return this.name;
	}
	
	private Long artworkId;
	public void setArtworkId(Long artworkId) {
		this.artworkId = artworkId;
	}
	public Long getArtworkId() {
		return this.artworkId;
	}
	
}
