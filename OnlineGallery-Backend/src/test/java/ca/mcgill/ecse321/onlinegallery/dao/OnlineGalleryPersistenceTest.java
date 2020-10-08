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

///////////////////////////////////////////////////////////////////////////////////
//      use this as a starter file to write your persistence tests               //
//              instructions and details in comments below                      //
///////////////////////////////////////////////////////////////////////////////////

// you may see that there are already many Crud Repository interfaces in the dao package
// don't get too excited. they are all empty. but even so, they can already save. I created them in helping
// debugging and rewriting the JPA annotations

// also, whenever you write something new, always good idea to run the default
// src/test/java/ca.mcgill.ecse321.onlinegallery.OnlineGalleryApplicationTests.java as a junit test to make 
// sure everything can start up. many times even that will fail ...

// anyways ...

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class OnlineGalleryPersistenceTest {

	// notice how i didn't import a Crud Repo interface for every class?
	// if you look at the JPA annotations in the classes themselves there are notations like CascadeStyle.ALL
	// that means the persistence is cascaded from the owner class of an association to the owned class
	// for examle, the model is set up such that GalleryRegistration is the owner of all of its associations
	// with Customer, Artist, Admin. By saving GallerRegistration, all those others will also be saved
	
	
	@Autowired
	private GalleryRegistrationRepository regRepo;
	
	@Autowired 
	private OnlineGalleryRepository onlineRepo;
	
	
//	@AfterEach
//	public void clearDatabase() {
//		// i didn't bother with emptying the db because I was only working getting persisting to work
		// you should refer to the tutorial for this part. you SHOULD clear the db every test to 
		// avoid inconsistencies
//	}

	
	// here we go with the actual useful stuff
	
	@Test
	public void testPersisting() {
		
		// this is a kind of one-size-fits all method that tests writing everything
		// you should pick and choose what you need
		// but know that you must test writing the classed to you itself, as well
		// as all its associations.
		
		// for classes that have optional associations, start with just testing the 
		// class itself with 0 classes associated with it
		
		// after you are comfortable, test the associations. more details on the 
		// associations after
		
		
		
		// just instantiating all the classes that we will be persisting
		OnlineGallery og = new OnlineGallery();
		PhysicalGallery pg = new PhysicalGallery();
		Shipment shipment = new Shipment();
		Purchase purchase = new Purchase();
		GalleryAdmin admin=new GalleryAdmin();
		GalleryRegistration reg = new GalleryRegistration();
		Customer customer = new Customer();
		Artist artist = new Artist();
		Profile profile = new Profile();
		Artwork art= new Artwork();

		
		// setting some of their property for viewing in debugging if you have psql
		reg.setUserName("donutLover69");
		art.setName("still life in blue");
		profile.setSelfDescription("starving artist");
		artist.setBankInfo("no account, no money");
		customer.setBankInfo("lots of money, lots of accounts");
		og.setDaysUp(100);
		pg.setAddress("100 art road");
		shipment.setShippingCompany("massimo trucking");
		
		//some of the associations between classes, especially 1 -- *, takes a set as a input
		//for example see the relationship between OnlineGallery and GalleryRegistration
		//here just making the sets for later
		Set<Purchase> allPurchase = new HashSet<Purchase>();
		allPurchase.add(purchase);
		
		Set<Shipment> allShipment = new HashSet<Shipment>();
		allShipment.add(shipment);
		
		Set<GalleryRegistration> allReg = new HashSet<GalleryRegistration>();
		allReg.add(reg);
		
		
		// now the fun starts
		
		// I am doing the root of the system, OnlineGallery, first. 
		// The JPA is set up so that OnlineGallery is the owning class of all of its associations
		// see the diagram. OnlineGallery is associated with Purchases, Shipments, Registration, 
		// and PhysicalGallery. all associations are bidirectional
		
		// notice how i am setting both ends of every association?
		og.setAllRegistrations(allReg);
		reg.setOnlineGallery(og);
		
		reg.setGalleryAdmin(admin);				//this is jumping ahead to the Registration - Admin, but the idea is same
		admin.setGalleryRegistration(reg);
		
		og.setAllShipments(allShipment);
		shipment.setOnlineGallery(og);
		
		og.setAllPurchases(allPurchase);
		purchase.setOnlineGallery(og);
		
		og.setPhysicalGallery(pg);
		pg.setOnlineGallery(og);
		
		//AFTER you've set the association on both ends
		// persist the OnlineGallery object
		// all associated objects are also by Cascading persisted!
		onlineRepo.save(og);
		
		
		// same idea for the next level in the composition
		// OnlineGallery was top composition level
		// now move on to Registration
		// same pattern, see if you can follow
		Set<Customer> allCustomers = new HashSet<Customer>();
		allCustomers.add(customer);
		
		Set<Artist> allArtists = new HashSet<Artist>();
		allArtists.add(artist);
		
		Set<Artwork> allArt = new HashSet<Artwork>();
		allArt.add(art);
		
		
		reg.setGalleryAdmin(admin);
		admin.setGalleryRegistration(reg);
		
		reg.setGalleryCustomers(allCustomers);
		customer.setGalleryRegistration(reg);
		
		reg.setGalleryArtists(allArtists);
		artist.setGalleryRegistration(reg);
		
		artist.setProfile(profile);
		profile.setArtist(artist);
		
		artist.setArtworks(allArt);
		art.setArtist(artist);
		

		purchase.setArtworkOrdered(art);
		art.setPurchase(purchase);
		
		purchase.setCustomer(customer);
		customer.setPurchases(allPurchase);
		
		purchase.setCommission(0.15);
		art.setNumViews(-3);

		art.setViewers(allCustomers);
		customer.setBrowseArtworks(allArt);
		
		purchase.setShipment(shipment);
		shipment.setPurchases(allPurchase);
		
		
		// see how I only saved Registration object?
		// if you do some print statements or inspect in 
		// psql, you will see everything is persisted
		regRepo.save(reg);
		
		
		// now this only is persisting
		// the empty CrudRepository interfaces are enough to ssave
		// but you need to write the query methods for fetching
		// and asserting that what you saved was right (they should be)
		// see the tutorial on that
		// and also this link https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.query-creation

		
	}
	
	
}

