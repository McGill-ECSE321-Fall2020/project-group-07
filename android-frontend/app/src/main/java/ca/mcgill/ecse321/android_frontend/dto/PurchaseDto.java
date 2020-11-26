package ca.mcgill.ecse321.android_frontend.dto;

public class PurchaseDto {
    private String purchaseId;
    private String username;
    private String artworkId;
    private String shipmentType;

    public String getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(String purchaseId) {
        this.purchaseId = purchaseId;
    }



    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getArtworkId() {
        return artworkId;
    }

    public void setArtworkId(String artworkId) {
        this.artworkId = artworkId;
    }

    public String getShipmentType() {
        return shipmentType;
    }

    public void setShipmentType(String shipmentType) {
        this.shipmentType = shipmentType;
    }

    @Override
    public String toString() {
        return "PurchaseDto{" +
                "purchaseId='" + purchaseId + '\'' +
                ", username='" + username + '\'' +
                ", artworkId='" + artworkId + '\'' +
                ", shipmentType='" + shipmentType + '\'' +
                '}';
    }
}
