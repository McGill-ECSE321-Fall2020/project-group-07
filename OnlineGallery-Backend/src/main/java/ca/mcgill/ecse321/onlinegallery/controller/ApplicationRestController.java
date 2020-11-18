package ca.mcgill.ecse321.onlinegallery.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.onlinegallery.dto.ApplicationDto;
import ca.mcgill.ecse321.onlinegallery.model.Purchase;
import ca.mcgill.ecse321.onlinegallery.model.ShipmentType;
import ca.mcgill.ecse321.onlinegallery.service.ApplicationService;
import ca.mcgill.ecse321.onlinegallery.service.PurchaseService;
import ca.mcgill.ecse321.onlinegallery.service.SoldArtworksSummaryEntry;
import ca.mcgill.ecse321.onlinegallery.service.exception.ApplicationException;
import ca.mcgill.ecse321.onlinegallery.service.exception.PurchaseException;

@CrossOrigin(origins = "*")
@RestController
public class ApplicationRestController {

	@Autowired
	ApplicationService service;
	
	@Autowired
	PurchaseService purchaseService;
	
	@GetMapping(value = { "/generateSummary", "/generateSummary/" })
	public ResponseEntity<?> generateSummary() throws ApplicationException, PurchaseException {
		try {
			List<Purchase> purchases = purchaseService.getAllPurchases();
			Map<Long, SoldArtworksSummaryEntry> summary = service.generateSummary();
			return new ResponseEntity<>(convertToDto(summary, purchases), HttpStatus.OK);			
		} catch(ApplicationException e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
	}
	
	private List<ApplicationDto> convertToDto(Map<Long, SoldArtworksSummaryEntry> summary, List<Purchase> purchases) {

		List<ApplicationDto> dto = new ArrayList<ApplicationDto>();
		
		for(int i=0; i<purchases.size(); i++) {
			
			SoldArtworksSummaryEntry entry = summary.get(purchases.get(i).getArtwork().getArtworkId());
			Map<String, Double> commissionMap = entry.getCommisionMap();
			Map<String, Double> priceMap = entry.getPriceMap();
			Map<String, Date> dateMap = entry.getDateMap();
			Map<String, String> nameMap = entry.getNameMap();
			Map<String, String> customerMap = entry.getCustomerMap();
			Map<String, String> artistMap = entry.getArtistMap();
			Map<String, ShipmentType> shipmentMap = entry.getShipmentMap();
			Map<String, Long> shipmentIdMap = entry.getShipmentIdMap();
			
			double commission = commissionMap.get("commission");
			double price = priceMap.get("price");
			Date datePurchased = dateMap.get("datePurchased");
			String name = nameMap.get("name");
			ShipmentType shipmentType = shipmentMap.get("shipmentType");
			String customerName = customerMap.get("customer");
			String artistName = artistMap.get("artist");
			Long shipmentId = shipmentIdMap.get("shipmentId");
			
			ApplicationDto appDto = new ApplicationDto();
			appDto.setCommission(commission);
			appDto.setDatePurchased(datePurchased);
			appDto.setArtworkId(purchases.get(i).getArtwork().getArtworkId());
			appDto.setPrice(price);
			appDto.setName(name);
			appDto.setArtistName(artistName);
			appDto.setCustomerName(customerName);
			appDto.setShipmentType(shipmentType);
			appDto.setShipmentId(shipmentId);
			
			dto.add(appDto);
			
		}
		
		return dto;
	}
	
	
}