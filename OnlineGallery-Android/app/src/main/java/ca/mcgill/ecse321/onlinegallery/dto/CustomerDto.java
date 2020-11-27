package ca.mcgill.ecse321.onlinegallery.dto;

public class CustomerDto {

	private Long customerId;
	public void setCustomerId(Long id) {
		this.customerId = id;
	}
	public Long getCustomerId() {
		return this.customerId;
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

	@Override
	public String toString() {
		return "CustomerDto{" +
				"customerId=" + customerId +
				", bankInfo='" + bankInfo + '\'' +
				", username='" + username + '\'' +
				'}';
	}
}
