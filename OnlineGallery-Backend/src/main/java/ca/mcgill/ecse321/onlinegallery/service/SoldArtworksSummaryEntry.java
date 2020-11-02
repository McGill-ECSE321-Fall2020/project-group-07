package ca.mcgill.ecse321.onlinegallery.service;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

public class SoldArtworksSummaryEntry {
	Map<String, String> nameMap;
	Map<String, Double> priceMap;
	Map<String, Double> commisionMap;
	Map<String, Date> dateMap;
	
	
	public SoldArtworksSummaryEntry(String aName, double aPrice, double aCommission, Date aDate) {
		nameMap = new HashMap <String, String> ();
		nameMap.put("name", aName);
		
		priceMap = new HashMap <String, Double> ();
		priceMap.put("price", aPrice);
		
		commisionMap = new HashMap <String, Double> ();
		commisionMap.put("commission", aCommission);
		
		dateMap = new HashMap <String, Date> ();
		dateMap.put("datePurchased", aDate);
		
		
		
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

}