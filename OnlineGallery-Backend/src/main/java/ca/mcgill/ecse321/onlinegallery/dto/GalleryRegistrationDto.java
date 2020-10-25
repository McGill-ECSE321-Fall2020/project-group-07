package ca.mcgill.ecse321.onlinegallery.dto;


public class GalleryRegistrationDto {

	private String username;
	public String getUsername() {
		return this.username;
	}
	public void setUsername(String username) {
		this.username=username;
	}
	
	private String firstname;
	public String getFirstName() {
		return this.firstname;
	}
	public void setFirstName(String firstname) {
		this.firstname=firstname;
	}
	
	private String lastname;
	public String getLastName() {
		return this.lastname;
	}
	public void setLastName(String lastname) {
		this.lastname=lastname;
	}
	
	private String email;
	public String getEmail() {
		return this.email;
	}
	public void setEmail(String email) {
		this.email=email;
	}
	
	private String password;
	public String getPassword() {
		return this.password;
	}
	public void setPassword(String password) {
		this.password=password;
	}
	
	private boolean loggedIn=false;
	public boolean isLoggedIn() {
		return this.loggedIn;
	}
	public void setLoggedIn(boolean status) {
		this.loggedIn=status;
	}
	
}
