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
	
	@Autowired
	private PurchaseRepository purchaseRepo;
	
	
	
//	@AfterEach
//	public void clearDatabase() {
//		onlineRepo.deleteAll();
//		regRepo.deleteAll();
//	}
	
	@Test
	public void testPersistingAndLoadingRegistrationAttributesAndAssociations() {
		
		// boilerplate to propagate the persistence OnlineGallery which lines
		// upstream of GalleryRegistration in the composition tree
		
		OnlineGallery og = new OnlineGallery();
		GalleryRegistration reg = new GalleryRegistration();
		
		// boiler plate to propagate the persistence of the classes that are 
		// downstream of GalleryRegistration in the composition tree, which
		// also happens to be the only direct association type that GalleryRegistration has: compositions
		
		Artist artist = new Artist();						//these objects all generate primary keys internally
		Customer customer=new Customer();					//but if you call object.getObjectID right now it would retur null
		GalleryAdmin admin = new GalleryAdmin();			//because those keys aren't generated until the obj is saved!
		
		Profile profile = new Profile();					//this Profile class is created because Artist must have a Profile
		
		
		// setting up all the bidirectional associations before persisting
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
		
		// setting attributes of GalleryRegistration prior to persisting everything
		
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
		
		// persist OnlineGallery first
		onlineRepo.save(og);								// persisting GalleryRegistration also persist all classes in its associations
		regRepo.save(reg);
		
		Long artistUniqueId = artist.getArtistId();			// get the unique id generated when each object is persisted for comparison later		
		Long customerUniqueId=customer.getCustomerId();		
		Long adminUniqueId = admin.getAdminId();
		Long ogUniqueId=og.getSystemId();
		
		
		// null everything
		reg=null;
		artist=null;
		customer=null;
		admin=null;
		
		// load back the saved GalleryRegistration
		reg=regRepo.findGalleryRegisrationByUserName(userName);
		
		// test attributes
		
		assertEquals(userName,reg.getUserName());
		assertEquals(firstName,reg.getFirstName());
		assertEquals(lastName,reg.getLastName());
		assertEquals(email,reg.getEmail());
		assertEquals(phoneNumber,reg.getPhoneNumber());
		assertEquals(passWord,reg.getPassword());
		assertEquals(isLoggedIn,reg.getIsLoggedIn());	
		
		//test associations
		assertEquals(artistUniqueId,reg.getGalleryArtist().getArtistId());
		assertEquals(customerUniqueId,reg.getGalleryCustomer().getCustomerId());
		assertEquals(adminUniqueId,reg.getGalleryAdmin().getAdminId());
		assertEquals(ogUniqueId,reg.getOnlineGallery().getSystemId());
	}
	
	@Test
	public void testPersistingAndLoadingPurchaseAttributesAndAssociations() {
		
		
		// boilerplate to propagate the persistence through all the classes that lie upstream
		// of the Purchase class in the composition tree
		
		OnlineGallery og = new OnlineGallery();
		GalleryRegistration reg = new GalleryRegistration();
		String userName="donutLover70";
		reg.setUserName(userName);
		
		Artist artist = new Artist();	
		Artwork artwork = new Artwork();
		Customer customer=new Customer();					
		Profile profile = new Profile();
		Shipment shipment = new Shipment();
		Purchase purchase = new Purchase();

		
		Set<GalleryRegistration> allReg = new HashSet<GalleryRegistration>();
		allReg.add(reg);
		
		Set<Artwork> allArt = new HashSet<Artwork>();
		allArt.add(artwork);
		
		Set<Purchase> allPurchase = new HashSet<Purchase>();
		allPurchase.add(purchase);
		
		Set<Shipment> allShipment = new HashSet<Shipment>();
		allShipment.add(shipment);
		
		Set<Customer> allCustomer = new HashSet<Customer>();
		allCustomer.add(customer);
		
		
		og.setAllRegistrations(allReg);
		reg.setOnlineGallery(og);
		
		og.setAllShipments(allShipment);
		shipment.setOnlineGallery(og);
		
		og.setAllPurchases(allPurchase);
		purchase.setOnlineGallery(og);
		

		reg.setGalleryCustomer(customer);
		customer.setGalleryRegistration(reg);
		
		reg.setGalleryArtist(artist);
		artist.setGalleryRegistration(reg);
		
		artist.setProfile(profile);
		profile.setArtist(artist);
		
	
		artist.setArtworks(allArt);
		artwork.setArtist(artist);


		
		purchase.setArtworkOrdered(artwork);
		artwork.setPurchase(purchase);
		
				
		purchase.setCustomer(customer);
		customer.setPurchases(allPurchase);
		
		
		purchase.setShipment(shipment);
		shipment.setPurchases(allPurchase);
		
		// now setting the attributes of the purchase itself for asserting later
		
		double commission=0.314;
		ShipmentType shipmentType = ShipmentType.ONSITE_PICKUP;
		PaymentMethod paymentMethod = PaymentMethod.WIRETRANSFER;
		
		purchase.setCommission(commission);
		purchase.setShipmentType(shipmentType);
		purchase.setPaymentMethod(paymentMethod);
		
		onlineRepo.save(og);								
		regRepo.save(reg);
		
		// get the primary key of all the objects associated with purchase

		Long purchaseId=purchase.getPurchaseId();		
		Long customerId=customer.getCustomerId();
		Long artworkId=artwork.getArtworkId();
		Long shipmentId=shipment.getShipmentId();
		Long ogId=og.getSystemId();
		
		// null everything
		
		purchase=null;
		purchase=null;
		artwork=null;
		customer=null;
		shipment=null;
		og=null;
		
		
		
		//retrieve purchase from db
		purchase=purchaseRepo.findPurchaseByPurchaseId(purchaseId);
	
		//checking attributes
		assertEquals(purchaseId,purchase.getPurchaseId());
		assertEquals(commission,purchase.getCommission());
		assertEquals(shipmentType.name(),purchase.getShipmentType().name());
		assertEquals(paymentMethod.name(),purchase.getPaymentMethod().name());
		
		//checking associations
		assertEquals(artworkId,purchase.getArtworkOrdered().getArtworkId());
		assertEquals(customerId,purchase.getCustomer().getCustomerId());
		assertEquals(shipmentId,purchase.getShipment().getShipmentId());
		assertEquals(ogId,purchase.getOnlineGallery().getSystemId());
		
		
		
	}
}

