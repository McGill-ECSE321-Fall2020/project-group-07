package ca.mcgill.ecse321.onlinegallery.service.Shipment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;

import ca.mcgill.ecse321.onlinegallery.dao.PurchaseRepository;
import ca.mcgill.ecse321.onlinegallery.dao.ShipmentRepository;
import ca.mcgill.ecse321.onlinegallery.dto.PurchaseDto;
import ca.mcgill.ecse321.onlinegallery.dto.ShipmentDto;
import ca.mcgill.ecse321.onlinegallery.model.GalleryRegistration;
import ca.mcgill.ecse321.onlinegallery.model.Purchase;
import ca.mcgill.ecse321.onlinegallery.model.Shipment;
import ca.mcgill.ecse321.onlinegallery.model.ShipmentType;
import ca.mcgill.ecse321.onlinegallery.service.ShipmentService;
import ca.mcgill.ecse321.onlinegallery.service.exception.ShipmentException;

@ExtendWith(MockitoExtension.class)
public class TestShipmentServiceDeleteShipment {

	@Mock
	ShipmentRepository shipRepo; 
	
	@Mock
	PurchaseRepository purchaseRepo;
	
	@InjectMocks
	private ShipmentService service;
	
	private static final long VALID_SHIPMENT_ID = (long)1;
	private static final long INVALID_SHIPMENT_ID = (long)2;
	
	private static final long VALID_PURCHASE_ID1 = (long)3;
	private static final long VALID_PURCHASE_ID2 = (long)4;
	
	
	@BeforeEach
	public void setMockOutput() {
		lenient().when(shipRepo.findShipmentByShipmentId(anyLong()))
		.thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(VALID_SHIPMENT_ID)) {

				Shipment s = new Shipment();
				s.setShipmentId(VALID_SHIPMENT_ID);
				Purchase p1 = new Purchase();
				Purchase p2 = new Purchase();
				p1.setPurchaseId(VALID_PURCHASE_ID1);
				p2.setPurchaseId(VALID_PURCHASE_ID2);
				s.getPurchase().add(p1);
				s.getPurchase().add(p2);

				return s;
			}

			return null;
		});
		
		lenient().when(purchaseRepo.findPurchaseByPurchaseId(anyLong())).thenAnswer((InvocationOnMock invocation)->{
			if (invocation.getArgument(0).equals(VALID_PURCHASE_ID1)) {
				Purchase p1 = new Purchase();
				p1.setPurchaseId(VALID_PURCHASE_ID1);
				Shipment s = new Shipment();
				s.setShipmentId(VALID_SHIPMENT_ID);
				p1.setShipment(s);
				s.getPurchase().add(p1);
				return p1;
				
				
			}
			if (invocation.getArgument(0).equals(VALID_PURCHASE_ID2)) {
			
				Purchase p2 = new Purchase();
				p2.setPurchaseId(VALID_PURCHASE_ID2);
				Shipment s = new Shipment();
				s.setShipmentId(VALID_SHIPMENT_ID);
				p2.setShipment(s);
				s.getPurchase().add(p2);
				return p2;
				
				
			}
			else {
				System.out.println("entering else with " + invocation.getArgument(0));
				return null;
			}
			
			
			
		});
		
		lenient().doAnswer((i)->null).when(shipRepo).delete(any(Shipment.class));
	}
	
	@Test
	public void testValidDelete() {
		
		Shipment s = null;
		
		try {
			s = service.deleteShipment(VALID_SHIPMENT_ID);
		}
		catch(ShipmentException e) {
			fail();
			
		}
		
		assertNotNull(s);
		
		
	}
	
	@Test
	public void testInvalidDeleteShipmentIdNotFound() {
		String error = null;
		Shipment s = null ;
		

		try {
			s = service.deleteShipment(INVALID_SHIPMENT_ID);
		}
		catch(ShipmentException e) {
			error=e.getMessage();
			
		}
		
		assertNull(s);
		assertEquals(error,"No shipment exists with id " + INVALID_SHIPMENT_ID);
		
		
	}
	
	
}
