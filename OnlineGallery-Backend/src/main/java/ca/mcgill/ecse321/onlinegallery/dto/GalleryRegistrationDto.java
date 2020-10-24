package ca.mcgill.ecse321.onlinegallery.dto;

import ca.mcgill.ecse321.onlinegallery.model.GalleryRegistration;

public class GalleryRegistrationDto {

	private GalleryRegistration reg;

	public GalleryRegistrationDto(GalleryRegistration reg) {
		
		this.reg=reg;
	}
	
	public String getUserName() {
		return this.reg.getUserName();
	}
	
	public String getFirstName() {
		return this.reg.getFirstName();
	}
	
	public String getLastName() {
		return this.reg.getLastName();
	}
	
	public String getEmail() {
		return this.reg.getEmail();
	}
	
	public String getPhoneNumber() {
		return this.reg.getPhoneNumber();
	}
	
	public String getPassword() {
		return this.reg.getPassWord();
	}
	
	public boolean getLogInStatus() {
		
		return this.reg.getIsLoggedIn();
	}
	
}
