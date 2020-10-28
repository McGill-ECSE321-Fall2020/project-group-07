package ca.mcgill.ecse321.onlinegallery.service.Customer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
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
import org.mockito.stubbing.Answer;

import ca.mcgill.ecse321.onlinegallery.dao.CustomerRepository;
import ca.mcgill.ecse321.onlinegallery.model.Customer;
import ca.mcgill.ecse321.onlinegallery.service.CustomerService;
import ca.mcgill.ecse321.onlinegallery.service.exception.CustomerException;

@ExtendWith(MockitoExtension.class)
public class TestCustomerServiceGetAllInvalid {

	@Mock
	private CustomerRepository cusRepo;
	
	@InjectMocks
	private CustomerService service;

	@BeforeEach
	public void setMockOutput() {		
		Answer<?> paramAsAnswer = (InvocationOnMock invocation)->{
			return invocation.getArgument(0);
		};
		lenient().when(cusRepo.findAll()).thenAnswer((InvocationOnMock invocation) -> {
			
			List<Customer> customers = new ArrayList<Customer>();
			Iterable<Customer> iterable = customers;
			 
			return iterable;
		});
	}
	
	@Test
	public void testInValidGetAll() {
		
		List<Customer> customers = null;
		String error = null;
		
		try {
			customers = service.findAllCustomers();
		}catch(CustomerException e) {
			error = e.getMessage();
		}
		
		assertNull(customers);
		assertEquals(error, "No customer exists.");
		
	}
	
}
