package ca.mcgill.ecse321.onlinegallery.dto;

import java.sql.Date;

import ca.mcgill.ecse321.onlinegallery.model.ShipmentType;

public class PurchaseSummaryDto {

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
	
	private String artworkName;
	public void setName(String artworkName) {
		this.artworkName = artworkName;
	}
	public String getName() {
		return this.artworkName;
	}

	public String artistName;
	public void setArtistName(String artistName) {
		this.artistName = artistName;
	}
	public String getArtistName() {
		return this.artistName;
	}
	
	private String artworkUrl;
	public void setArtworkUrl(String url) {
		this.artworkUrl = url;
	}
	public String getArtowkrUrl() {
		return this.artworkUrl;
	}
	
	ShipmentType shipmentType;
	public void setShipmentType(ShipmentType shipmentType) {
		this.shipmentType = shipmentType;
	}
	public ShipmentType getShipmentType() {
		return this.shipmentType;
	}
	
}
