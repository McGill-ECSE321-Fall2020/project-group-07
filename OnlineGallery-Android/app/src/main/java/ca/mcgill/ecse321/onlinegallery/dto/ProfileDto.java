package ca.mcgill.ecse321.onlinegallery.dto;

public class ProfileDto {

    private Long id;
    public void setProfileId(Long id) {
        this.id = id;
    }
    public Long getProfileId() {
        return this.id;
    }

    private String url;
    public void setUrl(String url) {
        this.url=url;
    }
    public String getUrl() {
        return this.url;
    }

    private int numSold;
    public void setNumSold(int numSold) {
        this.numSold = numSold;
    }
    public int getNumSold() {
        return this.numSold;
    }

    private double totalEarnings;
    public void setTotalEarnings(double totalEarnings) {
        this.totalEarnings = totalEarnings;
    }
    public double getTotalEarnings() {
        return this.totalEarnings;
    }

    private String selfDescription;
    public void setSelfDescription(String selfDescription) {
        this.selfDescription = selfDescription;
    }
    public String getSelfDescription() {
        return this.selfDescription;
    }

    private double rating;
    public void setRating(double rating) {
        this.rating = rating;
    }
    public double getRating() {
        return this.rating;
    }

    private String username;
    public void setUsername(String username) {
        this.username = username;
    }
    public String getUsername() {
        return this.username;
    }

}
