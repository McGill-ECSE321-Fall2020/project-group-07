package ca.mcgill.ecse321.onlinegallery.service.Application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.lenient;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ca.mcgill.ecse321.onlinegallery.dao.ArtworkRepository;
import ca.mcgill.ecse321.onlinegallery.dao.PurchaseRepository;
import ca.mcgill.ecse321.onlinegallery.service.ApplicationService;
import ca.mcgill.ecse321.onlinegallery.service.SoldArtworksSummaryEntry;
import ca.mcgill.ecse321.onlinegallery.service.exception.ApplicationException;

@ExtendWith(MockitoExtension.class)
public class TestApplicationServiceGenerateSummaryNoPurchases {
	@Mock
	PurchaseRepository purchaseRepo;
	
	@Mock
	ArtworkRepository artworkRepo;
	
	@InjectMocks
	private ApplicationService service;
	

	
	@BeforeEach
	public void setMockOutput() {
		lenient().when(purchaseRepo.count()).thenReturn((long) 0);
	}
	
	@Test
	public void testGenerateSummaryWithNoArtworksSold() {
		Map<Long, SoldArtworksSummaryEntry> summaryList = null;
		String error = "";
		try {
			summaryList = service.generateSummary();
		}
		catch (ApplicationException e) {
			error=e.getMessage();
		}
		assertNull(summaryList);
		assertEquals(error,"no Purchase found in system");
	}
}
