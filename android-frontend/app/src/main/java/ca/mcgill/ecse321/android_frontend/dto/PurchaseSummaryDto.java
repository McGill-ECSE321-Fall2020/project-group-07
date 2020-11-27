package ca.mcgill.ecse321.android_frontend.dto;

import com.google.gson.annotations.SerializedName;

public class PurchaseSummaryDto {

    @SerializedName("comission")
    private Double commission;
    public Double getCommission() {
        return this.commission;
    }

    private Double price;
    public void setPrice(Double price) {
        this.price = price;
    }
    public Double getPrice() {
        return this.price;
    }

    private String datePurchased;
    public void setDatePurchased(String datePurchased) {
        this.datePurchased = datePurchased;
    }
    public String getDatePurchased() {
        return this.datePurchased;
    }

    @SerializedName("name")
    private String artworkName;
    public String getName() { return this.artworkName; }

    public String artistName;
    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }
    public String getArtistName() {
        return this.artistName;
    }

    @SerializedName("artowkrUrl")
    private String artworkUrl;
    public String getArtworkUrl() { return this.artworkUrl; }

    String shipmentType;
    public void setShipmentType(String shipmentType) {
        this.shipmentType = shipmentType;
    }
    public String getShipmentType() {
        return this.shipmentType;
    }

}
