package ca.mcgill.ecse321.onlinegallery.service.Application;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;

import ca.mcgill.ecse321.onlinegallery.dao.ArtworkRepository;
import ca.mcgill.ecse321.onlinegallery.dao.PurchaseRepository;
import ca.mcgill.ecse321.onlinegallery.service.ApplicationService;

public class TestApplicationServiceGenerateSummary {
	
	@Mock
	PurchaseRepository purchaseRepo;
	
	@Mock
	ArtworkRepository artworkRepo;
	
	@InjectMocks
	private ApplicationService service;

}
