package ca.mcgill.ecse321.onlinegallery.dto;

public class ArtistDto {
	
	private String bankInfo;
	
	public ArtistDto() {
		
	}
	public ArtistDto(String bankInfo) {
		
		this.bankInfo=bankInfo;
	}
	
	public String getBankInfo() {
		
		return this.bankInfo;
	}
}
