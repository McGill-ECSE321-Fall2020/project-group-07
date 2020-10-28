package ca.mcgill.ecse321.onlinegallery.service.Shipment;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import ca.mcgill.ecse321.onlinegallery.dao.ArtworkRepository;
import ca.mcgill.ecse321.onlinegallery.dao.GalleryRegistrationRepository;
import ca.mcgill.ecse321.onlinegallery.dao.PurchaseRepository;
import ca.mcgill.ecse321.onlinegallery.dao.ShipmentRepository;
import ca.mcgill.ecse321.onlinegallery.dto.PurchaseDto;
import ca.mcgill.ecse321.onlinegallery.dto.ShipmentDto;
import ca.mcgill.ecse321.onlinegallery.model.Artwork;
import ca.mcgill.ecse321.onlinegallery.model.ArtworkStatus;
import ca.mcgill.ecse321.onlinegallery.model.Customer;
import ca.mcgill.ecse321.onlinegallery.model.GalleryRegistration;
import ca.mcgill.ecse321.onlinegallery.model.Purchase;
import ca.mcgill.ecse321.onlinegallery.model.Shipment;
import ca.mcgill.ecse321.onlinegallery.model.ShipmentStatus;
import ca.mcgill.ecse321.onlinegallery.model.ShipmentType;
import ca.mcgill.ecse321.onlinegallery.service.ShipmentService;
import ca.mcgill.ecse321.onlinegallery.service.exception.PurchaseException;
import ca.mcgill.ecse321.onlinegallery.service.exception.ShipmentException;
import javassist.bytecode.Descriptor.Iterator;




@ExtendWith(MockitoExtension.class)
public class TestShipmentServiceCreateShipment {

	@Mock
	private ShipmentRepository shipRepo; 
	
	@Mock
	private PurchaseRepository purchaseRepo;
	
	@Mock
	private GalleryRegistrationRepository regRepo;
	
	@Mock
	private ArtworkRepository artworkRepo;
	
	@InjectMocks
	private ShipmentService service;
	
	private static final long VALID_SHIPMENT_ID = (long)1;
	private static final String VALID_SOURCE_ADDRESS = "3415 girouard, montreal, QC";
	private static final String VALID_DESTINATION_ADDRESS = "1000 sherbrooke, toronto, ON";
	private static final double VALID_SHIPPING_COST = 34.5;
	private static final double VALID_TOTAL_COST = 1034.9;
	private static final String VALID_RECIPIENT_STRING = "Natalia Tabet";
	private static final String VALID_USER_NAME = "natis5005";
	private static final String INVALID_SOURCE_ADDRESS = "3415 girouard, montreal";
	private static final String INVALID_DESTINATION_ADDRESS = "1000 sherbrooke, ON";

	private static final long VALID_ARTWORK_ID1 = (long)2;
	private static final long VALID_ARTWORK_ID2 = (long)22;
	private static final long INVALID_ARTWORK_ID_STILLAVAILABLE = (long)10;
	private static final double VALID_ARTWORK_PRICE = 500.2;
	
	
	private static final long VALID_PURCHASE_ID1 = (long)3;
	private static final long VALID_PURCHASE_ID2 = (long)33;
	private static final long INVALID_PURCHASE_ID_NONEXISTENT = (long)55;
	private static final Long EXISTING_PURCHASEID_DELIVERY = (long) 19;
	private static final Long EXISTING_PURCHASEID_PICKUP = (long) 11;
	
	private static final long VALID_CUSTOMER_ID = (long)66;
	private static final long INVALID_CUSTOMER_ID =(long)99;
	private static final String INVALID_USER_NAME_DIFFERENT_CLIENT = "juano1623";
	
	
	
	
	
