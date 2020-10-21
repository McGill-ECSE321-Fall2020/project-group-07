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
	
	@Autowired
	private CustomerRepository custRepo;
	
	@Autowired
	private ArtistRepository artistRepo;
	
	@Autowired
	private ProfileRepository proRepo;
	
	@Autowired
	private ArtworkRepository artRepo;
	
	@Autowired
	private PurchaseRepository purchaseRepo;

	@AfterEach
	public void clearDatabase() {
		ogRepo.deleteAll();
		pgRepo.deleteAll();
		regRepo.deleteAll();
		adminRepo.deleteAll();
		custRepo.deleteAll();
		artistRepo.deleteAll();
		proRepo.deleteAll();
		artRepo.deleteAll();
		purchaseRepo.deleteAll();
	}


	@Test
	public void testOnlineGallery() {
		
		OnlineGallery og = new OnlineGallery();
		int daysUp=15;
		
		GalleryRegistration reg=new GalleryRegistration();

		String userName="bananaFartman";
		String firstName="Bart";
		String lastName="simpson";
		String email="bsimpson@gmail.com";
		String phone="123-456-7899";
		Boolean loggedIn= false;
		String password="password";
		
		reg.setUserName(userName);
		reg.setFirstName(firstName);
		reg.setLastName(lastName);
		reg.setEmail(email);
		reg.setPhoneNumber(phone);
		reg.setIsLoggedIn(loggedIn);
		reg.setPassWord(password);
		
		PhysicalGallery pg = new PhysicalGallery();
		String pgAddress="100 arts lane";
		
		pg.setAddress(pgAddress);
		
		
		og.setDaysUp(daysUp);
		og.getAllRegistrations().add(reg);
		og.setPhysicalGallery(pg);
		
		ogRepo.save(og);
		Long ogId=og.getSystemId();
		Long pgId=pg.getGalleryId();
		
		og=null;
		reg=null;
		pg=null;
		
		og=ogRepo.findOnlineGalleryBySystemId(ogId);
		
		//checking og attributes
		assertNotNull(og);
		assertEquals(og.getDaysUp(),daysUp);
		
		//checking associations from onlinegallery
		reg=og.getAllRegistrations().iterator().next();
		assertNotNull(reg);
		assertEquals(reg.getUserName(),userName);
		assertEquals(reg.getFirstName(),firstName);
		assertEquals(reg.getLastName(),lastName);
		assertEquals(reg.getEmail(),email);
		assertEquals(reg.getPhoneNumber(),phone);
		assertEquals(reg.getIsLoggedIn(),loggedIn);
		assertEquals(reg.getPassWord(),password);
		
		pg=og.getPhysicalGallery();
		assertNotNull(pg);
		assertEquals(pg.getGalleryId(),pgId);
		assertEquals(pg.getAddress(),pgAddress);
	}
	
	@Test
	public void testPhysicalGallery() {
		
		OnlineGallery og = new OnlineGallery();	
		
		PhysicalGallery pg = new PhysicalGallery();
		String address = "555 watercolor drive";
		pg.setAddress(address);
		
		og.setPhysicalGallery(pg);
		
		ogRepo.save(og);						//save with the owner in the unidirectional association
		
		Long pgId=pg.getGalleryId();
		Long ogId=og.getSystemId();
		
		og=null;
		pg=null;
		
		pg=pgRepo.findPhysicalGalleryByGalleryId(pgId);	//persistence cascaded from og
		
		//checking pg attributes
		assertNotNull(pg);
		assertEquals(pg.getAddress(),address);
		
		//checking associations from physicalgallery: 
			//none, unidirectional only
		
		//checking associations to physicalgallery;
		og=ogRepo.findOnlineGalleryBySystemId(ogId);
		pg=og.getPhysicalGallery();
		assertNotNull(pg);
		assertEquals(pg.getAddress(),address);
	}
	
	@Test
	public void testGalleryRegistration() {
		GalleryRegistration reg=new GalleryRegistration();

		String userName="bananaFartman15";
		String firstName="Bart";
		String lastName="simpson";
		String email="bsimpson@gmail.com";
		String phone="123-456-7899";
		Boolean loggedIn= false;
		String password="password";
		
		reg.setUserName(userName);
		reg.setFirstName(firstName);
		reg.setLastName(lastName);
		reg.setEmail(email);
		reg.setPhoneNumber(phone);
		reg.setIsLoggedIn(loggedIn);
		reg.setPassWord(password);
		
		GalleryAdmin admin = new GalleryAdmin();
		reg.setAdmin(admin);
		admin.setGalleryRegistration(reg);
		
		Customer customer = new Customer();
		String customerBank="has money";
		customer.setBankInfo(customerBank);
		reg.setCustomer(customer);
		customer.setGalleryRegistration(reg);
		
		Artist artist = new Artist();
		String artistBank="no money";
		artist.setBankInfo(artistBank);
		reg.setArtist(artist);
		artist.setGalleryRegistration(reg);

		adminRepo.save(admin);
		custRepo.save(customer);
		artistRepo.save(artist);
		regRepo.save(reg);

		Long adminId=admin.getAdminId();
		Long custId=customer.getCustomerId();
		Long artistId=artist.getArtistId();
		
		reg=null;
		customer=null;
		admin=null;
		artist=null;
		
		//checking reg attributes
		reg=regRepo.findGalleryRegisrationByUserName(userName);
		assertNotNull(reg);
		assertEquals(reg.getUserName(),userName);
		assertEquals(reg.getFirstName(),firstName);
		assertEquals(reg.getLastName(),lastName);
		assertEquals(reg.getEmail(),email);
		assertEquals(reg.getPhoneNumber(),phone);
		assertEquals(reg.getIsLoggedIn(),loggedIn);
		assertEquals(reg.getPassWord(),password);
		
		//checking associations from reg
		customer = reg.getCustomer();
		assertNotNull(customer);
		assertEquals(customer.getCustomerId(),custId);
		assertEquals(customer.getBankInfo(),customerBank);
		
		admin = reg.getAdmin();
		assertNotNull(admin);
		assertEquals(admin.getAdminId(),adminId);
		
		artist = reg.getArtist();
		assertNotNull(artist);
		assertEquals(artist.getArtistId(),artistId);
		assertEquals(artist.getBankInfo(),artistBank);
		
		//checking associations to reg
		customer=null;
		admin=null;
		artist=null;
		
		customer=custRepo.findCustomerByCustomerId(custId);
		assertNotNull(customer);
		assertEquals(customer.getCustomerId(),custId);
		assertEquals(customer.getBankInfo(),customerBank);
		
		artist=artistRepo.findArtistByArtistId(artistId);
		assertNotNull(artist);
		assertEquals(artist.getArtistId(),artistId);
		assertEquals(artist.getBankInfo(),artistBank);
		
		admin = adminRepo.findGalleryAdminByAdminId(adminId);
		assertNotNull(admin);
		assertEquals(admin.getAdminId(),adminId);
		
	}
	
	@Test
	public void testAdmin() {
		GalleryRegistration reg=new GalleryRegistration();

		String userName="bananaFartman15";
		reg.setUserName(userName);
		
		GalleryAdmin admin = new GalleryAdmin();
		reg.setAdmin(admin);
		admin.setGalleryRegistration(reg);
		
		adminRepo.save(admin);
		regRepo.save(reg);
		Long adminId=admin.getAdminId();
		
		reg=null;
		admin=null;
		
		//checking admin attributes
		admin=adminRepo.findGalleryAdminByAdminId(adminId);
		assertNotNull(admin);
		
		//cehcking associations from admin
		assertEquals(admin.getGalleryRegistration().getUserName(),userName);
		
		//checking associations to admin
		reg=regRepo.findGalleryRegisrationByUserName(userName);
		assertNotNull(reg);
		assertEquals(reg.getAdmin().getAdminId(),adminId);
	}
	
	
	@Test
	public void testArtist() {
		GalleryRegistration reg=new GalleryRegistration();
		String username="bart";
		reg.setUserName(username);
		
		Artist artist = new Artist();
		String bankInfo="TD checking";
		artist.setBankInfo(bankInfo); 
		
		Profile p = new Profile();
		artist.setProfile(p);
		
		reg.setArtist(artist);
		artist.setGalleryRegistration(reg);
		
		Artwork art = new Artwork();
		
		artist.getArtwork().add(art);
		
		artistRepo.save(artist);
		Long artistId=artist.getArtistId();
		Long pId=p.getProfileId();
		Long artId=art.getArtworkId();
		
		artist=null;
		reg=null;
		p=null;
		artist=artistRepo.findArtistByArtistId(artistId);
		
		//checking artist attributes
		assertNotNull(artist);
		assertEquals(artist.getArtistId(),artistId);
		assertEquals(artist.getBankInfo(),bankInfo);
		
		//checking associations from artist
		assertEquals(artist.getProfile().getProfileId(),pId);
		assertEquals(artist.getGalleryRegistration().getUserName(),username);
		assertEquals(artist.getArtwork().iterator().next().getArtworkId(),artId);
		
		//checking associations to artist
		reg=regRepo.findGalleryRegisrationByUserName(username);			//bidrectional persistence cascaded back to registration
		artist=reg.getArtist();
		assertNotNull(artist);
		assertEquals(artist.getArtistId(),artistId);
		assertEquals(artist.getBankInfo(),bankInfo);
		
	}
	
	@Test
	public void testProfile() {
		
		GalleryRegistration reg=new GalleryRegistration();
		String username="bart";
		reg.setUserName(username);
		
		Artist artist = new Artist();
		reg.setArtist(artist);
		artist.setGalleryRegistration(reg);
		
		Profile p = new Profile();
		artist.setProfile(p);
		
		int numSold=3;
		double totalEarnings=3.14;
		String selfDesc="starving";
		double rating=5.0;
		
		p.setNumSold(numSold);
		p.setTotalEarnings(totalEarnings);
		p.setSelfDescription(selfDesc);
		p.setRating(rating);
		
		artistRepo.save(artist);							//only need to persist the owner of unidirectional association
		Long pId=p.getProfileId();
		Long artistId=artist.getArtistId();
		
		p=null;
		p=proRepo.findProfileByProfileId(pId);
		
		//check attributes
		assertNotNull(p);
		assertEquals(p.getNumSold(),numSold);
		assertEquals(p.getTotalEarnings(),totalEarnings);
		assertEquals(p.getSelfDescription(),selfDesc);
		assertEquals(p.getRating(),rating);
		
		//checking associations to profile
		artist=artistRepo.findArtistByArtistId(artistId);
		p=artist.getProfile();
		assertNotNull(p);
		assertEquals(p.getNumSold(),numSold);
		assertEquals(p.getTotalEarnings(),totalEarnings);
		assertEquals(p.getSelfDescription(),selfDesc);
		assertEquals(p.getRating(),rating);	
		
		//no associations from profile, unidirectional
	}
	
	@Test
	public void testArtwork() {
		GalleryRegistration reg=new GalleryRegistration();
		String userName="bananaFartman15";		
		reg.setUserName(userName);

		
		Customer customer = new Customer();
		reg.setCustomer(customer);
		customer.setGalleryRegistration(reg);
		
		Artist artist = new Artist();
		reg.setArtist(artist);
		artist.setGalleryRegistration(reg);
		
		Artwork art = new Artwork();
		artist.getArtwork().add(art);
		
		art.getViewers().add(customer);
		customer.getBrowsedArtwork().add(art);
		
		Purchase purchase = new Purchase();
		art.setPurchase(purchase);
		purchase.setArtwork(art);
		
		
		String name="still life in donuts";
		String description="no need";
		double price=100.2;
		ArtworkStatus status = ArtworkStatus.AVAILABLE;
		boolean onsite=false;
		int numViews=100;
		String dimensions="11'x17'";
		double weight=12.0;
		double shippingCost=34.2;
		
		art.setName(name);
		art.setDescription(description);
		art.setPrice(price);
		art.setStatus(status);
		art.setOnSite(onsite);
		art.setNumViews(numViews);
		art.setDimension(dimensions);
		art.setWeight(weight);
		art.setShippingCost(shippingCost);
		
		artRepo.save(art);
		
		Long artId=art.getArtworkId();
		Long customerId=customer.getCustomerId();
		Long artistId=artist.getArtistId();
		Long purchaseId=purchase.getPurchaseId();
		
		art=null;
		customer=null;
		artist=null;
		
		//checking art attributes
		art=artRepo.findArtworkByArtworkId(artId);
		assertNotNull(art);
		assertEquals(art.getName(),name);
		assertEquals(art.getDescription(),description);
		assertEquals(art.getStatus(),status);
		assertEquals(art.isOnSite(),onsite);
		assertEquals(art.getNumViews(),numViews);
		assertEquals(art.getDimension(),dimensions);
		assertEquals(art.getWeight(),weight);
		assertEquals(art.getShippingCost(),shippingCost);
		
		//checking associations from art
		customer=art.getViewers().iterator().next();
		assertEquals(customer.getCustomerId(),customerId);
		
		purchase=art.getPurchase();
		assertEquals(purchase.getPurchaseId(),purchaseId);
		
		//checking associations to art
		customer=custRepo.findCustomerByCustomerId(customerId);
		art=customer.getBrowsedArtwork().iterator().next();
		assertNotNull(art);
		assertEquals(art.getName(),name);
		assertEquals(art.getDescription(),description);
		assertEquals(art.getStatus(),status);
		assertEquals(art.isOnSite(),onsite);
		assertEquals(art.getNumViews(),numViews);
		assertEquals(art.getDimension(),dimensions);
		assertEquals(art.getWeight(),weight);
		assertEquals(art.getShippingCost(),shippingCost);
		
		artist=artistRepo.findArtistByArtistId(artistId);
		art=artist.getArtwork().iterator().next();
		assertNotNull(art);
		assertEquals(art.getName(),name);
		assertEquals(art.getDescription(),description);
		assertEquals(art.getStatus(),status);
		assertEquals(art.isOnSite(),onsite);
		assertEquals(art.getNumViews(),numViews);
		assertEquals(art.getDimension(),dimensions);
		assertEquals(art.getWeight(),weight);
		assertEquals(art.getShippingCost(),shippingCost);
		
		purchase=purchaseRepo.findPurchaseByPurchaseId(purchaseId);
		art=purchase.getArtwork();
		assertNotNull(art);
		assertEquals(art.getName(),name);
		assertEquals(art.getDescription(),description);
		assertEquals(art.getStatus(),status);
		assertEquals(art.isOnSite(),onsite);
		assertEquals(art.getNumViews(),numViews);
		assertEquals(art.getDimension(),dimensions);
		assertEquals(art.getWeight(),weight);
		assertEquals(art.getShippingCost(),shippingCost);
		
	}
	
	@Test 
	public void testCustomer() {
		GalleryRegistration reg=new GalleryRegistration();
		String userName="bananaFartman15";		
		reg.setUserName(userName);
		
		Customer customer = new Customer();
		String bankInfo="has money";
		customer.setBankInfo(bankInfo);
		
		reg.setCustomer(customer);
		customer.setGalleryRegistration(reg);
		
		Artwork art = new Artwork();
		Purchase purchase = new Purchase();
		
		customer.getPurchase().add(purchase);
		
		customer.getBrowsedArtwork().add(art);
		art.getViewers().add(customer);
		
			// still has to do shipment
		
		custRepo.save(customer);

		Long custId=customer.getCustomerId();
		Long artId=art.getArtworkId();
		Long purchaseId=purchase.getPurchaseId();
		
		customer=null;
		art=null;
		purchase=null;
		reg=null;
		
		customer=custRepo.findCustomerByCustomerId(custId);

		//test customer attributes
		assertNotNull(customer);
		assertEquals(customer.getBankInfo(),bankInfo);
		
		//test associations from customer
		art=customer.getBrowsedArtwork().iterator().next();
		assertEquals(art.getArtworkId(),artId);

		purchase=customer.getPurchase().iterator().next();
		assertEquals(purchase.getPurchaseId(),purchaseId);
		
			//still needs to do shipment
		
		//test associations to customer
		reg=regRepo.findGalleryRegisrationByUserName(userName);
		customer=reg.getCustomer();
		assertNotNull(customer);
		assertEquals(customer.getBankInfo(),bankInfo);
		
		art=artRepo.findArtworkByArtworkId(artId);
		customer=art.getViewers().iterator().next();
		assertNotNull(customer);
		assertEquals(customer.getBankInfo(),bankInfo);
		
		
		
		
	}
	
	
	
}
