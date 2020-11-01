package ca.mcgill.ecse321.onlinegallery.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.assertNotNull;

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
	
	@Autowired
	private ShipmentRepository shipRepo;

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
		Boolean loggedIn= false;
		String password="password";
		
		reg.setUserName(userName);
		reg.setFirstName(firstName);
		reg.setLastName(lastName);
		reg.setEmail(email);
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
		Boolean loggedIn= false;
		String password="password";
		
		reg.setUserName(userName);
		reg.setFirstName(firstName);
		reg.setLastName(lastName);
		reg.setEmail(email);
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
		art.setArtist(artist);
		
		artistRepo.save(artist);
		Long artistId=artist.getArtistId();
		Long pId=p.getProfileId();
		Long artId=art.getArtworkId();
		
		artist=null;
		reg=null;
		p=null;
		art=null;
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
		
		art=artRepo.findArtworkByArtworkId(artId);
		artist=art.getArtist();
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
		art.setArtist(artist);
		
		
		Purchase purchase = new Purchase();
		art.setPurchase(purchase);
		purchase.setArtwork(art);
		
		
		String name="still life in donuts";
		String description="no need";
		double price=100.2;
		ArtworkStatus status = ArtworkStatus.AVAILABLE;
		int numViews=100;
		String dimensions="11'x17'";
		double weight=12.0;
		double commission=0.2;
		
		art.setName(name);
		art.setDescription(description);
		art.setPrice(price);
		art.setStatus(status);
		art.setNumViews(numViews);
		art.setDimension(dimensions);
		art.setWeight(weight);
		art.setCommission(commission);
		
		artistRepo.save(artist);
		artRepo.save(art);
		
		Long artId=art.getArtworkId();
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
		assertEquals(art.getPrice(),price);
		assertEquals(art.getStatus(),status);
		assertEquals(art.getNumViews(),numViews);
		assertEquals(art.getDimension(),dimensions);
		assertEquals(art.getWeight(),weight);
		assertEquals(art.getComission(),commission);
		
		//checking associations from art
		artist=art.getArtist();
		assertEquals(artist.getArtistId(),artistId);
		
		purchase=art.getPurchase();
		assertEquals(purchase.getPurchaseId(),purchaseId);
		
		//checking associations to art
		artist=artistRepo.findArtistByArtistId(artistId);
		art=artist.getArtwork().iterator().next();
		assertNotNull(art);
		assertEquals(art.getName(),name);
		assertEquals(art.getDescription(),description);
		assertEquals(art.getPrice(),price);
		assertEquals(art.getStatus(),status);
		assertEquals(art.getNumViews(),numViews);
		assertEquals(art.getDimension(),dimensions);
		assertEquals(art.getWeight(),weight);
		assertEquals(art.getComission(),commission);
		
		purchase=purchaseRepo.findPurchaseByPurchaseId(purchaseId);
		art=purchase.getArtwork();
		assertNotNull(art);
		assertEquals(art.getName(),name);
		assertEquals(art.getDescription(),description);
		assertEquals(art.getPrice(),price);
		assertEquals(art.getStatus(),status);
		assertEquals(art.getNumViews(),numViews);
		assertEquals(art.getDimension(),dimensions);
		assertEquals(art.getWeight(),weight);
		assertEquals(art.getComission(),commission);
		
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
		Shipment shipment = new Shipment();
		
		customer.getPurchase().add(purchase);
		purchase.setCustomer(customer);
		
		customer.getShipment().add(shipment);
				
		custRepo.save(customer);

		Long custId=customer.getCustomerId();
		Long purchaseId=purchase.getPurchaseId();
		Long shipmentId=shipment.getShipmentId();
		
		customer=null;
		art=null;
		purchase=null;
		reg=null;
		shipment=null;
		
		customer=custRepo.findCustomerByCustomerId(custId);

		//test customer attributes
		assertNotNull(customer);
		assertEquals(customer.getBankInfo(),bankInfo);
		
		//test associations from customer

		purchase=customer.getPurchase().iterator().next();
		assertEquals(purchase.getPurchaseId(),purchaseId);
		
		shipment=customer.getShipment().iterator().next();
		assertEquals(shipment.getShipmentId(),shipmentId);
		
		
		//test associations to customer
		reg=regRepo.findGalleryRegisrationByUserName(userName);
		customer=reg.getCustomer();
		assertNotNull(customer);
		assertEquals(customer.getBankInfo(),bankInfo);
		
		purchase=purchaseRepo.findPurchaseByPurchaseId(purchaseId);
		customer=purchase.getCustomer();
		assertNotNull(customer);
		assertEquals(customer.getBankInfo(),bankInfo);
		
		
	}
	
	@Test
	public void testPurchase() {
		GalleryRegistration reg=new GalleryRegistration();
		Customer customer = new Customer();
		Artwork art = new Artwork();
		Shipment shipment = new Shipment();
		Purchase purchase = new Purchase();
		
		ShipmentType shipType=ShipmentType.OFFSITE_DELIVERY;
		Date date = java.sql.Date.valueOf(LocalDate.of(2020, Month.JANUARY, 31));
		
		purchase.setShipmentType(shipType);
		purchase.setDate(date);
		
		
		String userName="bananaFartman15";		
		reg.setUserName(userName);
		
		reg.setCustomer(customer);
		
		customer.setGalleryRegistration(reg);
		customer.getPurchase().add(purchase);
		customer.getShipment().add(shipment);
		
		purchase.setCustomer(customer);
		purchase.setArtwork(art);
		art.setPurchase(purchase);
		
		purchase.setShipment(shipment);
		shipment.getPurchase().add(purchase);
		
		custRepo.save(customer);
		purchaseRepo.save(purchase);

		
		Long purchaseId=purchase.getPurchaseId();
		Long artId=art.getArtworkId();
		Long shipId=shipment.getShipmentId();
		Long customerId=customer.getCustomerId();
		
		purchase=null;
		shipment=null;
		art=null;

		//checking purchase attributes
		purchase=purchaseRepo.findPurchaseByPurchaseId(purchaseId);
		assertNotNull(purchase);
		assertEquals(purchase.getShipmentType(),shipType);
		assertEquals(purchase.getDate(),date);
		
		//checking associations from purchase
		assertEquals(purchase.getShipment().getShipmentId(),shipId);
		assertEquals(purchase.getArtwork().getArtworkId(),artId);
		assertEquals(purchase.getCustomer().getCustomerId(),customerId);
		
		//checking associations to purchase
		shipment=shipRepo.findShipmentByShipmentId(shipId);
		purchase=shipment.getPurchase().iterator().next();
		assertNotNull(purchase);
		assertEquals(purchase.getShipmentType(),shipType);
		assertEquals(purchase.getDate(),date);
		
		art=artRepo.findArtworkByArtworkId(artId);
		purchase=art.getPurchase();
		assertNotNull(purchase);
		assertEquals(purchase.getShipmentType(),shipType);
		assertEquals(purchase.getDate(),date);
		
		customer=custRepo.findCustomerByCustomerId(customerId);
		purchase=customer.getPurchase().iterator().next();
		assertNotNull(purchase);
		assertEquals(purchase.getShipmentType(),shipType);
		assertEquals(purchase.getDate(),date);
				
	}
	
	@Test
	public void testShipment() {
		GalleryRegistration reg=new GalleryRegistration();
		Customer customer = new Customer();
		Shipment shipment = new Shipment();
		Purchase purchase = new Purchase();
		
		String sourceAddress="here";
		String destAddress="there";
		double shippingCost=34;
		double totalAmount=24.9;
		ShipmentStatus status=ShipmentStatus.CREATED;
		String recipientName="nemo";
		
		String ccNumber="2344 1115 3335 6666";
		String ccFirstname="John";
		String ccLastname="Smith";
		
		
		shipment.setSourceAddress(sourceAddress);
		shipment.setDestinationAddress(destAddress);
		shipment.setShippingCost(shippingCost);
		shipment.setTotalAmount(totalAmount);
		shipment.setShipmentStatus(status);
		shipment.setRecipientName(recipientName);
		
		shipment.setCreditCardNumber(ccNumber);
		shipment.setCreditCardFirstName(ccFirstname);
		shipment.setCreditCardLastName(ccLastname);
		
		String userName="bananaFartman15";		
		reg.setUserName(userName);
		
		reg.setCustomer(customer);
		
		customer.setGalleryRegistration(reg);
		customer.getPurchase().add(purchase);
		customer.getShipment().add(shipment);

		purchase.setCustomer(customer);
		purchase.setShipment(shipment);
		shipment.getPurchase().add(purchase);

		custRepo.save(customer);
		shipRepo.save(shipment);

		
		Long purchaseId=purchase.getPurchaseId();
		Long shipId=shipment.getShipmentId();
		Long customerId=customer.getCustomerId();
		
		purchase=null;
		shipment=null;
		customer=null;

		//checking shipment attributes
		shipment=shipRepo.findShipmentByShipmentId(shipId);
		assertNotNull(shipment);
		assertEquals(shipment.getSourceAddress(),sourceAddress);
		assertEquals(shipment.getDestinationAddress(),destAddress);
		assertEquals(shipment.getShippingCost(),shippingCost);
		assertEquals(shipment.getTotalAmount(),totalAmount);
		assertEquals(shipment.getShipmentStatus(),status);
		assertEquals(shipment.getRecipientName(),recipientName);
		
		assertEquals(shipment.getCreditCardNumber(),ccNumber);
		assertEquals(shipment.getCreditCardFirstName(),ccFirstname);
		assertEquals(shipment.getCreditCardLastName(),ccLastname);

		
		//checking assocations from shipment
		assertEquals(shipment.getPurchase().iterator().next().getPurchaseId(),purchaseId);
		
		//checking associations to shipment
		purchase=purchaseRepo.findPurchaseByPurchaseId(purchaseId);
		shipment=purchase.getShipment();
		assertNotNull(shipment);
		assertEquals(shipment.getSourceAddress(),sourceAddress);
		assertEquals(shipment.getDestinationAddress(),destAddress);
		assertEquals(shipment.getShippingCost(),shippingCost);
		assertEquals(shipment.getTotalAmount(),totalAmount);
		assertEquals(shipment.getShipmentStatus(),status);
		assertEquals(shipment.getRecipientName(),recipientName);
		
		assertEquals(shipment.getCreditCardNumber(),ccNumber);
		assertEquals(shipment.getCreditCardFirstName(),ccFirstname);
		assertEquals(shipment.getCreditCardLastName(),ccLastname);

		
		customer=custRepo.findCustomerByCustomerId(customerId);
		shipment=customer.getShipment().iterator().next();
		assertNotNull(shipment);
		assertEquals(shipment.getSourceAddress(),sourceAddress);
		assertEquals(shipment.getDestinationAddress(),destAddress);
		assertEquals(shipment.getShippingCost(),shippingCost);
		assertEquals(shipment.getTotalAmount(),totalAmount);
		assertEquals(shipment.getShipmentStatus(),status);
		assertEquals(shipment.getRecipientName(),recipientName);
		
		assertEquals(shipment.getCreditCardNumber(),ccNumber);
		assertEquals(shipment.getCreditCardFirstName(),ccFirstname);
		assertEquals(shipment.getCreditCardLastName(),ccLastname);

				
	}
	
	
	
}
