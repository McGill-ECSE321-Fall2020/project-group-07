package ca.mcgill.ecse321.onlinegallery.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.HashSet;
import java.util.Set;
import java.sql.Date;
import java.time.LocalDate;
import java.time.Month;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ca.mcgill.ecse321.onlinegallery.model.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class OnlineGalleryPersistenceTest {

	@Autowired
	private OnlineGalleryRepository onlineRepo;

	@Autowired
	private PhysicalGalleryRepository pgRepo;
	
	@Autowired
	private GalleryRegistrationRepository regRepo;

//	@AfterEach
//	public void clearDatabase() {
//		onlineRepo.deleteAll();
//		pgRepo.deleteAll();
//	}

//
	@Test
	public void testBasics() {
		OnlineGallery og = new OnlineGallery();
		og.setDaysUp(12);
		
		PhysicalGallery pg = new PhysicalGallery();
		pg.setAddress("100 arts drive");
		
		og.setPhysicalGallery(pg);
		
		GalleryRegistration reg = new GalleryRegistration();
		reg.setUserName("homer");
		
		GalleryRegistration reg2 = new GalleryRegistration();
		reg2.setUserName("bart");
		
		
		og.getAllRegistrations().add(reg);
		og.getAllRegistrations().add(reg2);
		
//		regRepo.save(reg);
		pgRepo.save(pg);
		onlineRepo.save(og);		

		
		
	}
}
