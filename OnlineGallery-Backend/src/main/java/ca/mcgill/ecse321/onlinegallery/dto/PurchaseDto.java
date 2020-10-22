package ca.mcgill.ecse321.onlinegallery.dto;

import java.sql.Date;

import ca.mcgill.ecse321.onlinegallery.model.PaymentMethod;
import ca.mcgill.ecse321.onlinegallery.model.ShipmentType;

public class PurchaseDto {

	private double commission;
	private ShipmentType shipmentType;
	private PaymentMethod paymentMethod;
	private boolean paid;
	private Date purchasedDate;

	public PurchaseDto(double commission, 
					   ShipmentType shipmentType, 
					   PaymentMethod paymentMethod, 
					   boolean paid,
					   Date purchasedDate) {
		
		this.commission=commission;
		this.shipmentType=shipmentType;
		this.paymentMethod=paymentMethod;
		this.paid=paid;
		this.purchasedDate=purchasedDate;

	}

	public double getCommission() {
		return this.commission;
	}
	
	public ShipmentType getShipmentType() {
		return this.shipmentType;
	}
	
	public PaymentMethod getPaymentMethod() {
		return this.paymentMethod;
	}
	
	public boolean isPaid() {
		return this.paid;
	}
	
	public Date getPurchaseDate() {
		return this.purchasedDate;
	}

}
