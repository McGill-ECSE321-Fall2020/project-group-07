package ca.mcgill.ecse321.onlinegallery.model;


public class PurchaseUpdateForm {

		private String userName;
		private Long artworkId;
		private double commission;
		private ShipmentType shipmentType;
		private PaymentMethod paymentMethod;
		private boolean paid;
		
		
		public String getUserName() {
			return this.userName;
		}
		
		public Long getArtworkId() {
			return this.artworkId;
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
}
