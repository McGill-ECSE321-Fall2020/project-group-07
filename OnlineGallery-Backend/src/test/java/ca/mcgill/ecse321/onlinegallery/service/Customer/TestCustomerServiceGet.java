package ca.mcgill.ecse321.onlinegallery.service.Customer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;

import ca.mcgill.ecse321.onlinegallery.dao.CustomerRepository;
import ca.mcgill.ecse321.onlinegallery.dao.GalleryRegistrationRepository;
import ca.mcgill.ecse321.onlinegallery.model.Artist;
import ca.mcgill.ecse321.onlinegallery.model.Customer;
import ca.mcgill.ecse321.onlinegallery.model.GalleryRegistration;
import ca.mcgill.ecse321.onlinegallery.service.CustomerService;

@ExtendWith(MockitoExtension.class)
public class TestCustomerServiceGet {

	@Mock
	private GalleryRegistrationRepository regRepo;
	
	@Mock
	private CustomerRepository cusRepo;
	
	@InjectMocks
	private CustomerService service;
	
	@SuppressWarnings("deprecation")
	private static final Long CUSTOMER_ID = new Long(1);
	
	private static final String VALID_USERNAME = "ValidUserName";
	private static final String INVALID_USERNAME_NO_EXIST = "nonexistentUser";
	private static final String INVALID_USERNAME_EXISTS_NO_CUSTOMER = "nonCustomerUser";
	
	@BeforeEach
	public void setMockOutput() {
		lenient().when(regRepo.existsByUserName(anyString())).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(VALID_USERNAME)) {
				return true;
			}
			else if (invocation.getArgument(0).equals(INVALID_USERNAME_EXISTS_NO_CUSTOMER)) {
				return true;
			}
			else {
				return false;
			}
		});
		lenient().when(regRepo.findGalleryRegisrationByUserName(anyString())).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(VALID_USERNAME)) {
				GalleryRegistration gallReg = new GalleryRegistration();
				Customer customer = new Customer();
				
				gallReg.setUserName(VALID_USERNAME);
				gallReg.setCustomer(customer);
				customer.setGalleryRegistration(gallReg);
				customer.setCustomerId(CUSTOMER_ID);
				
				return gallReg;
			}
			else if(invocation.getArgument(0).equals(INVALID_USERNAME_EXISTS_NO_CUSTOMER)) {
				GalleryRegistration gallReg = new GalleryRegistration();
				Artist artist = new Artist();
				
				gallReg.setUserName(INVALID_USERNAME_EXISTS_NO_CUSTOMER);
				gallReg.setArtist(artist);
				artist.setGalleryRegistration(gallReg);
				
				return gallReg;
			}
			else {
				return null;
			}
		});

	}
	
	@Test
	public void testGetValidCustomer() {
		
		Customer customer = null;
		
		try {
			customer = service.findCustomerByUsername(VALID_USERNAME);
		} catch (Exception e) {
			fail();
		}
		
		assertNotNull(customer);
		assertEquals(customer.getCustomerId(), CUSTOMER_ID);
		
	}
	
	@Test
	public void testGetInValidNotCustomer() {
		
		Customer customer = null;
		String error = null;
		
		try {
			customer = service.findCustomerByUsername(INVALID_USERNAME_EXISTS_NO_CUSTOMER);
		} catch (Exception e) {
			error = e.getMessage();
		}
		
		assertNull(customer);
		assertEquals(error, "No customer exists under the username ["+INVALID_USERNAME_EXISTS_NO_CUSTOMER+"]");
		
	}
	
	@Test
	public void testGetInValidNotUser() {
		
		Customer customer = null;
		String error = null;
		
		try {
			customer = service.findCustomerByUsername(INVALID_USERNAME_NO_EXIST);
		} catch (Exception e) {
			error = e.getMessage();
		}
		
		assertNull(customer);
		assertEquals(error, "No registration exists under the username ["+INVALID_USERNAME_NO_EXIST+"]");
		
	}
	
}
