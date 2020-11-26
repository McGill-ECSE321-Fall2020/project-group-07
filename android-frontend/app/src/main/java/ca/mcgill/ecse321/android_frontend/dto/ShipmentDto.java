package ca.mcgill.ecse321.android_frontend.dto;

import java.util.List;

public class ShipmentDto {


    private Long shipmentId;
    private String sourceAddress;
    private String destinationAddress;
    private double shippingCost;
    private double totalCost;
    private String shipmentStatus;
    private String recipientName;
    private Long customerId;
    private List<Long> purchases;

    public Long getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(Long shipmentId) {
        this.shipmentId = shipmentId;
    }

    public String getSourceAddress() {
        return sourceAddress;
    }

    public void setSourceAddress(String sourceAddress) {
        this.sourceAddress = sourceAddress;
    }

    public String getDestinationAddress() {
        return destinationAddress;
    }

    public void setDestinationAddress(String destinationAddress) {
        this.destinationAddress = destinationAddress;
    }

    public double getShippingCost() {
        return shippingCost;
    }

    public void setShippingCost(double shippingCost) {
        this.shippingCost = shippingCost;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public String getShipmentStatus() {
        return shipmentStatus;
    }

    public void setShipmentStatus(String shipmentStatus) {
        this.shipmentStatus = shipmentStatus;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public List<Long> getPurchases() {
        return purchases;
    }

    public void setPurchases(List<Long> purchases) {
        this.purchases = purchases;
    }

    @Override
    public String toString() {
        return "ShipmentDto{" +
                "shipmentId=" + shipmentId +
                ", sourceAddress='" + sourceAddress + '\'' +
                ", destinationAddress='" + destinationAddress + '\'' +
                ", shippingCost=" + shippingCost +
                ", totalCost=" + totalCost +
                ", shipmentStatus='" + shipmentStatus + '\'' +
                ", recipientName='" + recipientName + '\'' +
                ", customerId=" + customerId +
                ", purchaseId=" + purchases +
                '}';
    }
}
