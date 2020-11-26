package ca.mcgill.ecse321.retrofit_rxjava.dto;

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

    private String datePurchased;
    public void setDatePurchased(String datePurchased) {
        this.datePurchased = datePurchased;
    }
    public String getDatePurchased() {
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

    String shipmentType;
    public void setShipmentType(String shipmentType) {
        this.shipmentType = shipmentType;
    }
    public String getShipmentType() {
        return this.shipmentType;
    }

}
