package ca.mcgill.ecse321.onlinegallery.service.Shipment;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;

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

import ca.mcgill.ecse321.onlinegallery.dao.PurchaseRepository;
import ca.mcgill.ecse321.onlinegallery.dao.ShipmentRepository;
import ca.mcgill.ecse321.onlinegallery.model.Purchase;
import ca.mcgill.ecse321.onlinegallery.model.Shipment;
import ca.mcgill.ecse321.onlinegallery.service.ShipmentService;
import ca.mcgill.ecse321.onlinegallery.service.exception.ShipmentException;

@ExtendWith(MockitoExtension.class)
public class TestShipmentServiceDeleteAllShipments {

	@Mock
	private ShipmentRepository shipRepo; 
	
	@Mock
	private PurchaseRepository purchaseRepo;
	
	@InjectMocks
	private ShipmentService service;
	
	private static final long VALID_SHIPMENT_ID1 = (long)1;
	private static final long VALID_SHIPMENT_ID2 = (long)7;
	
	private static final long VALID_PURCHASE_ID1 = (long)3;
	private static final long VALID_PURCHASE_ID2 = (long)4;
	
	private static final long VALID_PURCHASE_ID3 = (long)5;
	private static final long VALID_PURCHASE_ID4 = (long)6;
	
	
	@BeforeEach
	public void setMockOutput() {
		
		lenient().when(shipRepo.count()).thenReturn((long) 2);
		
		lenient().when(shipRepo.findShipmentByShipmentId(anyLong()))
		.thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(VALID_SHIPMENT_ID1)) {
				Shipment s1 = new Shipment();
				s1.setShipmentId(VALID_SHIPMENT_ID1);
				Purchase p1 = new Purchase();
				Purchase p2 = new Purchase();
				p1.setPurchaseId(VALID_PURCHASE_ID1);
				p2.setPurchaseId(VALID_PURCHASE_ID2);
				s1.getPurchase().add(p1);
				s1.getPurchase().add(p2);

				return s1;
			}
			if (invocation.getArgument(0).equals(VALID_SHIPMENT_ID2)) {
				Shipment s2 = new Shipment();
				s2.setShipmentId(VALID_SHIPMENT_ID2);
				Purchase p3 = new Purchase();
				Purchase p4 = new Purchase();
				p3.setPurchaseId(VALID_PURCHASE_ID3);
				p4.setPurchaseId(VALID_PURCHASE_ID4);
				s2.getPurchase().add(p3);
				s2.getPurchase().add(p4);

				return s2;
			}

			return null;
		});
		
		lenient().when(purchaseRepo.findPurchaseByPurchaseId(anyLong())).thenAnswer((InvocationOnMock invocation)->{
			if (invocation.getArgument(0).equals(VALID_PURCHASE_ID1)) {
				Purchase p1 = new Purchase();
				p1.setPurchaseId(VALID_PURCHASE_ID1);
				Shipment s = new Shipment();
				s.setShipmentId(VALID_SHIPMENT_ID1);
				p1.setShipment(s);
				s.getPurchase().add(p1);
				return p1;
				
				
			}
			if (invocation.getArgument(0).equals(VALID_PURCHASE_ID2)) {
			
				Purchase p2 = new Purchase();
				p2.setPurchaseId(VALID_PURCHASE_ID2);
				Shipment s = new Shipment();
				s.setShipmentId(VALID_SHIPMENT_ID1);
				p2.setShipment(s);
				s.getPurchase().add(p2);
				return p2;
				
				
			}
			if (invocation.getArgument(0).equals(VALID_PURCHASE_ID3)) {
				
				Purchase p3 = new Purchase();
				p3.setPurchaseId(VALID_PURCHASE_ID3);
				Shipment s = new Shipment();
				s.setShipmentId(VALID_SHIPMENT_ID2);
				p3.setShipment(s);
				s.getPurchase().add(p3);
				return p3;
				
				
			}
			if (invocation.getArgument(0).equals(VALID_PURCHASE_ID4)) {
				
				Purchase p4 = new Purchase();
				p4.setPurchaseId(VALID_PURCHASE_ID4);
				Shipment s = new Shipment();
				s.setShipmentId(VALID_SHIPMENT_ID2);
				p4.setShipment(s);
				s.getPurchase().add(p4);
				return p4;
				
				
			}
			else {
				System.out.println("entering else with " + invocation.getArgument(0));
				return null;
			}
			
			
			
		});
		lenient().when(shipRepo.findAll()).thenAnswer((InvocationOnMock invocation) -> {
			Shipment s1 = new Shipment();
			s1.setShipmentId(VALID_SHIPMENT_ID1);
			Purchase p1 = new Purchase();
			Purchase p2 = new Purchase();
			p1.setPurchaseId(VALID_PURCHASE_ID1);
			p2.setPurchaseId(VALID_PURCHASE_ID2);
			s1.getPurchase().add(p1);
			s1.getPurchase().add(p2);
			
			Shipment s2 = new Shipment();
			s2.setShipmentId(VALID_SHIPMENT_ID2);
			Purchase p3 = new Purchase();
			Purchase p4 = new Purchase();
			p3.setPurchaseId(VALID_PURCHASE_ID3);
			p4.setPurchaseId(VALID_PURCHASE_ID4);
			s2.getPurchase().add(p3);
			s2.getPurchase().add(p4);
			
			Set<Shipment> allShip = new HashSet<Shipment>();
			
			allShip.add(s1);
			allShip.add(s2);
			
			return allShip;
		});
		
		lenient().doAnswer((i)->null).when(shipRepo).delete(any(Shipment.class));
	}
	
	@Test
	public void testDeleteAllTwoShipments() {

		List<Shipment> allShip = null;
		try {
			allShip = service.deleteAllShipments();
		} catch (ShipmentException e) {
			fail();
		}
		assertNotNull(allShip);
	}
	
}
