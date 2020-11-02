package ca.mcgill.ecse321.onlinegallery.service.Customer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.lenient;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;

import ca.mcgill.ecse321.onlinegallery.dao.CustomerRepository;
import ca.mcgill.ecse321.onlinegallery.model.Customer;
import ca.mcgill.ecse321.onlinegallery.model.GalleryRegistration;
import ca.mcgill.ecse321.onlinegallery.service.CustomerService;
import ca.mcgill.ecse321.onlinegallery.service.exception.CustomerException;

@ExtendWith(MockitoExtension.class)
public class TestCustomerServiceGetAll {

	@Mock
	private CustomerRepository cusRepo;
	
	@InjectMocks
	private CustomerService service;
	
	@SuppressWarnings("deprecation")
	private static final Long CUSTOMER_ID_1 = new Long(1);
	private static final String BANK_INFO_1 = "420_EZ";
	private static final String VALID_USERNAME_1 = "Valid1";
	
	@SuppressWarnings("deprecation")
	private static final Long CUSTOMER_ID_2 = new Long(2);
	private static final String BANK_INFO_2 = "69_LULW";
	private static final String VALID_USERNAME_2 = "Valid2";
	
	@BeforeEach
	public void setMockOutput() {		
		lenient().when(cusRepo.findAll()).thenAnswer((InvocationOnMock invocation) -> {
			
			GalleryRegistration reg1 = new GalleryRegistration();
			reg1.setUserName(VALID_USERNAME_1);
			
			GalleryRegistration reg2 = new GalleryRegistration();
			reg2.setUserName(VALID_USERNAME_2);
			
			Customer customer1 = new Customer();
			customer1.setBankInfo(BANK_INFO_1);
			customer1.setCustomerId(CUSTOMER_ID_1);
			customer1.setGalleryRegistration(reg1);
			reg1.setCustomer(customer1);
			
			Customer customer2 = new Customer();
			customer2.setBankInfo(BANK_INFO_2);
			customer2.setCustomerId(CUSTOMER_ID_2);
			customer2.setGalleryRegistration(reg2);
			reg2.setCustomer(customer2);
			
			List<Customer> customers = new ArrayList<Customer>();
			customers.add(customer1);
			customers.add(customer2);
			
			Iterable<Customer> iterable = customers;
			 
			return iterable;
		});
	}
	
	@Test
	public void testValidGetAll() {
		
		List<Customer> customers = null;
		
		try {
			customers = service.findAllCustomers();
		}catch(CustomerException e) {
			fail();
		}
		
		assertNotNull(customers);
		assertEquals(customers.size(), 2);
		assertEquals(customers.get(0).getBankInfo(), BANK_INFO_1);
		assertEquals(customers.get(0).getCustomerId(), CUSTOMER_ID_1);
		assertEquals(customers.get(1).getBankInfo(), BANK_INFO_2);
		assertEquals(customers.get(1).getCustomerId(), CUSTOMER_ID_2);
		
	}
	
}
