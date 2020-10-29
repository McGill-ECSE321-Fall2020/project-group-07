import java.sql.Date;

public class Purchase {
private long purchaseId;

public void setPurchaseId(long value) {
   this.purchaseId = value;
}

public long getPurchaseId() {
   return this.purchaseId;
}

private double commission;

public void setCommission(double value) {
   this.commission = value;
}

public double getCommission() {
   return this.commission;
}

private ShipmentType shipmentType;

public void setShipmentType(ShipmentType value) {
   this.shipmentType = value;
}

public ShipmentType getShipmentType() {
   return this.shipmentType;
}

private PaymentMethod paymentType;

public void setPaymentType(PaymentMethod value) {
   this.paymentType = value;
}

public PaymentMethod getPaymentType() {
   return this.paymentType;
}

private boolean paid;

public void setPaid(boolean value) {
   this.paid = value;
}

public boolean isPaid() {
   return this.paid;
}

private Date date;

public void setDate(Date value) {
   this.date = value;
}

public Date getDate() {
   return this.date;
}

/**
 * <pre>
 *           0..1     1..1
 * Purchase ------------------------- Artwork
 *           purchase        &gt;       artwork
 * </pre>
 */
private Artwork artwork;

public void setArtwork(Artwork value) {
   this.artwork = value;
}

public Artwork getArtwork() {
   return this.artwork;
}

/**
 * <pre>
 *           1..*     0..1
 * Purchase ------------------------- Shipment
 *           purchase        &gt;       shipment
 * </pre>
 */
private Shipment shipment;

public void setShipment(Shipment value) {
   this.shipment = value;
}

public Shipment getShipment() {
   return this.shipment;
}

}
