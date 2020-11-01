package ca.mcgill.ecse321.onlinegallery.service.Purchase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
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
import org.mockito.stubbing.Answer;

import ca.mcgill.ecse321.onlinegallery.dao.*;
import ca.mcgill.ecse321.onlinegallery.dto.*;
import ca.mcgill.ecse321.onlinegallery.model.*;
import ca.mcgill.ecse321.onlinegallery.service.*;
import ca.mcgill.ecse321.onlinegallery.service.exception.*;

@ExtendWith(MockitoExtension.class)
public class TestPurchaseServiceDelete { 
	
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
	
	private static final String VALID_USERNAME="validUsername";
	private static final String VALID_USERNAME_BUTNOPURCHASE="validNoPurchase";
	private static final String INVALID_USERNAMENOTACUSTOMER="userNotCustomer";

	@SuppressWarnings("deprecation")
	private static final Long VALID_ARTWORKID= new Long(1);
	@SuppressWarnings("deprecation")
	private static final Long INVALID_ARTWORKIDNOTAVAILABLE=new Long (3);
	
	@BeforeEach
	public void setMockOutput() {
		Answer<?> paramAsAnswer = (InvocationOnMock invocation)->{
			return invocation.getArgument(0);
		};
		
		lenient().when(regRepo.existsByUserName(anyString())).thenAnswer((InvocationOnMock invocation)->{
			if (invocation.getArgument(0).equals(VALID_USERNAME)) {
				return true;
			}
			if (invocation.getArgument(0).equals(VALID_USERNAME_BUTNOPURCHASE)) {
				return true;
			}
			if (invocation.getArgument(0).equals(INVALID_USERNAMENOTACUSTOMER)) {
				return true;
			}
			
			else {
				return false;
			}
		}); 
		
		lenient().when(regRepo.findGalleryRegisrationByUserName(anyString())).thenAnswer((InvocationOnMock invocation)->{
			if (invocation.getArgument(0).equals(VALID_USERNAME)) {
				GalleryRegistration reg = new GalleryRegistration();
				Customer c = new Customer();
				reg.setUserName(VALID_USERNAME);
				reg.setCustomer(c);
				c.setGalleryRegistration(reg);
				return reg;
			}
			if (invocation.getArgument(0).equals(VALID_USERNAME_BUTNOPURCHASE)) {
				GalleryRegistration reg = new GalleryRegistration();
				Customer c = new Customer();
				reg.setUserName(VALID_USERNAME_BUTNOPURCHASE);
				reg.setCustomer(c);
				c.setGalleryRegistration(reg);
				return reg;
			}
			if (invocation.getArgument(0).equals(INVALID_USERNAMENOTACUSTOMER)) {
				GalleryRegistration reg = new GalleryRegistration();
				reg.setUserName(INVALID_USERNAMENOTACUSTOMER);
				return reg;
			}
			else {
				return null;
			}
		});
		
		lenient().when(artRepo.existsByArtworkId(anyLong())).thenAnswer((InvocationOnMock invocation)->{
			if (invocation.getArgument(0).equals(VALID_ARTWORKID)) {
				return true;
			}
			if (invocation.getArgument(0).equals(INVALID_ARTWORKIDNOTAVAILABLE)) {
				return true;
			}
			else {
				return false;
			}
		});
		
		lenient().when(artRepo.findArtworkByArtworkId(anyLong())).thenAnswer((InvocationOnMock invocation)->{
			if (invocation.getArgument(0).equals(VALID_ARTWORKID)) {
				Artwork art = new Artwork();
				art.setArtworkId(VALID_ARTWORKID);
				art.setStatus(ArtworkStatus.AVAILABLE);
				return art;
			}
			if (invocation.getArgument(0).equals(INVALID_ARTWORKIDNOTAVAILABLE)) {
				Artwork art = new Artwork();
				art.setArtworkId(INVALID_ARTWORKIDNOTAVAILABLE);
				art.setStatus(ArtworkStatus.UNAVAILABLE);
				return art;
			}
			else {
				return null;
			}
		});
		
		lenient().when(purchaseRepo.save(any(Purchase.class))).thenAnswer((InvocationOnMock invocation)->{
			GalleryRegistration reg = new GalleryRegistration();			
			Customer cust = new Customer();
			Purchase p = new Purchase();
			Artwork art = new Artwork();

			reg.setUserName(VALID_USERNAME);
			art.setArtworkId(VALID_ARTWORKID);
			art.setStatus(ArtworkStatus.UNAVAILABLE);
			
			cust.setGalleryRegistration(reg);
			reg.setCustomer(cust);
			
			p.setCustomer(cust);
			cust.getPurchase().add(p);
			
			p.setArtwork(art);
			art.setPurchase(p);
			
			p.setShipmentType(((Purchase)invocation.getArgument(0)).getShipmentType());

			return p;
		});
		
		lenient().when(purchaseRepo.findByCustomerAndArtwork(any(Customer.class),any(Artwork.class))).thenAnswer((InvocationOnMock invocation)->{
			if ((((Customer)invocation.getArgument(0)).getGalleryRegistration().getUserName().equals(VALID_USERNAME)) && (((Artwork)invocation.getArgument(1)).getArtworkId().equals(VALID_ARTWORKID)) ) {
				GalleryRegistration reg = new GalleryRegistration();
				Purchase p = new Purchase();
				Customer c = new Customer();
				Artwork art= new Artwork();
				
				reg.setUserName(VALID_USERNAME);
				art.setArtworkId(VALID_ARTWORKID);
				
				reg.setCustomer(c);
				c.setGalleryRegistration(reg);
				
				c.getPurchase().add(p);
				p.setCustomer(c);
				
				p.setArtwork(art);
				art.setPurchase(p);
				
				return p;
			}
			else {
				return null;
			}
		});
		
		lenient().doAnswer((i)->null).when(purchaseRepo).delete(any(Purchase.class));
	}
	
	@Test
	public void testValidDelete() {
		PurchaseDto dto = new PurchaseDto();
		dto.setUsername(VALID_USERNAME);
		dto.setArtworkId(VALID_ARTWORKID);
		dto.setShipmentType(ShipmentType.OFFSITE_DELIVERY);
		
		Purchase p = null;
		
		try {
			p=service.deletePurchase(dto);
		}
		catch(PurchaseException e) {
			fail();
		}
		
		assertNotNull(p);
		assertEquals(p.getShipmentType(),ShipmentType.OFFSITE_DELIVERY);
	}	
}

