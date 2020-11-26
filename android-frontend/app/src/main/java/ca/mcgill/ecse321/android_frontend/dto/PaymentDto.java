package ca.mcgill.ecse321.android_frontend.dto;

public class PaymentDto {

    private String ccNum;
    private String ccCSV;
    private String ccExp;
    private String firstName;
    private String lastName;
    private Long shipmentId;

    public String getCcNum() {
        return ccNum;
    }

    public void setCcNum(String ccNum) {
        this.ccNum = ccNum;
    }

    public String getCcCSV() {
        return ccCSV;
    }

    public void setCcCSV(String ccCSV) {
        this.ccCSV = ccCSV;
    }

    public String getCcExp() {
        return ccExp;
    }

    public void setCcExp(String ccExp) {
        this.ccExp = ccExp;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Long getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(Long shipmentId) {
        this.shipmentId = shipmentId;
    }

    @Override
    public String toString() {
        return "PaymentDto{" +
                "ccNum='" + ccNum + '\'' +
                ", ccCSV='" + ccCSV + '\'' +
                ", ccExp='" + ccExp + '\'' +
                ", firstname='" + firstName + '\'' +
                ", lastname='" + lastName + '\'' +
                ", shipmentId=" + shipmentId +
                '}';
    }
}
