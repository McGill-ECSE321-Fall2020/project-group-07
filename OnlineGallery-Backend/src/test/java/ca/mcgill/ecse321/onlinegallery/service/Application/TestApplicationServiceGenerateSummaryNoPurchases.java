package ca.mcgill.ecse321.onlinegallery.service.Application;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.lenient;

import java.sql.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;

import ca.mcgill.ecse321.onlinegallery.dao.ArtworkRepository;
import ca.mcgill.ecse321.onlinegallery.dao.PurchaseRepository;
import ca.mcgill.ecse321.onlinegallery.model.Artwork;
import ca.mcgill.ecse321.onlinegallery.model.Purchase;
import ca.mcgill.ecse321.onlinegallery.model.ShipmentType;
import ca.mcgill.ecse321.onlinegallery.service.ApplicationService;
import ca.mcgill.ecse321.onlinegallery.service.SoldArtworksSummaryEntry;
import ca.mcgill.ecse321.onlinegallery.service.exception.ApplicationException;

public class TestApplicationServiceGenerateSummaryNoPurchases {
	@Mock
	PurchaseRepository purchaseRepo;
	
	@Mock
	ArtworkRepository artworkRepo;
	
	@InjectMocks
	private ApplicationService service;
	

	
	@BeforeEach
	public void setMockOutput() {
		lenient().when(purchaseRepo.findAll()).thenAnswer((InvocationOnMock i) -> {
			Set<Purchase> purchaseSet = new HashSet<Purchase>();
			
			
			return null;
		});
	}
	
	@Test
	public void testGenerateSummaryWithNoArtworksSold() {
		Map<Long, SoldArtworksSummaryEntry> summaryList = null;
		try {
			summaryList = service.generateSummary();
		}
		catch (ApplicationException e) {
			fail ();
		}
	}
}
