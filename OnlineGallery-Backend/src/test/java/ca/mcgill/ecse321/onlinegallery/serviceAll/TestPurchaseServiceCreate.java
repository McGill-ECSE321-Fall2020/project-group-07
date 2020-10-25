package ca.mcgill.ecse321.onlinegallery.serviceAll;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;

import java.sql.Date;
import java.time.LocalDate;

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
public class TestPurchaseServiceCreate {
	
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
	private static final String INVALID_USERNAMENONEXIST="nonexistUsername";
	private static final String INVALID_USERNAMENOTACUSTOMER="userNotCustomer";

	@SuppressWarnings("deprecation")
	private static final Long VALID_ARTWORKID= new Long(1);
	@SuppressWarnings("deprecation")
	private static final Long INVALID_ARTWORKIDNONEXIST= new Long(2);
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
				reg.setUserName(VALID_USERNAME);
				reg.setCustomer(new Customer());
				return reg;
			}
			if (invocation.getArgument(0).equals(INVALID_USERNAMENOTACUSTOMER)) {
				GalleryRegistration reg = new GalleryRegistration();
				reg.setUserName(VALID_USERNAME);
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

			return p;
		});
		
	}
	
	@Test
	public void createPurchaseValidUsernameValidArtid() {
		PurchaseDto dto = new PurchaseDto();
		dto.setUsername(VALID_USERNAME);
		dto.setArtworkId(VALID_ARTWORKID);
		dto.setShipmentType(ShipmentType.OFFSITE_DELIVERY);
		
		Purchase p=null;
		String error=null;
		
		try {
			p=service.createPurchase(dto);
		}
		catch(PurchaseException e){
			fail();
		}
		
		assertNotNull(p);
		assertEquals(p.getCustomer().getGalleryRegistration().getUserName(),VALID_USERNAME);
		assertEquals(p.getArtwork().getArtworkId(),VALID_ARTWORKID);
		assertEquals(p.getArtwork().getStatus(),ArtworkStatus.UNAVAILABLE);
	}
	
	@Test
	public void createPurchaseInvalidUsernameNonExistValidArtid() {
		PurchaseDto dto = new PurchaseDto();
		dto.setUsername(INVALID_USERNAMENONEXIST);
		dto.setArtworkId(VALID_ARTWORKID);
		dto.setShipmentType(ShipmentType.OFFSITE_DELIVERY);
		
		Purchase p=null;
		String error=null;
		
		try {
			p=service.createPurchase(dto);
		}
		catch(PurchaseException e){
			error=e.getMessage();
		}
		
		assertNull(p);
		assertEquals(error,"no GalleryRegistration with the username ["+INVALID_USERNAMENONEXIST+"] found in system");
	}
	
	@Test
	public void createPurchaseInvalidUsernameNotCustomerValidArtid() {
		PurchaseDto dto = new PurchaseDto();
		dto.setUsername(INVALID_USERNAMENOTACUSTOMER);
		dto.setArtworkId(VALID_ARTWORKID);
		dto.setShipmentType(ShipmentType.OFFSITE_DELIVERY);
		
		Purchase p=null;
		String error=null;
		
		try {
			p=service.createPurchase(dto);
		}
		catch(PurchaseException e){
			error=e.getMessage();
		}
		
		assertNull(p);
		assertEquals(error,"no Customer associated with the GalleryRegistratrion username ["+INVALID_USERNAMENOTACUSTOMER+"]");
	}
	
	@Test
	public void createPurchaseValidUsernameInvalidArtworkIdNonExist() {
		PurchaseDto dto = new PurchaseDto();
		dto.setUsername(VALID_USERNAME);
		dto.setArtworkId(INVALID_ARTWORKIDNONEXIST);
		dto.setShipmentType(ShipmentType.OFFSITE_DELIVERY);
		
		Purchase p=null;
		String error=null;
		
		try {
			p=service.createPurchase(dto);
		}
		catch(PurchaseException e){
			error=e.getMessage();
		}
		
		assertNull(p);
		assertEquals(error,"no artwork with id ["+INVALID_ARTWORKIDNONEXIST+"] found in system");
	}
	
	@Test
	public void createPurchaseValidUsernameInvalidArtworkIdNotAvailable() {
		PurchaseDto dto = new PurchaseDto();
		dto.setUsername(VALID_USERNAME);
		dto.setArtworkId(INVALID_ARTWORKIDNOTAVAILABLE);
		dto.setShipmentType(ShipmentType.OFFSITE_DELIVERY);
		
		Purchase p=null;
		String error=null;
		
		try {
			p=service.createPurchase(dto);
		}
		catch(PurchaseException e){
			error=e.getMessage();
		}
		
		assertNull(p);
		assertEquals(error,"Artwork with id ["+INVALID_ARTWORKIDNOTAVAILABLE+"] no longer available for sale");
	}

}

