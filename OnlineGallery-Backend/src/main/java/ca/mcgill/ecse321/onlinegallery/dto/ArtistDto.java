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
}
