package ca.mcgill.ecse321.onlinegallery.service.Purchase;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.lenient;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ca.mcgill.ecse321.onlinegallery.dao.*;
import ca.mcgill.ecse321.onlinegallery.model.*;
import ca.mcgill.ecse321.onlinegallery.service.PurchaseService;
import ca.mcgill.ecse321.onlinegallery.service.exception.*;

@ExtendWith(MockitoExtension.class)
public class TestPurchaseServiceGetAllEmpty {

	@Mock
	private GalleryRegistrationRepository regRepo;
	
	@Mock
	private PurchaseRepository purchaseRepo;
	
	@Mock
	private CustomerRepository custRepo;
	
	@Mock
	private ArtworkRepository artRepo;
	
	@InjectMocks
	private PurchaseService service;
	
	@BeforeEach
	public void setMockOutput() {
 
		lenient().when(purchaseRepo.count()).thenReturn((long) 0);
	
	}

	@Test
	public void testGetAllPurchasesNonEmpty() { 

		List<Purchase> allP = null;
		String error=null;
		try {
			allP=service.getAllPurchases();
		} catch (PurchaseException e) {
			error=e.getMessage();
		}
		assertNull(allP);
		assertEquals(error,"no Purchase in system");
	} 

}
