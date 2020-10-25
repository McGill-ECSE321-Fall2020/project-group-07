//package ca.mcgill.ecse321.onlinegallery.dto;
//
//import java.sql.Date;
//
//import ca.mcgill.ecse321.onlinegallery.model.PaymentMethod;
//import ca.mcgill.ecse321.onlinegallery.model.Purchase;
//import ca.mcgill.ecse321.onlinegallery.model.ShipmentType;
//
//public class PurchaseDto {
//	private Purchase p;
//
//	public PurchaseDto(Purchase purchase) {
//		this.p=purchase;
//	}
//
//	public double getCommission() {
//		return this.p.getCommission();
//	}
//	
//	public ShipmentType getShipmentType() {
//		return this.p.getShipmentType();
//	}
//	
//
//	
//	public boolean isPaid() {
//		return this.p.isPaid();
//	}
//	
//	public Date getPurchaseDate() {
//		return this.p.getDate();
//	}
//	
//	public String getArtName() {
//		return this.p.getArtwork().getName();
//	}
//	
//	public Long getCustomerId() {
//		return this.p.getCustomer().getCustomerId();
//	}
//	
//	public String getUsername() {
//		return this.p.getCustomer().getGalleryRegistration().getUserName();
//	}
//
//}
