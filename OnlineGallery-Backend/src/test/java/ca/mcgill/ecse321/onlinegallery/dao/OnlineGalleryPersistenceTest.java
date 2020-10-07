package ca.mcgill.ecse321.onlinegallery.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.HashSet;
import java.util.Set;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ca.mcgill.ecse321.onlinegallery.model.Customer;
import ca.mcgill.ecse321.onlinegallery.model.GalleryAdmin;
import ca.mcgill.ecse321.onlinegallery.model.GalleryRegistration;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class OnlineGalleryPersistenceTest {

	@Autowired
	private GalleryRegistrationRepository regRepo;
	
	@Autowired
	private GalleryAdminRepository adminRepo;
	
	@Autowired
	private CustomerRepository customerRepo;
	

//	@AfterEach
//	public void clearDatabase() {
//		registrationRepo.deleteAll();
//		adminRepo.deleteAll();
//	}

	@Test
	public void testPersistAndLoadRegistration() {
	
		GalleryRegistration reg = new GalleryRegistration();
		reg.setUserName("homer");
		
		GalleryAdmin admin=new GalleryAdmin();
		Customer customer = new Customer();
		Set<Customer> allCustomers = new HashSet<Customer>();
		allCustomers.add(customer);
		
		reg.setGalleryAdmin(admin);
		reg.setGalleryCustomers(allCustomers);
		
		admin.setGalleryRegistration(reg);
		customer.setGalleryRegistration(reg);
		
		
		regRepo.save(reg);
		
	}
	
}

