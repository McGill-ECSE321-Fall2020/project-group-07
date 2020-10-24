package ca.mcgill.ecse321.onlinegallery.dto;

import ca.mcgill.ecse321.onlinegallery.model.OnlineGallery;

public class OnlineGalleryDto {
	
	private OnlineGallery onlineGallery;
	
	public OnlineGalleryDto(OnlineGallery og) {
		
		this.onlineGallery = og;
	}
	
	public int getDaysUp() {
		
		return this.onlineGallery.getDaysUp();
	}
}
