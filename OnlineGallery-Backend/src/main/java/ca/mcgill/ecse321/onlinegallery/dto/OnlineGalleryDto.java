package ca.mcgill.ecse321.onlinegallery.dto;

public class OnlineGalleryDto {
	
	private int daysUp;
	
	public OnlineGalleryDto() {
		
	}
	public OnlineGalleryDto(int daysUp) {
		
		this.daysUp=daysUp;
	}
	
	public int getDaysUp() {
		
		return this.daysUp;
	}
}
