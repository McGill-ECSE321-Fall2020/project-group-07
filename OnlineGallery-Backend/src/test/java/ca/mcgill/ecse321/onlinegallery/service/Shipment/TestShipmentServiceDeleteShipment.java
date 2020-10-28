package ca.mcgill.ecse321.onlinegallery.service.Shipment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.lenient;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import ca.mcgill.ecse321.onlinegallery.dao.PurchaseRepository;
import ca.mcgill.ecse321.onlinegallery.dao.ShipmentRepository;
import ca.mcgill.ecse321.onlinegallery.dto.PurchaseDto;
import ca.mcgill.ecse321.onlinegallery.dto.ShipmentDto;
import ca.mcgill.ecse321.onlinegallery.model.Artwork;
import ca.mcgill.ecse321.onlinegallery.model.ArtworkStatus;
import ca.mcgill.ecse321.onlinegallery.model.Purchase;
import ca.mcgill.ecse321.onlinegallery.model.Shipment;
import ca.mcgill.ecse321.onlinegallery.model.ShipmentType;
import ca.mcgill.ecse321.onlinegallery.service.PurchaseService;
import ca.mcgill.ecse321.onlinegallery.service.ShipmentService;
import ca.mcgill.ecse321.onlinegallery.service.exception.PurchaseException;
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
	private static final String VALID_SOURCE_ADDRESS = "3415 girouard, montreal, QC";
	private static final String VALID_DESTINATION_ADDRESS = "1000 sherbrooke, toronto, ON";
	private static final double VALID_SHIPPING_COST = 34.5;
	private static final double VALID_TOTAL_COST = 1034.9;
	private static final String VALID_RECIPIENT_STRING = "Natalia Tabet";
	private static final String VALID_USER_NAME = "natis5005";
	
	private static final long VALID_PURCHASE_ID1 = (long)3;
	private static final long VALID_PURCHASE_ID2 = (long)4;
	
	
	@BeforeEach
	public void setMockOutput() {
		lenient().when(shipRepo.findShipmentByShipmentId(anyLong())).thenAnswer((InvocationOnMock invocation)->{
			if (invocation.getArgument(0).equals(VALID_SHIPMENT_ID)) {
				Shipment s = new Shipment();
				s.setShipmentId(VALID_SHIPMENT_ID);
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
				return p1;
				
				
			}
			if (invocation.getArgument(0).equals(VALID_PURCHASE_ID2)) {
			
				Purchase p2 = new Purchase();
				p2.setPurchaseId(VALID_PURCHASE_ID2);
				Shipment s = new Shipment();
				s.setShipmentId(VALID_SHIPMENT_ID);
				p2.setShipment(s);
				return p2;
				
				
			}
			
			return null;
			
		});
		
		lenient().doAnswer((i)->null).when(shipRepo).delete(any(Shipment.class));
	}
	
	@Test
	public void testValidDelete() {
		
		
		ShipmentDto sTO = new ShipmentDto(VALID_SHIPMENT_ID, VALID_SOURCE_ADDRESS, VALID_DESTINATION_ADDRESS, VALID_SHIPPING_COST,VALID_TOTAL_COST, VALID_RECIPIENT_STRING, VALID_USER_NAME);

		
		PurchaseDto pTO1 = new PurchaseDto();
		PurchaseDto pTO2 = new PurchaseDto();
		
		pTO1.setPurchaseId(VALID_PURCHASE_ID1);
		pTO2.setPurchaseId(VALID_PURCHASE_ID2);
		
		pTO1.setShipmentType(ShipmentType.OFFSITE_DELIVERY);
		pTO2.setShipmentType(ShipmentType.OFFSITE_DELIVERY);
		
		pTO1.setUsername(VALID_USER_NAME);
		pTO2.setUsername(VALID_USER_NAME);
	
		sTO.addPurchase(pTO1);
		sTO.addPurchase(pTO2);
		
		Shipment s = null;

		try {
			s = service.deleteShipment(sTO);
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
		ShipmentDto sTO = new ShipmentDto(INVALID_SHIPMENT_ID, VALID_SOURCE_ADDRESS, VALID_DESTINATION_ADDRESS, VALID_SHIPPING_COST,VALID_TOTAL_COST, VALID_RECIPIENT_STRING, VALID_USER_NAME);

		
		PurchaseDto pTO1 = new PurchaseDto();
		PurchaseDto pTO2 = new PurchaseDto();
		
		pTO1.setPurchaseId(VALID_PURCHASE_ID1);
		pTO2.setPurchaseId(VALID_PURCHASE_ID2);
		
		pTO1.setShipmentType(ShipmentType.OFFSITE_DELIVERY);
		pTO2.setShipmentType(ShipmentType.OFFSITE_DELIVERY);
		
		pTO1.setUsername(VALID_USER_NAME);
		pTO2.setUsername(VALID_USER_NAME);
	
		sTO.addPurchase(pTO1);
		sTO.addPurchase(pTO2);

		try {
			s = service.deleteShipment(sTO);
		}
		catch(ShipmentException e) {
			error=e.getMessage();
			
		}
		
		
		assertNull(s);
		assertEquals(error,"No shipment exists with id " + INVALID_SHIPMENT_ID);
		
		
	}
	
	
}
