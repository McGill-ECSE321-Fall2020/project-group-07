package ca.mcgill.ecse321.onlinegallery.dto;


public class UpdatePasswordDto {

	private String username;
	public String getUsername() {
		return this.username;
	}
	public void setUsername(String username) {
		this.username=username;
	}
	

	private String password;
	public String getPassowrd() {
		return this.password;
	}
	public void setpassWord(String pwd) {
		this.password=pwd;
	}
	
	
}
