package ca.mcgill.ecse321.onlinegallery.dto;

import java.sql.Date;

import ca.mcgill.ecse321.onlinegallery.model.ShipmentType;


public class PurchaseDto {
	private Long purchaseId;
	public void setPurchaseId(Long id) {
		this.purchaseId=id;
	}
	public Long getPurcahseId() {
		return this.purchaseId;
	}
	
	private String username;
	public void setUsername(String username) {
		this.username=username;
	}
	public String getUsername() {
		return this.username;
	}
	
	private Long artworkId;
	public void setArtworkId(Long id) {
		this.artworkId=id;
	}
	public Long getArtworkId()
	{
		return this.artworkId;
	}	
	
	private ShipmentType shipmentType;
	public void setShipmentType(ShipmentType type) {
		this.shipmentType=type;
	}
	public ShipmentType getShipmentType() {
		return this.shipmentType;
	}
}
