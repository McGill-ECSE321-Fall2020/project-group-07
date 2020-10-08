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

import ca.mcgill.ecse321.onlinegallery.model.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest
public class OnlineGalleryPersistenceTest {

	@Autowired
	private GalleryRegistrationRepository regRepo;
	
	@Autowired 
	private OnlineGalleryRepository onlineRepo;
	
	
	
	@AfterEach
	public void clearDatabase() {
		onlineRepo.deleteAll();
		regRepo.deleteAll();
	}

		
	@Test
	public void testPersistingAndLoadingRegistrationAttributesOnly() {
		
		OnlineGallery og = new OnlineGallery();
		int daysUp=100;
		og.setDaysUp(daysUp);

		GalleryRegistration reg = new GalleryRegistration();

		String userName="donutLover69";
		String firstName="Homer";
		String lastName="Simpson";
		String email="doh@nuts.com";
		String phoneNumber="555-GOT-DONUTS";
		String passWord="123";
		Boolean isLoggedIn=false;
		
		reg.setUserName(userName);
		reg.setFirstName(firstName);
		reg.setLastName(lastName);
		reg.setEmail(email);
		reg.setPhoneNumber(phoneNumber);
		reg.setPassword(passWord);
		reg.setIsLoggedIn(isLoggedIn);
		
		Set<GalleryRegistration> allReg = new HashSet<GalleryRegistration>();
		allReg.add(reg);
		
		og.setAllRegistrations(allReg);
		reg.setOnlineGallery(og);
		
		onlineRepo.save(og);
		
		reg=null;
		
		reg=regRepo.findGalleryRegisrationByUserName(userName);
		
		assertEquals(userName,reg.getUserName());
		assertEquals(firstName,reg.getFirstName());
		assertEquals(lastName,reg.getLastName());
		assertEquals(email,reg.getEmail());
		assertEquals(phoneNumber,reg.getPhoneNumber());
		assertEquals(passWord,reg.getPassword());
		assertEquals(isLoggedIn,reg.getIsLoggedIn());			
		
	}
	
	@Test
	public void testPersistingAndLoadingRegistrationAssociations() {
		
		OnlineGallery og = new OnlineGallery();
		int daysUp=100;
		og.setDaysUp(daysUp);

		GalleryRegistration reg = new GalleryRegistration();
		String userName="donutLover103";
		reg.setUserName(userName);
		
		Artist artist = new Artist();						//these objects all generate primary keys internally
		Customer customer=new Customer();					//but if you call object.getObjectID right now it would retur null
		GalleryAdmin admin = new GalleryAdmin();			//because those keys aren't generated until the obj is saved!
		Profile profile = new Profile();					//see after saving ~ line 120 for retriving the ids and doing assertion later
		
		
		Set<GalleryRegistration> allReg = new HashSet<GalleryRegistration>();
		allReg.add(reg);
		
		og.setAllRegistrations(allReg);
		reg.setOnlineGallery(og);
		
		
		reg.setGalleryCustomer(customer);
		customer.setGalleryRegistration(reg);
		
		reg.setGalleryAdmin(admin);
		admin.setGalleryRegistration(reg);
		
		reg.setGalleryArtist(artist);
		artist.setGalleryRegistration(reg);
		
		artist.setProfile(profile);
		profile.setArtist(artist);
		
		onlineRepo.save(og);								// need to save og first
		regRepo.save(reg);
		
		Long artistUniqueId = artist.getArtistId();				
		Long customerUniqueId=customer.getCustomerId();		
		Long adminUniqueId = admin.getAdminId();
		
		reg=null;
		artist=null;
		customer=null;
		admin=null;
		
		reg=regRepo.findGalleryRegisrationByUserName(userName);
		
		assertEquals(userName,reg.getUserName());
		assertEquals(artistUniqueId,reg.getGalleryArtist().getArtistId());
		assertEquals(customerUniqueId,reg.getGalleryCustomer().getCustomerId());
		assertEquals(adminUniqueId,reg.getGalleryAdmin().getAdminId());
	}
	
	
}

