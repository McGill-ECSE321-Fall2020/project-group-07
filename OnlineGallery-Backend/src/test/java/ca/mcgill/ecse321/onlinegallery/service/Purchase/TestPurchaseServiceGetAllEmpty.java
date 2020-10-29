package ca.mcgill.ecse321.onlinegallery.service.Purchase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import ca.mcgill.ecse321.onlinegallery.dao.*;
import ca.mcgill.ecse321.onlinegallery.dto.*;
import ca.mcgill.ecse321.onlinegallery.model.*;
import ca.mcgill.ecse321.onlinegallery.service.GalleryRegistrationService;
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

	private static final Long PID1= (long) 1;
	private static final Long PID2= (long) 2;

	
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
