package ca.mcgill.ecse321.onlinegallery.service.Shipment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.lenient;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import ca.mcgill.ecse321.onlinegallery.dao.*;
import ca.mcgill.ecse321.onlinegallery.model.*;
import ca.mcgill.ecse321.onlinegallery.service.*;
import ca.mcgill.ecse321.onlinegallery.service.exception.*;

@ExtendWith(MockitoExtension.class)
public class TestShipmentServiceAddToShipment {

	@Mock
	private ShipmentRepository shipRepo;

	@Mock
	private PurchaseRepository purchaseRepo;

	@InjectMocks
	private ShipmentService service;

	private static final Long VALID_SHIPID_EXISTPURCHASE_DELIVERY = (long) 0;
	private static final Long VALID_SHIPID_EXISTPURCHASE_PICKUP = (long) 1;
	private static final Long VALID_SHIPID_NOPURCHASE = (long) 2;
	private static final Long INVALID_SHIPID_NONEXIST = (long) 3;

	private static final Long EXISTING_PURCHASEID_DELIVERY = (long) 10;
	private static final Long EXISTING_PURCHASEID_PICKUP = (long) 11;
	
	private static final Long VALID_NEW_PURCHASEID_PICKUP = (long) 12;
	private static final Long VALID_NEW_PURCHASEID_DELIVERY = (long) 13;
	private static final Long INVALID_NEW_PURCHASEID_NONEXIST = (long) 14;
	private static final Long INVALID_NEW_PURCHASEID_DIFFERENT_CUSTOMER = (long) 15;


	@BeforeEach
	public void setMockOutput() {

		lenient().when(purchaseRepo.existsById(anyLong())).thenAnswer((InvocationOnMock i) -> {
			if (i.getArgument(0).equals(INVALID_NEW_PURCHASEID_NONEXIST)) {
				return false;
			} else {
				return true;
			}
		});

		lenient().when(shipRepo.existsById(anyLong())).thenAnswer((InvocationOnMock i) -> {
			if (i.getArgument(0).equals(INVALID_SHIPID_NONEXIST)) {
				return false;
			} else {
				return true;
			}
		});

		lenient().when(purchaseRepo.findPurchaseByPurchaseId(anyLong())).thenAnswer((InvocationOnMock i) -> {
			if (i.getArgument(0).equals(EXISTING_PURCHASEID_DELIVERY)) {
				Purchase p = new Purchase();
				p.setPurchaseId(EXISTING_PURCHASEID_DELIVERY);
				p.setShipmentType(ShipmentType.OFFSITE_DELIVERY);
				
				Customer c = new Customer();
				c.setCustomerId((long) 0);
				p.setCustomer(c);
				
				return p;
			}

			if (i.getArgument(0).equals(EXISTING_PURCHASEID_PICKUP)) {
				Purchase p = new Purchase();
				p.setPurchaseId(EXISTING_PURCHASEID_PICKUP);
				p.setShipmentType(ShipmentType.ONSITE_PICKUP);
				
				Customer c = new Customer();
				c.setCustomerId((long) 0);
				p.setCustomer(c);
				
				return p;
			}
			
			if (i.getArgument(0).equals(VALID_NEW_PURCHASEID_PICKUP)) {
				Purchase p = new Purchase();
				p.setPurchaseId(VALID_NEW_PURCHASEID_PICKUP);
				p.setShipmentType(ShipmentType.ONSITE_PICKUP);
				
				Customer c = new Customer();
				c.setCustomerId((long) 0);
				p.setCustomer(c);
				
				return p;
			}

			if (i.getArgument(0).equals(VALID_NEW_PURCHASEID_DELIVERY)) {
				Purchase p = new Purchase();
				p.setPurchaseId(VALID_NEW_PURCHASEID_DELIVERY);
				p.setShipmentType(ShipmentType.OFFSITE_DELIVERY);
				
				Customer c = new Customer();
				c.setCustomerId((long) 0);
				p.setCustomer(c);
				
				return p;
			}
			
			if (i.getArgument(0).equals(INVALID_NEW_PURCHASEID_DIFFERENT_CUSTOMER)) {
				Purchase p = new Purchase();
				p.setPurchaseId(VALID_NEW_PURCHASEID_DELIVERY);
				p.setShipmentType(ShipmentType.OFFSITE_DELIVERY);
				
				Customer c = new Customer();
				c.setCustomerId((long) 3);
				p.setCustomer(c);
				
				return p;
			}

			return null;
		});

		lenient().when(shipRepo.findShipmentByShipmentId(anyLong())).thenAnswer((InvocationOnMock i) -> {
			if (i.getArgument(0).equals(VALID_SHIPID_EXISTPURCHASE_DELIVERY)) {
				Purchase p = new Purchase();
				p.setShipmentType(ShipmentType.OFFSITE_DELIVERY);
				p.setPurchaseId(EXISTING_PURCHASEID_DELIVERY);

				Shipment s = new Shipment();
				Customer c = new Customer();
				c.setCustomerId((long) 0);
				p.setCustomer(c);
				c.getPurchase().add(p);
				
				s.getPurchase().add(p);

				
				return s;
			}

			if (i.getArgument(0).equals(VALID_SHIPID_EXISTPURCHASE_PICKUP)) {
				Purchase p = new Purchase();
				p.setShipmentType(ShipmentType.ONSITE_PICKUP);
				p.setPurchaseId(EXISTING_PURCHASEID_PICKUP);
				
				Shipment s = new Shipment();
				
				Customer c = new Customer();
				c.setCustomerId((long) 0);
				p.setCustomer(c);
				c.getPurchase().add(p);

				s.getPurchase().add(p);

				return s;
			}

			if (i.getArgument(0).equals(VALID_SHIPID_NOPURCHASE)) {

				Shipment s = new Shipment();
				return s;
			}

			return null;
		});

		lenient().when(shipRepo.save(any(Shipment.class))).thenAnswer((InvocationOnMock i) -> {
			return i.getArgument(0);
		});

	}
	
