package ca.mcgill.ecse321.onlinegallery.dto;

public class CustomerDto {

	private Long id;
	public void setCustomerId(Long id) {
		this.id = id;
	}
	public Long getCustomerId() {
		return this.id;
	}
	
	private String bankInfo;
	public void setBankInfo(String bankInfo) {
		this.bankInfo = bankInfo;
	}
	public String getBankInfo() {
		return this.bankInfo;
	}
	
	private String username;
	public void setUsername(String username) {
		this.username=username;
	}
	public String getUsername() {
		return this.username;
	}
}
