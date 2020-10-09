package ca.mcgill.ecse321.onlinegallery.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.HashSet;
import java.util.Set;
import java.sql.Date;
import java.time.LocalDate;
import java.time.Month;

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
	private ArtworkRepository artworkRepo;
	
	@Autowired 
	private GalleryAdminRepository adminRepo;
	
	@Autowired
	private PurchaseRepository purchaseRepo;

	@Autowired
	private ProfileRepository profileRepo;
	
	@Autowired
	private ShipmentRepository shipmentRepo;
	
	@Autowired 
	private ArtistRepository artistRepo;
	
	@Autowired
	private CustomerRepository custRepo;
	
	@Autowired
	private PhysicalGalleryRepository pgRepo;
	
	@AfterEach
	public void clearDatabase() {
		onlineRepo.deleteAll();
		regRepo.deleteAll();
		artistRepo.deleteAll();
		custRepo.deleteAll();
		pgRepo.deleteAll();
	}
	
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
		Date date = java.sql.Date.valueOf(LocalDate.of(2020, Month.JANUARY, 31));
		Boolean paid = false;

		
		
		purchase.setCommission(commission);
		purchase.setShipmentType(shipmentType);
		purchase.setPaymentMethod(paymentMethod);
		purchase.setDate(date);
		purchase.setPaid(paid);
		
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
		assertEquals(date,purchase.getDate());
		assertEquals(paid,purchase.getPaid());
		
		//checking associations
		assertEquals(artworkId,purchase.getArtworkOrdered().getArtworkId());
		assertEquals(customerId,purchase.getCustomer().getCustomerId());
		assertEquals(shipmentId,purchase.getShipment().getShipmentId());
		assertEquals(ogId,purchase.getOnlineGallery().getSystemId());
		
		
		
	}

	@Test
	public void testPersistingAndLoadingProfileAttributesAndAssociations() {
		
		//Upstream of Profile in composition tree
		OnlineGallery og = new OnlineGallery();
		
		GalleryRegistration gr = new GalleryRegistration();
		String userName="RedditUser01101001";
		gr.setUserName(userName);
		
		Artist artist = new Artist();
		Profile profile = new Profile();
		
		// setting up all the associations
		Set<GalleryRegistration> allGR = new HashSet<GalleryRegistration>();
		allGR.add(gr);
		
		og.setAllRegistrations(allGR);
		gr.setOnlineGallery(og);
		
		gr.setGalleryArtist(artist);
		artist.setGalleryRegistration(gr);
		
		profile.setArtist(artist);
		artist.setProfile(profile);
		
		//Setting attributes of Profile
		int numSold = 9001;
		double totalEarnings = -69;
		String selfDescription = "Scrooge McDuck";
		double rating = 420;
		
		profile.setNumSold(numSold);
		profile.setRating(rating);
		profile.setSelfDescription(selfDescription);
		profile.setTotalEarnings(totalEarnings);
		
		onlineRepo.save(og);								
		regRepo.save(gr);
		
		
		// getting primary keys of all the objects associated with profile + profile itself
		Long profileId = profile.getProfileId();
		Long artistId = artist.getArtistId();
		
		//losing everything
		profile = null;
		artist = null;
	
		//retrieve from database
		profile = profileRepo.findProfileByProfileId(profileId);
		
		//checking attributes
		assertEquals(profileId, profile.getProfileId());
		assertEquals(numSold, profile.getNumSold());
		assertEquals(totalEarnings, profile.getTotalEarnings());
		assertEquals(selfDescription, profile.getSelfDescription());
		assertEquals(rating, profile.getRating());
		
		//checking associations
		assertEquals(artistId, profile.getArtist().getArtistId());		

	}
	
	@Test
	public void testPersistingAndLoadingShipmentAttributesAndAssociations() {
		
		//Upstream of Shipment in composition tree + mandatory associations
		OnlineGallery og = new OnlineGallery();
		GalleryRegistration gr = new GalleryRegistration();
		String userName="4ChanUser10010110";
		gr.setUserName(userName);
		
		Artist artist = new Artist();	
		Artwork artwork = new Artwork();
		Customer customer=new Customer();					
		Profile profile = new Profile();
		Shipment shipment = new Shipment();
		Purchase purchase = new Purchase();
		
		//Setting up all the associations
		
		Set<GalleryRegistration> allRegistrations = new HashSet<GalleryRegistration>();
		allRegistrations.add(gr);
		
		Set<Artwork> artworks = new HashSet<Artwork>();
		artworks.add(artwork);
		
		Set<Purchase> purchases = new HashSet<Purchase>();
		purchases.add(purchase);
		
		Set<Shipment> allShipments = new HashSet<Shipment>();
		allShipments.add(shipment);
		
		Set<Purchase> allPurchases = new HashSet<Purchase>();
		allPurchases.add(purchase);
		
		og.setAllPurchases(allPurchases);
		og.setAllRegistrations(allRegistrations);
		og.setAllShipments(allShipments);
		
		purchase.setOnlineGallery(og);
		gr.setOnlineGallery(og);
		shipment.setOnlineGallery(og);
		
		gr.setGalleryCustomer(customer);
		customer.setGalleryRegistration(gr);
		
		customer.setPurchases(purchases);
		purchase.setCustomer(customer);
		
		purchase.setArtworkOrdered(artwork);
		artwork.setPurchase(purchase);
		
		artwork.setArtist(artist);
		artist.setArtworks(artworks);
		
		artist.setProfile(profile);
		profile.setArtist(artist);
		
		purchase.setShipment(shipment);
		shipment.setPurchases(purchases);
		
		artist.setGalleryRegistration(gr);
		gr.setGalleryArtist(artist);
		
		//Setting attributes of the shipment
		String sourceAddress = "EzClap Pepega Road 7777777";
		String destinationAddress = "Much WOW Jebaited L_ 420420420";
		double totalAmount = 69696969696969.0;
		String shippingCompany = "You'll Never Get Your Shipment LULW";
		ShipmentStatus status = ShipmentStatus.SHIPPED;
		String reciepientName = "Eminem";
		
		shipment.setSourceAddress(sourceAddress);
		shipment.setDestinationAddress(destinationAddress);
		shipment.setTotalAmount(totalAmount);
		shipment.setShippingCompany(shippingCompany);
		shipment.setStatus(status);
		shipment.setReceipientName(reciepientName);
	
		onlineRepo.save(og);								
		regRepo.save(gr);
		
		//Getting primary keys of all the objects associated with shipment + shipment itself
		Long purchaseId=purchase.getPurchaseId();		
		Long shipmentId=shipment.getShipmentId();
		Long ogId=og.getSystemId();

		//Losing everything
		purchase=null;
		shipment=null;
		og=null;
		
		//retrieve from database
		shipment = shipmentRepo.findShipmentByShipmentId(shipmentId);
		
		//checking attributes
		assertEquals(shipmentId, shipment.getShipmentId());
		assertEquals(sourceAddress, shipment.getSourceAddress());
		assertEquals(destinationAddress, shipment.getDestinationAddress());
		assertEquals(totalAmount, shipment.getTotalAmount());
		assertEquals(shippingCompany, shipment.getShippingCompany());
		assertEquals(status, shipment.getStatus());
		assertEquals(reciepientName, shipment.getReceipientName());
		
		//checking associations
		assertEquals(ogId, shipment.getOnlineGallery().getSystemId());
		assertEquals(purchaseId, shipment.getPurchases().iterator().next().getPurchaseId());
		
	}
	// Artist test
		@Test
		public void testPersistingAndLoadingArtistyAttributesAndAssociations() {
			
			Artist artist = new Artist();
			// creating other classes for association testing
			OnlineGallery og = new OnlineGallery();
			GalleryRegistration reg = new GalleryRegistration();
			Profile profile = new Profile();
			Artwork art = new Artwork();
			
			// properties/attributes
			reg.setUserName("CamNewton");
			String bankInfo = "TD";
			artist.setBankInfo(bankInfo);
			
			// creating the sets
			// artwork
			Set<Artwork> allArt = new HashSet<Artwork>();
			allArt.add(art);
			// GalleryRegistration
			Set<GalleryRegistration> allReg = new HashSet<GalleryRegistration>();
			allReg.add(reg);
			
			// setting associations
			// gallery registration
			artist.setGalleryRegistration(reg);
			reg.setGalleryArtist(artist);

			// profile
			artist.setProfile(profile);
			profile.setArtist(artist);
			// artwork
			artist.setArtworks(allArt);
			art.setArtist(artist);
			// online gallery
			og.setAllRegistrations(allReg);
			reg.setOnlineGallery(og);
			
			// persist
			onlineRepo.save(og);
			regRepo.save(reg);
			artistRepo.save(artist);
			
			// storing values locally for testing
			Long systemID = og.getSystemId();
			Long artistID = artist.getArtistId();
			String regID = reg.getUserName();
			Long artworkID = art.getArtworkId();
			Long profileID = profile.getProfileId();
			
			// clear artist, and set it equal to that of the repo (persistence)
			artist = null;
			artist = artistRepo.findArtistByArtistId(artistID);
			
			// tests
			assertNotNull(artist);
			
			assertEquals(bankInfo, artist.getBankInfo());
			assertEquals(artistID, artist.getArtistId());
			
			assertEquals(systemID, artist.getGalleryRegistration().getOnlineGallery().getSystemId());
			assertEquals(regID, artist.getGalleryRegistration().getUserName());
			assertEquals(artworkID, artist.getArtworks().iterator().next().getArtworkId());
			assertEquals(profileID, artist.getProfile().getProfileId());
		}
		// OnlineGallery test
		@Test
		public void testPersistingAndLoadingGalleryAttributesAndAssociations() {
			
			OnlineGallery og = new OnlineGallery();
			// creating other classes for associations testing
			PhysicalGallery pg = new PhysicalGallery();
			Shipment shipment = new Shipment();
			Purchase purchase = new Purchase();
			GalleryRegistration reg = new GalleryRegistration();
			
			// properties/attributes
			reg.setUserName("josh");
			int days = 100;
			og.setDaysUp(days);
			
			// creating the sets 
			Set<Shipment> allShipment = new HashSet<Shipment>();
			allShipment.add(shipment);
			
			Set<Purchase> allPurchase = new HashSet<Purchase>();
			allPurchase.add(purchase);
			
			Set<GalleryRegistration> allReg = new HashSet<GalleryRegistration>();
			allReg.add(reg);
			
			// setting associations
			// physical gallery
			pg.setOnlineGallery(og);
			og.setPhysicalGallery(pg);
			// shipments
			og.setAllShipments(allShipment);
			shipment.setOnlineGallery(og);
			// purchases
			og.setAllPurchases(allPurchase);
			purchase.setOnlineGallery(og);
			// registrations
			og.setAllRegistrations(allReg);
			reg.setOnlineGallery(og);
			
			// persist the OnlineGallery object
			onlineRepo.save(og);
			
			// storing values for testing
			Long systemID = og.getSystemId();
			Long pgID = pg.getGalleryId();
			Long shipmentID = shipment.getShipmentId();
			Long purchaseID = purchase.getPurchaseId();
			String regID = reg.getUserName();
			
			// clear gallery, and set it equal to that of the repo (persistence)
			og = null;
			og = onlineRepo.findOnlineGalleryBySystemId(systemID);
			
			//tests
			assertEquals(pgID, og.getPhysicalGallery().getGalleryId());
			assertEquals(shipmentID, og.getAllShipments().iterator().next().getShipmentId());
			assertEquals(purchaseID, og.getAllPurchases().iterator().next().getPurchaseId());
			assertEquals(regID, og.getAllRegistrations().iterator().next().getUserName());
			assertEquals(days, og.getDaysUp());
			assertEquals(systemID, og.getSystemId());
		}
		
		@Test
		public void testPersistingAndLoadingCustomerAttributesAndAssociations() {
			
			OnlineGallery og = new OnlineGallery();
			GalleryRegistration reg = new GalleryRegistration();
			String userName="natalia";
			reg.setUserName(userName);
			
			Artist artist = new Artist();
			Artwork art  = new Artwork();
			Profile profile = new Profile ();
			Purchase purchase = new Purchase();
			
			Customer customer = new Customer();
			String bankInfo = "royal";
			customer.setBankInfo(bankInfo);
			
			Set<GalleryRegistration> allReg = new HashSet<GalleryRegistration>();
			allReg.add(reg);
			
			Set<Customer> allCostumers = new HashSet<Customer>();
			allCostumers.add(customer);
			
			Set<Purchase> allPurchases = new HashSet<Purchase>();
			allPurchases.add(purchase);
			
			Set<Artwork> allArtworks = new HashSet<Artwork>();
			allArtworks.add(art);
			
			og.setAllRegistrations(allReg);
			reg.setOnlineGallery(og);
			
			og.setAllPurchases(allPurchases);
			purchase.setOnlineGallery(og);
			
			reg.setGalleryCustomer(customer);
			customer.setGalleryRegistration(reg);
			
			reg.setGalleryArtist(artist);
			artist.setGalleryRegistration(reg);
			
			artist.setProfile(profile);
			profile.setArtist(artist);
			
			artist.setArtworks(allArtworks);
			art.setArtist(artist);
		
			customer.setBrowseArtworks(allArtworks);
			art.setViewers(allCostumers);
			
			customer.setPurchases(allPurchases);
			purchase.setCustomer(customer);
			
			onlineRepo.save(og);
			regRepo.save(reg);
			
			Long artworkUniqueId = art.getArtworkId();			
			Long customerUniqueId=customer.getCustomerId();		
			Long purchaseUniqueId = purchase.getPurchaseId();
			
			
			customer = null;
			
			customer = custRepo.findCustomerByBankInfo(bankInfo);
			
			assertNotNull(customer);
			assertEquals(bankInfo, customer.getBankInfo());
			assertEquals(customerUniqueId, customer.getCustomerId());
			
			//test association
			assertEquals(userName, customer.getGalleryRegistration().getUserName());
			assertEquals(allPurchases.size(), customer.getPurchases().size());
			for (Purchase p : allPurchases) {
				assertEquals(purchaseUniqueId, p.getPurchaseId());
			}
			for (Artwork a : allArtworks) {
				assertEquals(artworkUniqueId, a.getArtworkId());
			}
			
			
		}
		
		@Test
		public void testPersistingAndLoadingPhysicalGalleryAttributesAndAssociations() {
			PhysicalGallery pg = new PhysicalGallery();
			OnlineGallery og = new OnlineGallery();
			
			
			
			String pgAddress = "popayan";
			pg.setAddress(pgAddress);
			//pgRepo.save(pg);
			
			
			int ogDaysUp = 100;
			og.setDaysUp(ogDaysUp);
			
			og.setPhysicalGallery(pg);
			pg.setOnlineGallery(og);
			
			onlineRepo.save(og);
			pgRepo.save(pg);
			Long pgID = pg.getGalleryId();
			Long ogID = og.getSystemId();
			
			pg = null;
			
			pg = pgRepo.findPhysicalGalleryByAddress(pgAddress);
			assertNotNull(pg);
			assertEquals(pgAddress, pg.getAddress());
			assertEquals(pgID, pg.getGalleryId());
			//test association
			assertNotNull(pg.getOnlineGallery());
			assertEquals(pg.getOnlineGallery().getSystemId(), og.getSystemId());
			assertEquals(pg.getOnlineGallery().getDaysUp(), og.getDaysUp());
			
			
			pg = null;
			
			pg = pgRepo.findPhysicalGalleryByGalleryId(pgID);
			assertNotNull(pg);
			assertEquals(pgAddress, pg.getAddress());
			assertEquals(pgID, pg.getGalleryId());
			//test association
			assertNotNull(pg.getOnlineGallery());
			assertEquals(pg.getOnlineGallery().getSystemId(), og.getSystemId());
			assertEquals(pg.getOnlineGallery().getDaysUp(), og.getDaysUp());
			
		}
		
		@Test
		public void testPersistingAndLoadingArtworkAttributesAndAssociations() {
			
			//instantiating needed classes
			OnlineGallery og = new OnlineGallery();
			GalleryRegistration reg = new GalleryRegistration();
			Customer customer = new Customer();
			Purchase purchase = new Purchase();
			Artist artist = new Artist();
			Artwork artwork = new Artwork();
			Profile profile = new Profile();
			
			String userName="LisaSimpson"; //not sure if necessary
			reg.setUserName(userName);

			Set<GalleryRegistration> allReg = new HashSet<GalleryRegistration>();
			allReg.add(reg);
			
			Set<Customer> allCustomer = new HashSet<Customer>();
			allCustomer.add(customer);
			
			Set<Purchase> allPurchase = new HashSet<Purchase>();
			allPurchase.add(purchase);
			
			Set<Artist> allArtists = new HashSet<Artist>();
			allArtists.add(artist);
			
			Set<Artwork> allArt = new HashSet<Artwork>();
			allArt.add(artwork);
			
			//setting associations
			og.setAllRegistrations(allReg);
			reg.setOnlineGallery(og);
			
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
			
			customer.setBrowseArtworks(allArt); //not sure about this
			artwork.setViewers(allCustomer);
			
			//setting attributes
			String artworkname = "Masterpiece";
			String description = "so beautiful";
			double price = 1000;
			ArtworkStatus status = ArtworkStatus.AVAILABLE;
			boolean onSite = true;
			int numViews = 25;
			String dimension = "24x48";
			double weight = 10;
			double shippingCost = 50;
			
			artwork.setName(artworkname);
			artwork.setDescription(description);
			artwork.setPrice(price);
			artwork.setStatus(status);
			artwork.setOnSite(onSite);
			artwork.setNumViews(numViews);
			artwork.setDimension(dimension);
			artwork.setWeight(weight);
			artwork.setShippingCost(shippingCost);
			
			//persisting 
			onlineRepo.save(og);
			regRepo.save(reg);
			artworkRepo.save(artwork);
			
			//saving primary
			Long purchaseId = purchase.getPurchaseId();		
			Long customerId = customer.getCustomerId();
			Long artworkId = artwork.getArtworkId();
			Long artistId = artist.getArtistId();
			String regId = reg.getUserName();
			
			og = null;
			reg = null;
			customer = null;
			purchase = null;
			artist = null;
			artwork =null;
			
			//retrieving artwork from db
			artwork = artworkRepo.findArtworkByArtworkId(artworkId);
			
			//testing associations
			assertEquals(artistId, artwork.getArtist().getArtistId());
			assertEquals(artworkId,artwork.getArtworkId());
			assertEquals(purchaseId,artwork.getPurchase().getPurchaseId());
			assertEquals(customerId, artwork.getViewers().iterator().next().getCustomerId());
			//not sure what to do with customer association
			
			//testing attributes
			assertEquals(artworkname, artwork.getName());
			assertEquals(description, artwork.getDescription());
			assertEquals(price, artwork.getPrice());
			assertEquals(status, artwork.getStatus());
			assertEquals(onSite, artwork.getOnSite());
			assertEquals(numViews, artwork.getNumViews());
			assertEquals(dimension, artwork.getDimension());
			assertEquals(weight, artwork.getWeight());
			assertEquals(shippingCost, artwork.getShippingCost());

		}
		
		@Test
		public void testPersistingAndLoadingGalleryAdminAttributesAndAssociations() {
			
			//instantiating needed classes
			GalleryAdmin admin=new GalleryAdmin();
			OnlineGallery og = new OnlineGallery();
			GalleryRegistration reg = new GalleryRegistration();


			//not exactly sure what has sets are needed	
			Set<GalleryRegistration> allReg = new HashSet<GalleryRegistration>();
			allReg.add(reg);
			
			//setting attributes
			reg.setUserName("NedFlanders");
			
			
			//setting associations
			reg.setGalleryAdmin(admin);
			admin.setGalleryRegistration(reg);
			
			og.setAllRegistrations(allReg);
			reg.setOnlineGallery(og);
			
			//persisting
			onlineRepo.save(og);
			regRepo.save(reg);
			adminRepo.save(admin);
		
		
			//storing values
			Long adminId = admin.getAdminId();
			Long systemId = og.getSystemId();
			String regId = reg.getUserName();
			
			//deleting local Objects
			admin = null;
			reg = null;
			og =null;
			
			//retrieving admin from db
			admin = adminRepo.findGalleryAdminByAdminId(adminId);
			
			//assertion tests for attributes
			assertEquals(adminId,admin.getAdminId());
			//assertion tests for associations
			assertEquals(regId,admin.getGalleryRegistration().getUserName());
			assertEquals(systemId,admin.getGalleryRegistration().getOnlineGallery().getSystemId());

		}
}

