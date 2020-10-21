package ca.mcgill.ecse321.onlinegallery.dto;

public class GalleryRegistrationDto {

	private String userName;
	private String firstName;
	private String lastName;
	private String email;
	private String phoneNumber;
	private String password;
	private boolean isLoggedIn;

	public GalleryRegistrationDto(String userName, 
								  String firstName, 
								  String lastName, 
								  String email, 
								  String phoneNumber,
								  String passWord,
								  boolean isLoggedIn
								  ) {
		
		this.userName=userName;
		this.firstName=firstName;
		this.lastName=lastName;
		this.email=email;
		this.phoneNumber=phoneNumber;
		this.password=passWord;
		this.isLoggedIn=isLoggedIn;
	
	}
	
	public String getUserName() {
		return this.userName;
	}
	
	public String getFirstName() {
		return this.firstName;
	}
	
	public String getLastName() {
		return this.lastName;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	public String getPhoneNumber() {
		return this.phoneNumber;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public boolean getLogInStatus() {
		return this.isLoggedIn;
	}
	
}
