package ca.mcgill.ecse321.onlinegallery.service;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

import ca.mcgill.ecse321.onlinegallery.model.ShipmentType;

public class SoldArtworksSummaryEntry {
	Map<String, String> nameMap;
	Map<String, Double> priceMap;
	Map<String, Double> commisionMap;
	Map<String, Date> dateMap;
	Map<String, String> customerMap;
	Map<String, String> artistMap;
	Map<String, ShipmentType> shipmentMap;
	
	public SoldArtworksSummaryEntry(String aName, double aPrice, double aCommission, Date aDate, ShipmentType shipmentType, String customerName, String artistName) {
		nameMap = new HashMap <String, String> ();
		nameMap.put("name", aName);
		
		priceMap = new HashMap <String, Double> ();
		priceMap.put("price", aPrice);
		
		commisionMap = new HashMap <String, Double> ();
		commisionMap.put("commission", aCommission);
		
		dateMap = new HashMap <String, Date> ();
		dateMap.put("datePurchased", aDate);
		
		customerMap = new HashMap <String, String> ();
		customerMap.put("customer", customerName);
		
		artistMap = new HashMap <String, String> ();
		artistMap.put("artist", artistName);
		
		shipmentMap = new HashMap <String, ShipmentType> ();
		shipmentMap.put("shipmentType", shipmentType);
		
	}


	public Map<String, String> getNameMap() {
		return nameMap;
	}


	public Map<String, Double> getPriceMap() {
		return priceMap;
	}

	public Map<String, Double> getCommisionMap() {
		return commisionMap;
	}

	public Map<String, Date> getDateMap() {
		return dateMap;
	}
	
	public Map<String, String> getCustomerMap(){
		return customerMap;
	}
	
	public Map<String, String> getArtistMap(){
		return artistMap;
	}
	
	public Map<String, ShipmentType> getShipmentMap(){
		return shipmentMap;
	}

}