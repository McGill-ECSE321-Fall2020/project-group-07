package ca.mcgill.ecse321.onlinegallery.service.Application;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.anyLong;
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
import org.springframework.beans.factory.annotation.Autowired;

import ca.mcgill.ecse321.onlinegallery.dao.ArtworkRepository;
import ca.mcgill.ecse321.onlinegallery.dao.PurchaseRepository;
import ca.mcgill.ecse321.onlinegallery.service.ApplicationService;
import ca.mcgill.ecse321.onlinegallery.service.SoldArtworksSummaryEntry;
import ca.mcgill.ecse321.onlinegallery.service.exception.ApplicationException;
import ca.mcgill.ecse321.onlinegallery.model.*;

public class TestApplicationServiceGenerateSummary {
	
	@Mock
	private PurchaseRepository purchaseRepo;
	
	@Mock
	private ArtworkRepository artworkRepo;
	
	@InjectMocks
	private ApplicationService service;
	
	//purchase values
	private static final Date VALID_PURCHASE_DATE = Date.valueOf("2015-03-31");
	private static final Long VALID_ARTWORK_ID = (long)2;
	private static final String VALID_ARTWORK_NAME = "Guernica";
	private static final double VALID_ARTWORK_PRICE = 2000.5;
	private static final double VALID_ARTWORK_COMMISSION = 0.15;
	
	
	//artwork values
	private static final Long VALID_PURCHASE_ID = (long)1;
	
	@BeforeEach
	public void setMockOutput() {
		lenient().when(purchaseRepo.findAll()).thenAnswer((InvocationOnMock i) -> {
			Set<Purchase> purchaseSet = new HashSet<Purchase>();
			Artwork art = new Artwork();
			art.setArtworkId(VALID_ARTWORK_ID);
			art.setName(VALID_ARTWORK_NAME);
			art.setPrice(VALID_ARTWORK_PRICE);
			art.setCommission(VALID_ARTWORK_COMMISSION);
			
			
			Purchase p = new Purchase();
			p.setShipmentType(ShipmentType.ONSITE_PICKUP);
			p.setPurchaseId(VALID_PURCHASE_ID);
			p.setDate(VALID_PURCHASE_DATE);
			p.setArtwork(art);
			purchaseSet.add(p);
			
			return null;
		});
	}
	
	@Test
	public void testGenerateSummarySuccessfully() {
		Map<Long, SoldArtworksSummaryEntry> summaryList = null;
		try {
			summaryList = service.generateSummary();
		}
		catch (ApplicationException e) {
			fail ();
		}
	}

}
