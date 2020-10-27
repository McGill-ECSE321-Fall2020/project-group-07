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
import ca.mcgill.ecse321.onlinegallery.dao.ShipmentRepository;
import ca.mcgill.ecse321.onlinegallery.model.Purchase;
import ca.mcgill.ecse321.onlinegallery.service.exception.ApplicationException;

@Service
public class ApplicationService {

	@Autowired
	PurchaseRepository purchaseRepo;
	
	@Autowired
	ArtworkRepository artworkRepo;
	
	@Transactional
	private Map<Long, SoldArtworksSummaryEntry> generateSummary () throws ApplicationException{
		if (toList(purchaseRepo.findAll()).size() == 0) {
			throw new ApplicationException ("no purchase exist on the system");
		}
		
		Map<Long, SoldArtworksSummaryEntry> summaryList = new HashMap <Long, SoldArtworksSummaryEntry> ();
		for (Purchase p : toList(purchaseRepo.findAll())) {
			long artworkId = p.getArtwork().getArtworkId();
			String name = p.getArtwork().getName();
			double price = p.getArtwork().getPrice();
			double commission = p.getArtwork().getComission();
			Date purshaseDate = p.getDate();
			SoldArtworksSummaryEntry aSummary = new SoldArtworksSummaryEntry(name, price, commission, purshaseDate);
			summaryList.put(artworkId, aSummary);
			
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