	@Test
	public void testAddToShipmentValidShipIdDeliveryValidPurchaseIdDelivery() {
		Shipment s = null;
		try {
			s=service.addToShipment(VALID_SHIPID_EXISTPURCHASE_DELIVERY, VALID_NEW_PURCHASEID_DELIVERY);
		}
		catch(ShipmentException | PurchaseException e) {
			fail();
		}
		
		assertNotNull(s);
		
		boolean newPurchaseInShipment=false;
		
		for (Purchase p:s.getPurchase()) {
			if (p.getPurchaseId().equals(VALID_NEW_PURCHASEID_DELIVERY)) {
				newPurchaseInShipment=true;
			}
		}
		
		assertEquals(true,newPurchaseInShipment);
	}
	
	@Test
	public void testAddToShipmentValidShipIdPickupValidPurchaseIdPickup() {
		Shipment s = null;
		try {
			s=service.addToShipment(VALID_SHIPID_EXISTPURCHASE_PICKUP, VALID_NEW_PURCHASEID_PICKUP);
		}
		catch(ShipmentException | PurchaseException e) {
			fail();
		}
		
		assertNotNull(s);
		
		boolean newPurchaseInShipment=false;
		
		for (Purchase p:s.getPurchase()) {
			if (p.getPurchaseId().equals(VALID_NEW_PURCHASEID_PICKUP)) {
				newPurchaseInShipment=true;
			}
		}
		
		assertEquals(true,newPurchaseInShipment);
	}
	
