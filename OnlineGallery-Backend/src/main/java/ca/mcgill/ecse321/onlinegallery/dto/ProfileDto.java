package ca.mcgill.ecse321.onlinegallery.dto;

import ca.mcgill.ecse321.onlinegallery.model.Profile;

public class ProfileDto {

	private Profile profile;
	
	public ProfileDto(Profile profile){
		this.profile = profile;
	}
	
	public long getProfileId() {
		return profile.getProfileId();
	}
	
	public int getNumSold() {
		return profile.getNumSold();
	}
	
	public double getTotalEarnings() {
		return profile.getTotalEarnings();
	}
	
	public String getSelfDescription() {
		return profile.getSelfDescription();
	}
	
	public double getRating() {
		return profile.getRating();
	}
}
