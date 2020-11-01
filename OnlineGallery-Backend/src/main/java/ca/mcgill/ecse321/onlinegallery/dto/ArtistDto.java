package ca.mcgill.ecse321.onlinegallery.dto;

public class ArtistDto {
	
	private Long artistId;
	public void setArtistId(Long id) {
		this.artistId=id;
	}
	public Long getArtistId() {
		return this.artistId;
	}
	private String bankInfo;
	public void setBankInfo(String bankInfo) {
		this.bankInfo=bankInfo;
	}
	public String getBankInfo() {
		return this.bankInfo;
	}
	private String username;
	public void setUsername(String username) {
		this.username=username;
	}
	public String getUsername() {
		return this.username;
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
	private Long id;
	public void setProfileId(Long id) {
		this.id = id;
	}
	public Long getProfileId() {
		return this.id;
	}
}
