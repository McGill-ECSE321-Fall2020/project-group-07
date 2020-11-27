package ca.mcgill.ecse321.android_frontend.dto;

import android.graphics.Bitmap;

import java.io.Serializable;

public class ArtworkDto implements Serializable, Comparable {
    private String url;
    private String medium;
    private String username;
    private String name;
    private String description;
    private double price;
    private String status;
    private int numViews;
    private String dimension;
    private double weight;
    private double commission;
    private int artworkId;
    private Bitmap artBitmap;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMedium() {
        return medium;
    }

    public void setMedium(String medium) {
        this.medium = medium;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getNumViews() {
        return numViews;
    }

    public void setNumViews(int numViews) {
        this.numViews = numViews;
    }

    public String getDimension() {
        return dimension;
    }

    public void setDimension(String dimension) {
        this.dimension = dimension;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getCommission() {
        return commission;
    }

    public void setCommission(double commission) {
        this.commission = commission;
    }

    public int getArtworkId() {
        return artworkId;
    }

    public void setArtworkId(int artworkId) {
        this.artworkId = artworkId;
    }

    public Bitmap getArtBitmap() {
        return artBitmap;
    }

    public void setArtBitmap(Bitmap artBitmap) {
        this.artBitmap = artBitmap;
    }



    @Override
    public String toString() {
        return "ArtworkDto{" +
                "url='" + url + '\'' +
                ", medium='" + medium + '\'' +
                ", username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", status='" + status + '\'' +
                ", numViews=" + numViews +
                ", dimension='" + dimension + '\'' +
                ", weight=" + weight +
                ", commission=" + commission +
                ", artworkId=" + artworkId +
                '}'+"\n";
    }

    @Override
    public int compareTo(Object arg0) {
        ArtworkDto otherDto = (ArtworkDto) arg0;

        int otherId = otherDto.getArtworkId();
        int thisId = this.getArtworkId();

        if (thisId<otherId) {
            return 1;
        }
        else if (thisId==otherId) {
            return 0;
        }

        return -1;
    }
}