	@BeforeEach
	public void setMockOutput() {
		lenient().when(artworkRepo.existsByArtworkId(anyLong())).thenAnswer((InvocationOnMock invocation)->{
			if (invocation.getArgument(0).equals(VALID_ARTWORK_ID1)) {
				return true;
			}
			if (invocation.getArgument(0).equals(VALID_ARTWORK_ID2)) {
				return true;
			}
			if (invocation.getArgument(0).equals(INVALID_ARTWORK_ID_STILLAVAILABLE)) {
				return true;
			}
			else {
				return false;
			}
		});
		
		lenient().when(purchaseRepo.existsByPurchaseId(anyLong())).thenAnswer((InvocationOnMock invocation)->{
			if (invocation.getArgument(0).equals(VALID_PURCHASE_ID1)) {
				return true;
			}
			if (invocation.getArgument(0).equals(VALID_PURCHASE_ID2)) {
				return true;
			}
			else {
				return false;
			}
		});
		
		lenient().when(artworkRepo.findArtworkByArtworkId(anyLong())).thenAnswer((InvocationOnMock invocation)->{
			if (invocation.getArgument(0).equals(VALID_ARTWORK_ID1)) {
				Artwork art = new Artwork();
				art.setArtworkId(VALID_ARTWORK_ID1);
				art.setStatus(ArtworkStatus.UNAVAILABLE);
				art.setPrice(VALID_ARTWORK_PRICE);
				return art;
			}
			if (invocation.getArgument(0).equals(VALID_ARTWORK_ID2)) {
				Artwork art = new Artwork();
				art.setArtworkId(VALID_ARTWORK_ID2);
				art.setStatus(ArtworkStatus.UNAVAILABLE);
				art.setPrice(VALID_ARTWORK_PRICE);
				return art;
			}
			if (invocation.getArgument(0).equals(INVALID_ARTWORK_ID_STILLAVAILABLE)) {
				Artwork art = new Artwork();
				art.setArtworkId(INVALID_ARTWORK_ID_STILLAVAILABLE);
				art.setStatus(ArtworkStatus.AVAILABLE);
				art.setPrice(VALID_ARTWORK_PRICE);
				return art;
			}
		
			else {
				return null;
			}
		});
		
		lenient().when(purchaseRepo.findPurchaseByPurchaseId(anyLong())).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(VALID_PURCHASE_ID1)) {
				Purchase p = new Purchase();
				p.setPurchaseId(VALID_PURCHASE_ID1);
				p.setShipmentType(ShipmentType.OFFSITE_DELIVERY);
				
				Customer c = new Customer();
				c.setCustomerId(VALID_CUSTOMER_ID);
				p.setCustomer(c);
				
				return p;
			}

