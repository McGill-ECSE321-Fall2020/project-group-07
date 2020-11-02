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
import ca.mcgill.ecse321.onlinegallery.model.Artwork;
import ca.mcgill.ecse321.onlinegallery.service.ApplicationService;
import ca.mcgill.ecse321.onlinegallery.service.ArtworkService;
import ca.mcgill.ecse321.onlinegallery.service.SoldArtworksSummaryEntry;
import ca.mcgill.ecse321.onlinegallery.service.exception.ApplicationException;
import ca.mcgill.ecse321.onlinegallery.service.exception.ArtworkException;

@CrossOrigin(origins = "*")
@RestController
public class ApplicationRestController {

	@Autowired
	ApplicationService service;
	
	@Autowired
	ArtworkService artworkService;
	
	@GetMapping(value = { "/generateSummary", "/generateSummary/" })
	public ResponseEntity<?> generateSummary() throws ApplicationException, ArtworkException {
		try {
			List<Artwork> artworks = artworkService.getAllArtworks();
			Map<Long, SoldArtworksSummaryEntry> summary = service.generateSummary();
			return new ResponseEntity<>(convertToDto(summary, artworks), HttpStatus.OK);			
		} catch(ApplicationException e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
	}
	
	private List<ApplicationDto> convertToDto(Map<Long, SoldArtworksSummaryEntry> summary, List<Artwork> artworks) {

		List<ApplicationDto> dto = new ArrayList<ApplicationDto>();
		
		for(int i=0; i<artworks.size(); i++) {
			
			SoldArtworksSummaryEntry entry = summary.get(artworks.get(i).getArtworkId());
			Map<String, Double> commissionMap = entry.getCommisionMap();
			Map<String, Double> priceMap = entry.getPriceMap();
			Map<String, Date> dateMap = entry.getDateMap();
			Map<String, String> nameMap = entry.getNameMap();
			
			double commission = commissionMap.get("commission");
			double price = priceMap.get("price");
			Date datePurchased = dateMap.get("datePurchased");
			String name = nameMap.get("name");
			
			ApplicationDto appDto = new ApplicationDto();
			appDto.setCommission(commission);
			appDto.setDatePurchased(datePurchased);
			appDto.setArtworkId(artworks.get(i).getArtworkId());
			appDto.setPrice(price);
			appDto.setName(name);
			
			dto.add(appDto);
			
		}
		
		return dto;
	}
	
	
}