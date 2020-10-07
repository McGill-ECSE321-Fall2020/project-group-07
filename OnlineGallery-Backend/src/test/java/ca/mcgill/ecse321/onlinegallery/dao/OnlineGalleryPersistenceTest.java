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

import ca.mcgill.ecse321.onlinegallery.model.Artist;
import ca.mcgill.ecse321.onlinegallery.model.Artwork;
import ca.mcgill.ecse321.onlinegallery.model.Customer;
import ca.mcgill.ecse321.onlinegallery.model.GalleryAdmin;
import ca.mcgill.ecse321.onlinegallery.model.GalleryRegistration;
import ca.mcgill.ecse321.onlinegallery.model.Profile;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class OnlineGalleryPersistenceTest {

	@Autowired
	private GalleryRegistrationRepository regRepo;
	
	@Autowired
	private GalleryAdminRepository adminRepo;
	
	@Autowired
	private CustomerRepository customerRepo;
	
	@Autowired
	private ArtistRepository artistRepo;
	
	@Autowired
	private ProfileRepository profileRepo;
	
	@Autowired
	private ArtworkRepository artRepo;	

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
		
		Artist artist = new Artist();
		Set<Artist> allArtists = new HashSet<Artist>();
		allArtists.add(artist);
		
		reg.setGalleryAdmin(admin);
		reg.setGalleryCustomers(allCustomers);
		reg.setGalleryArtists(allArtists);
		
		admin.setGalleryRegistration(reg);
		customer.setGalleryRegistration(reg);
		artist.setGalleryRegistration(reg);
		
		Profile profile = new Profile();
		
		artist.setProfile(profile);
		profile.setArtist(artist);
		
		Artwork art= new Artwork();
		Set<Artwork> allArt = new HashSet<Artwork>();
		allArt.add(art);
		
		artist.setArtworks(allArt);
		art.setArtist(artist);
		
		profile.setArtworks(allArt);
		
		art.setViewers(allCustomers);
		customer.setBrowseArtworks(allArt);
		
		System.out.println(art.getViewers());
		
		regRepo.save(reg);
		
	}
	
//	@Test
//	public void testPersistAndLoadArtist() {
//	
//		GalleryRegistration reg = new GalleryRegistration();
//		reg.setUserName("homer");
//		
//		GalleryAdmin admin=new GalleryAdmin();
//		
//		Customer customer = new Customer();
//		Set<Customer> allCustomers = new HashSet<Customer>();
//		allCustomers.add(customer);
//		
//		Artist artist = new Artist();
//		Set<Artist> allArtists = new HashSet<Artist>();
//		allArtists.add(artist);
//		
//		reg.setGalleryAdmin(admin);
//		reg.setGalleryCustomers(allCustomers);
//		reg.setGalleryArtists(allArtists);
//		
//		
//	}
	
}

