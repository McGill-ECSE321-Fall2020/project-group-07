package ca.mcgill.ecse321.retrofit_rxjava.dto;

public class AvailableNumDto {
    int available;

    public AvailableNumDto(int available) {
        this.available = available;
    }

    public int getAvailable() {
        return available;
    }

    public void setAvailable(int available) {
        this.available = available;
    }
}