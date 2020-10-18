package ca.mcgill.ecse321.onlinegallery.dto;

public class PhysicalGalleryDto {
	
	private String address;
	
	public PhysicalGalleryDto() {
		
	}
	
	public PhysicalGalleryDto(String address){
		this.address=address;
	}
	
	public String getAddress() {
		return this.address;
	}

}