			if (invocation.getArgument(0).equals(VALID_PURCHASE_ID2)) {
				Purchase p = new Purchase();
				p.setPurchaseId(VALID_PURCHASE_ID2);
				p.setShipmentType(ShipmentType.OFFSITE_DELIVERY);
				
				Customer c = new Customer();
				c.setCustomerId(VALID_CUSTOMER_ID);
				p.setCustomer(c);
				
				return p;
			}
			return invocation;
		});
		
		lenient().when(shipRepo.save(any(Shipment.class))).thenAnswer((InvocationOnMock i) -> {

			return i.getArgument(0);
		});
		

	}
	
	@Test
	public void testCreateShipmentValidParametersOffsite() {
		ShipmentDto sTO = new ShipmentDto(VALID_SHIPMENT_ID, VALID_SOURCE_ADDRESS, VALID_DESTINATION_ADDRESS, VALID_SHIPPING_COST,VALID_TOTAL_COST, VALID_RECIPIENT_STRING, VALID_USER_NAME);

		
		PurchaseDto pTO1 = new PurchaseDto();
		PurchaseDto pTO2 = new PurchaseDto();
		
		pTO1.setPurchaseId(VALID_PURCHASE_ID1);
		pTO2.setPurchaseId(VALID_PURCHASE_ID2);
		
		pTO1.setShipmentType(ShipmentType.OFFSITE_DELIVERY);
		pTO2.setShipmentType(ShipmentType.OFFSITE_DELIVERY);
		
		pTO1.setUsername(VALID_USER_NAME);
		pTO2.setUsername(VALID_USER_NAME);
		
		pTO1.setArtworkId(VALID_ARTWORK_ID1);
		pTO2.setArtworkId(VALID_ARTWORK_ID2);
		
		sTO.addPurchase(pTO1);
		sTO.addPurchase(pTO2);

		Shipment s = null ;
		
		try {
			s = service.createShipment(sTO);
		}
		catch(ShipmentException | PurchaseException e) {
			e.printStackTrace();
			fail();
		}
		assertNotNull(s);
		
		assertEquals(2,s.getPurchase().size());
		assertEquals(ShipmentStatus.CREATED,s.getShipmentStatus());
		assertEquals(VALID_SHIPMENT_ID, s.getShipmentId());
		assertEquals(VALID_SOURCE_ADDRESS, s.getSourceAddress());
		assertEquals(VALID_DESTINATION_ADDRESS, s.getDestinationAddress());
		assertEquals(VALID_RECIPIENT_STRING, s.getRecipientName());
		assertEquals(VALID_SHIPPING_COST, s.getShippingCost());
		assertEquals(VALID_TOTAL_COST, s.getTotalAmount());
		
		for (Purchase p : s.getPurchase()) {
			if (!p.getPurchaseId().equals(VALID_PURCHASE_ID1) ) {
				if (!p.getPurchaseId().equals(VALID_PURCHASE_ID2))  {
					fail();
				}
				
			}
		}
		

		
	}
	
	@Test
	public void testCreateServiceWithInvalidPurchaeId() {
		String error = null;
		Shipment s = null ;
		ShipmentDto sTO = new ShipmentDto(VALID_SHIPMENT_ID, VALID_SOURCE_ADDRESS, VALID_DESTINATION_ADDRESS, VALID_SHIPPING_COST,VALID_TOTAL_COST, VALID_RECIPIENT_STRING, VALID_USER_NAME);

		
		PurchaseDto pTO1 = new PurchaseDto();
		PurchaseDto pTO2 = new PurchaseDto();
		
		pTO1.setPurchaseId(INVALID_PURCHASE_ID_NONEXISTENT);
		pTO2.setPurchaseId(VALID_PURCHASE_ID2);
		
		pTO1.setShipmentType(ShipmentType.OFFSITE_DELIVERY);
		pTO2.setShipmentType(ShipmentType.OFFSITE_DELIVERY);
		
		pTO1.setUsername(VALID_USER_NAME);
		pTO2.setUsername(VALID_USER_NAME);
		
		pTO1.setArtworkId(VALID_ARTWORK_ID1);
		pTO2.setArtworkId(VALID_ARTWORK_ID2);
		
		sTO.addPurchase(pTO1);
		sTO.addPurchase(pTO2);

		try {
			s = service.createShipment(sTO);
		}
		catch(ShipmentException | PurchaseException e) {
			error=e.getMessage();
			
		}
		
		
		assertNull(s);
		assertEquals(error,"no purchase with id ["+INVALID_PURCHASE_ID_NONEXISTENT+"] found in system");
		
	}
	
	@Test
	public void testCreateServiceWithPurchasesOfDifferentTypes() {
		String error = null;
		Shipment s = null ;
		ShipmentDto sTO = new ShipmentDto(VALID_SHIPMENT_ID, VALID_SOURCE_ADDRESS, VALID_DESTINATION_ADDRESS, VALID_SHIPPING_COST,VALID_TOTAL_COST, VALID_RECIPIENT_STRING, VALID_USER_NAME);

		
		PurchaseDto pTO1 = new PurchaseDto();
		PurchaseDto pTO2 = new PurchaseDto();
		
		pTO1.setPurchaseId(VALID_PURCHASE_ID1);
		pTO2.setPurchaseId(VALID_PURCHASE_ID2);
		
		pTO1.setShipmentType(ShipmentType.OFFSITE_DELIVERY);
		pTO2.setShipmentType(ShipmentType.ONSITE_PICKUP);
		
		pTO1.setUsername(VALID_USER_NAME);
		pTO2.setUsername(VALID_USER_NAME);
		
		pTO1.setArtworkId(VALID_ARTWORK_ID1);
		pTO2.setArtworkId(VALID_ARTWORK_ID2);
		
		sTO.addPurchase(pTO1);
		sTO.addPurchase(pTO2);

		try {
			s = service.createShipment(sTO);
		}
		catch(ShipmentException | PurchaseException e) {
			error=e.getMessage();
			
		}
		
		
		assertNull(s);
		assertEquals(error,"not all purchases are of the same type");
	}
	
	@Test
	public void testCreateServiceWithPurchasesFromDifferentCustomers() {
		String error = null;
		Shipment s = null ;
		ShipmentDto sTO = new ShipmentDto(VALID_SHIPMENT_ID, VALID_SOURCE_ADDRESS, VALID_DESTINATION_ADDRESS, VALID_SHIPPING_COST,VALID_TOTAL_COST, VALID_RECIPIENT_STRING, VALID_USER_NAME);

		
		PurchaseDto pTO1 = new PurchaseDto();
		PurchaseDto pTO2 = new PurchaseDto();
		
		pTO1.setPurchaseId(VALID_PURCHASE_ID1);
		pTO2.setPurchaseId(VALID_PURCHASE_ID2);
		
		pTO1.setShipmentType(ShipmentType.OFFSITE_DELIVERY);
		pTO2.setShipmentType(ShipmentType.OFFSITE_DELIVERY);
		
		pTO1.setUsername(VALID_USER_NAME);
		pTO2.setUsername(INVALID_USER_NAME_DIFFERENT_CLIENT);
		
		pTO1.setArtworkId(VALID_ARTWORK_ID1);
		pTO2.setArtworkId(VALID_ARTWORK_ID2);
		
		sTO.addPurchase(pTO1);
		sTO.addPurchase(pTO2);

		try {
			s = service.createShipment(sTO);
		}
		catch(ShipmentException | PurchaseException e) {
			error=e.getMessage();
			
		}
		
		
		assertNull(s);
		assertEquals(error,"not all purchases are from the same client");
	}
	
	@Test
	public void testCreateServiceWithInvalidSourceAddress() {
		String error = null;
		Shipment s = null ;
		ShipmentDto sTO = new ShipmentDto(VALID_SHIPMENT_ID, INVALID_SOURCE_ADDRESS, VALID_DESTINATION_ADDRESS, VALID_SHIPPING_COST,VALID_TOTAL_COST, VALID_RECIPIENT_STRING, VALID_USER_NAME);

		
		PurchaseDto pTO1 = new PurchaseDto();
		PurchaseDto pTO2 = new PurchaseDto();
		
		pTO1.setPurchaseId(VALID_PURCHASE_ID1);
		pTO2.setPurchaseId(VALID_PURCHASE_ID2);
		
		pTO1.setShipmentType(ShipmentType.OFFSITE_DELIVERY);
		pTO2.setShipmentType(ShipmentType.OFFSITE_DELIVERY);
		
		pTO1.setUsername(VALID_USER_NAME);
		pTO2.setUsername(VALID_USER_NAME);
		
		pTO1.setArtworkId(VALID_ARTWORK_ID1);
		pTO2.setArtworkId(VALID_ARTWORK_ID2);
		
		sTO.addPurchase(pTO1);
		sTO.addPurchase(pTO2);

		try {
			s = service.createShipment(sTO);
		}
		catch(ShipmentException | PurchaseException e) {
			error=e.getMessage();
			
		}
		
		
		assertNull(s);
		assertEquals(error,"invalid source address. Address must be in format 1111 StreetName, Montreal, QC");
	}
	
	@Test
	public void testCreateServiceWithInvaliddestinationAddress() {
		String error = null;
		Shipment s = null ;
		ShipmentDto sTO = new ShipmentDto(VALID_SHIPMENT_ID, VALID_SOURCE_ADDRESS, INVALID_DESTINATION_ADDRESS, VALID_SHIPPING_COST,VALID_TOTAL_COST, VALID_RECIPIENT_STRING, VALID_USER_NAME);

		
		PurchaseDto pTO1 = new PurchaseDto();
		PurchaseDto pTO2 = new PurchaseDto();
		
		pTO1.setPurchaseId(VALID_PURCHASE_ID1);
		pTO2.setPurchaseId(VALID_PURCHASE_ID2);
		
		pTO1.setShipmentType(ShipmentType.OFFSITE_DELIVERY);
		pTO2.setShipmentType(ShipmentType.OFFSITE_DELIVERY);
		
		pTO1.setUsername(VALID_USER_NAME);
		pTO2.setUsername(VALID_USER_NAME);
		
		pTO1.setArtworkId(VALID_ARTWORK_ID1);
		pTO2.setArtworkId(VALID_ARTWORK_ID2);
		
		sTO.addPurchase(pTO1);
		sTO.addPurchase(pTO2);

		try {
			s = service.createShipment(sTO);
		}
		catch(ShipmentException | PurchaseException e) {
			error=e.getMessage();
			
		}
		
		
		assertNull(s);
		assertEquals(error,"invalid destination address. Address must be in format 1111 StreetName, Montreal, QC");
	}
	
	@Test
	public void testCreateServiceWithMissingrecipientNameNull() {
		String error = null;
		Shipment s = null ;
		ShipmentDto sTO = new ShipmentDto(VALID_SHIPMENT_ID, VALID_SOURCE_ADDRESS, VALID_DESTINATION_ADDRESS, VALID_SHIPPING_COST,VALID_TOTAL_COST, null, VALID_USER_NAME);

		
		PurchaseDto pTO1 = new PurchaseDto();
		PurchaseDto pTO2 = new PurchaseDto();
		
		pTO1.setPurchaseId(VALID_PURCHASE_ID1);
		pTO2.setPurchaseId(VALID_PURCHASE_ID2);
		
		pTO1.setShipmentType(ShipmentType.OFFSITE_DELIVERY);
		pTO2.setShipmentType(ShipmentType.OFFSITE_DELIVERY);
		
		pTO1.setUsername(VALID_USER_NAME);
		pTO2.setUsername(VALID_USER_NAME);
		
		pTO1.setArtworkId(VALID_ARTWORK_ID1);
		pTO2.setArtworkId(VALID_ARTWORK_ID2);
		
		sTO.addPurchase(pTO1);
		sTO.addPurchase(pTO2);

		try {
			s = service.createShipment(sTO);
		}
		catch(ShipmentException | PurchaseException e) {
			error=e.getMessage();
			
		}
		
		
		assertNull(s);
		assertEquals(error,"Recipient name missing");
	}

	@Test
	public void testCreateServiceWithMissingrecipientNameEmpty() {
		String error = null;
		Shipment s = null ;
		ShipmentDto sTO = new ShipmentDto(VALID_SHIPMENT_ID, VALID_SOURCE_ADDRESS, VALID_DESTINATION_ADDRESS, VALID_SHIPPING_COST,VALID_TOTAL_COST, "", VALID_USER_NAME);

		
		PurchaseDto pTO1 = new PurchaseDto();
		PurchaseDto pTO2 = new PurchaseDto();
		
		pTO1.setPurchaseId(VALID_PURCHASE_ID1);
		pTO2.setPurchaseId(VALID_PURCHASE_ID2);
		
		pTO1.setShipmentType(ShipmentType.OFFSITE_DELIVERY);
		pTO2.setShipmentType(ShipmentType.OFFSITE_DELIVERY);
		
		pTO1.setUsername(VALID_USER_NAME);
		pTO2.setUsername(VALID_USER_NAME);
		
		pTO1.setArtworkId(VALID_ARTWORK_ID1);
		pTO2.setArtworkId(VALID_ARTWORK_ID2);
		
		sTO.addPurchase(pTO1);
		sTO.addPurchase(pTO2);

		try {
			s = service.createShipment(sTO);
		}
		catch(ShipmentException | PurchaseException e) {
			error=e.getMessage();
			
		}
		
		
		assertNull(s);
		assertEquals(error,"Recipient name missing");
	}
	
	@Test
	public void testCreateServiceWithArtworkStillAvailable() {
		String error = null;
		Shipment s = null ;
		ShipmentDto sTO = new ShipmentDto(VALID_SHIPMENT_ID, VALID_SOURCE_ADDRESS, VALID_DESTINATION_ADDRESS, VALID_SHIPPING_COST,VALID_TOTAL_COST, VALID_RECIPIENT_STRING, VALID_USER_NAME);

		
		PurchaseDto pTO1 = new PurchaseDto();
		PurchaseDto pTO2 = new PurchaseDto();
		
		pTO1.setPurchaseId(VALID_PURCHASE_ID1);
		pTO2.setPurchaseId(VALID_PURCHASE_ID2);
		
		pTO1.setShipmentType(ShipmentType.OFFSITE_DELIVERY);
		pTO2.setShipmentType(ShipmentType.OFFSITE_DELIVERY);
		
		pTO1.setUsername(VALID_USER_NAME);
		pTO2.setUsername(VALID_USER_NAME);
		
		pTO1.setArtworkId(INVALID_ARTWORK_ID_STILLAVAILABLE);
		pTO2.setArtworkId(VALID_ARTWORK_ID2);
		
		sTO.addPurchase(pTO1);
		sTO.addPurchase(pTO2);

		try {
			s = service.createShipment(sTO);
		}
		catch(ShipmentException | PurchaseException e) {
			error=e.getMessage();
			
		}
		
		
		assertNull(s);
		assertEquals(error,"artwork is still avaiable and can not be shipped");
	}
}
