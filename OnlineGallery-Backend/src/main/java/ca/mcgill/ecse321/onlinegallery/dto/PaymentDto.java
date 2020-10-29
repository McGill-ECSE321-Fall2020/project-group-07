package ca.mcgill.ecse321.onlinegallery.dto;


public class PaymentDto {

	private Long shipmentId;
	
	public void setShipmentId(Long id) {
		this.shipmentId=id;
	}
	public Long getShipmentId() {
		return this.shipmentId;
	}
	
	
	
	private String ccNum;
	
	public void setCcNum(String ccNum) {
		this.ccNum=ccNum;
	}
	public String getCcNum() {
		return this.ccNum;
	}
	
	
	private String ccCSV;
	
	public void setCcCSV(String ccCSV) {
		this.ccCSV=ccCSV;
	}
	public String getCcCSV() {
		return this.ccCSV;
	}
	
	
	
	private String ccExp;
	
	public void setCcExp(String date) {
		this.ccExp=date;
	}
	public String getCcExp() {
		return this.ccExp;
	}
	
	
	
	private String ccFirstName;
	
	public void setCcFirstname(String name) {
		this.ccFirstName=name;
	}
	public String getCcFirstname() {
		return this.ccFirstName;
	}
	
	

	private String ccLastName;
	
	public void setCcLastname(String name) {
		this.ccLastName=name;
	}
	public String getccLastname() {
		return this.ccLastName;
	}
	
	

	
	
	
}
