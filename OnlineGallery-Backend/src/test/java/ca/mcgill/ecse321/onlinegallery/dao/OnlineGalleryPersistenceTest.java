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
	private OnlineGalleryRepository ogRepo;

	@Autowired
	private PhysicalGalleryRepository pgRepo;
	
	@Autowired
	private GalleryRegistrationRepository regRepo;
	
	@Autowired
	private GalleryAdminRepository adminRepo;

	@AfterEach
	public void clearDatabase() {
		ogRepo.deleteAll();
		pgRepo.deleteAll();
		adminRepo.deleteAll();
	}

//	@Test
//	public void testOnlineGalleryPhysicalGalleryGalleryRegistration() {
//		OnlineGallery og = new OnlineGallery();
//		og.setDaysUp(12);
//		
//		PhysicalGallery pg = new PhysicalGallery();
//		pg.setAddress("100 arts drive");
//		
//		og.setPhysicalGallery(pg);
//		
//		GalleryRegistration reg = new GalleryRegistration();
//		reg.setUserName("lisa");
//		
//		GalleryRegistration reg2 = new GalleryRegistration();
//		reg2.setUserName("baby");
//		
//		
//		og.getAllRegistrations().add(reg);
//		og.getAllRegistrations().add(reg2);
//		
//		pgRepo.save(pg);
//		onlineRepo.save(og);	
//	}
	
	@Test
	public void testPhysicalGallery() {
		String address="100 arts road";
		int daysUp=12;
		
		PhysicalGallery pg = new PhysicalGallery();
		pg.setAddress(address);
		
		OnlineGallery og = new OnlineGallery();
		og.setDaysUp(daysUp);
		
		og.setPhysicalGallery(pg);
		pg.setOnlineGallery(og);
		
		pgRepo.save(pg);
		
		Long pgId = pg.getGalleryId();
		Long ogId=og.getSystemId();
		
		pg=null;
		og=null;
		
		pg=pgRepo.findPhysicalGalleryByGalleryId(pgId);
		og=ogRepo.findOnlineGalleryBySystemId(ogId);
		
		assertEquals(pg.getAddress(),address);
		assertEquals(pg.getGalleryId(),pgId);
		
		assertEquals(pg.getOnlineGallery().getDaysUp(),daysUp);
		assertEquals(pg.getOnlineGallery().getSystemId(),ogId);
		
		assertEquals(og.getSystemId(),ogId);
		assertEquals(og.getDaysUp(),daysUp);
		
		assertEquals(og.getPhysicalGallery().getAddress(),address);
		assertEquals(og.getPhysicalGallery().getGalleryId(),pgId);
	}
	
//	@Test
//	public void testRegistrationAdmin() {
//		GalleryRegistration reg = new GalleryRegistration();
//		reg.setUserName("homer");
//		
//		GalleryAdmin admin = new GalleryAdmin();
//		reg.setAdmin(admin);
//		admin.setGalleryRegistration(reg);
//		
//		adminRepo.save(admin);
//		
//		Long adminId=admin.getAdminId();
//		
//		admin=null;
//		
//		admin=adminRepo.findGalleryAdminByAdminId(adminId);
//		
//		System.out.println(admin.getGalleryRegistration().getUserName());
//	}
}
