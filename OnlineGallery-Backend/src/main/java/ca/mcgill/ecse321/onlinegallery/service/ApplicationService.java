package ca.mcgill.ecse321.onlinegallery.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.onlinegallery.dao.ArtworkRepository;
import ca.mcgill.ecse321.onlinegallery.dao.PurchaseRepository;
import ca.mcgill.ecse321.onlinegallery.model.Purchase;
import ca.mcgill.ecse321.onlinegallery.model.ShipmentType;
import ca.mcgill.ecse321.onlinegallery.service.exception.ApplicationException;

@Service
public class ApplicationService {

	@Autowired
	PurchaseRepository purchaseRepo;
	
	@Autowired
	ArtworkRepository artworkRepo;
	
	@Transactional
	public Map<Long, SoldArtworksSummaryEntry> generateSummary () throws ApplicationException{
		Map<Long, SoldArtworksSummaryEntry> summaryList = new HashMap <Long, SoldArtworksSummaryEntry> ();
		if (purchaseRepo.count() == 0) {
			throw new ApplicationException ("no Purchase found in system");
		}
		else {
			for (Purchase p : toList(purchaseRepo.findAll())) {
				long artworkId = p.getArtwork().getArtworkId();
				String name = p.getArtwork().getName();
				double price = p.getArtwork().getPrice();
				double commission = p.getArtwork().getComission();
				Date purshaseDate = p.getDate();
				ShipmentType shipmentType = p.getShipmentType();
				String customerName = p.getCustomer().getGalleryRegistration().getUserName();
				String artistName = p.getArtwork().getArtist().getGalleryRegistration().getUserName();
				Long shipmentId;
				try {
					shipmentId = p.getShipment().getShipmentId();
				}
				catch (Exception e) {
					shipmentId= (long) -1;
				}

				SoldArtworksSummaryEntry aSummary = new SoldArtworksSummaryEntry(name, price, commission, purshaseDate, shipmentType, customerName, artistName, shipmentId);
				summaryList.put(artworkId, aSummary);
			
			}
		}
		
		return summaryList;
	}
	
	private <T> List<T> toList(Iterable<T> iterable){
		List<T> resultList = new ArrayList<T>();
		for (T t : iterable) {
			resultList.add(t);
		}
		return resultList;
	}
	
	
}
