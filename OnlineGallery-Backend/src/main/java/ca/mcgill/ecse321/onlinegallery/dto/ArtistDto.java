package ca.mcgill.ecse321.onlinegallery.dto;

import ca.mcgill.ecse321.onlinegallery.model.Artist;

public class ArtistDto {
	
	private Artist a;
	
	public ArtistDto(Artist artist) {
		
		this.a=artist;
	}

	public String getBankInfo() {
		
		return this.a.getBankInfo();
	}
}
