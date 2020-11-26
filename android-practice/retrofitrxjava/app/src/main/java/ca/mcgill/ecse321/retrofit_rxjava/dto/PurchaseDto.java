package ca.mcgill.ecse321.retrofit_rxjava.dto;

public class PurchaseDto {
    private String purchaseId;
    private String username;
    private String artworkId;
    private String shipmentType;
    private String artworkUrl;

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

    public void setArtworkUrl(String url) { this.artworkUrl = url; }

    public String getArtowkrUrl() { return this.artworkUrl; }

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
