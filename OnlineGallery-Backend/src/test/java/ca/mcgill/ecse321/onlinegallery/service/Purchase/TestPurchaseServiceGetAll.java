package ca.mcgill.ecse321.onlinegallery.service.Purchase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.lenient;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;

import ca.mcgill.ecse321.onlinegallery.dao.*;
import ca.mcgill.ecse321.onlinegallery.model.*;
import ca.mcgill.ecse321.onlinegallery.service.PurchaseService;
import ca.mcgill.ecse321.onlinegallery.service.exception.*;

@ExtendWith(MockitoExtension.class)
public class TestPurchaseServiceGetAll {

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

	private static final Long PID1= (long) 1;
	private static final Long PID2= (long) 2;

	
	@BeforeEach
	public void setMockOutput() {
 
		lenient().when(purchaseRepo.count()).thenReturn((long) 2);
		lenient().when(purchaseRepo.findAll()).thenAnswer((InvocationOnMock invocation)->{
			Purchase p1 = new Purchase();
			p1.setPurchaseId(PID1);
			
			Purchase p2 = new Purchase();
			p2.setPurchaseId(PID2);		

			
			Set<Purchase> allP = new HashSet<Purchase>();
			
			allP.add(p1);
			allP.add(p2);
			
			return allP;
		});
	}

	@Test
	public void testGetAllPurchasesNonEmpty() { 

		List<Purchase> allP = null;
		try {
			allP=service.getAllPurchases();
		} catch (PurchaseException e) {
			fail();
		}
		assertNotNull(allP);
		assertEquals(allP.size(),2);
		
		List<Long> expectedPIds=new ArrayList<Long>(List.of((long) 1, (long) 2));
		
		for (Purchase eachP:allP) {
			assertEquals(true,expectedPIds.contains(eachP.getPurchaseId()));
		}		
	} 
}