	@Test
	public void testAddToShipmentValidShipIdNoPurchaseValidPurchaseIdPickup() {
		Shipment s = null;
		try {
			s=service.addToShipment(VALID_SHIPID_NOPURCHASE, VALID_NEW_PURCHASEID_PICKUP);
		}
		catch(ShipmentException | PurchaseException e) {
			fail();
		}
		
		assertNotNull(s);
		
		boolean newPurchaseInShipment=false;
		
		for (Purchase p:s.getPurchase()) {
			if (p.getPurchaseId().equals(VALID_NEW_PURCHASEID_PICKUP)) {
				newPurchaseInShipment=true;
			}
		}
		
		assertEquals(true,newPurchaseInShipment);
	}
	
	@Test
	public void testAddToShipmentValidShipIdNoPurchaseValidPurchaseIdDelivery() {
		Shipment s = null;
		try {
			s=service.addToShipment(VALID_SHIPID_NOPURCHASE, VALID_NEW_PURCHASEID_DELIVERY);
		}
		catch(ShipmentException | PurchaseException e) {
			fail();
		}
		
		assertNotNull(s);
		
		boolean newPurchaseInShipment=false;
		
		for (Purchase p:s.getPurchase()) {
			if (p.getPurchaseId().equals(VALID_NEW_PURCHASEID_DELIVERY)) {
				newPurchaseInShipment=true;
			}
		}
		
		assertEquals(true,newPurchaseInShipment);
	}
	
	@Test
	public void testAddToShipmentValidShipIdDeliveryInvalidPurchaseDifferentCustomer() {
		Shipment s = null;
		String error=null;
		try {
			s=service.addToShipment(VALID_SHIPID_EXISTPURCHASE_DELIVERY, INVALID_NEW_PURCHASEID_DIFFERENT_CUSTOMER);
		}
		catch(ShipmentException | PurchaseException e) {
			error=e.getMessage();
		}
		
		assertNull(s);
		assertEquals(error,"cannot add a Purchase to a Shipment owned by another Customer");
	}
	
	@Test
	public void testAddToShipmentInValidShipIdNonExist() {
		Shipment s = null;
		String error=null;
		try {
			s=service.addToShipment(INVALID_SHIPID_NONEXIST, VALID_NEW_PURCHASEID_PICKUP);
		}
		catch(ShipmentException | PurchaseException e) {
			error=e.getMessage();
		}
		
		assertNull(s);
		assertEquals(error,"no Shipment with id ["+INVALID_SHIPID_NONEXIST+"] found");
	}
	
	@Test
	public void testAddToShipmentValidShipIdPickupInvalidPurchaseIdNonExist() {
		Shipment s = null;
		String error=null;
		try {
			s=service.addToShipment(VALID_SHIPID_EXISTPURCHASE_PICKUP, INVALID_NEW_PURCHASEID_NONEXIST);
		}
		catch(ShipmentException | PurchaseException e) {
			error=e.getMessage();
		}
		
		assertNull(s);
		assertEquals(error,"no Purchase with id ["+INVALID_NEW_PURCHASEID_NONEXIST+"] found");
	}

	@Test
	public void testAddToShipmentValidShipIdPickupValidPurchaseIdDelivery() {
		Shipment s = null;
		String error=null;
		try {
			s=service.addToShipment(VALID_SHIPID_EXISTPURCHASE_PICKUP, VALID_NEW_PURCHASEID_DELIVERY);
		}
		catch(ShipmentException | PurchaseException e) {
			error=e.getMessage();
		}
		
		assertNull(s);
		assertEquals(error,"incompatible shipment types between existing Purchases in Shipment and Shipment to add");
	}
	
	@Test
	public void testAddToShipmentValidShipIdDeliveryValidPurchaseIdPIckup() {
		Shipment s = null;
		String error=null;
		try {
			s=service.addToShipment(VALID_SHIPID_EXISTPURCHASE_DELIVERY, VALID_NEW_PURCHASEID_PICKUP);
		}
		catch(ShipmentException | PurchaseException e) {
			error=e.getMessage();
		}
		
		assertNull(s);
		assertEquals(error,"incompatible shipment types between existing Purchases in Shipment and Shipment to add");
	}

}