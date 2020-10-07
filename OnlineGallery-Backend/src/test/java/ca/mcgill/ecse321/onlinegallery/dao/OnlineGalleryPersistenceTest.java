//package ca.mcgill.ecse321.onlinegallery.dao;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//
//
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//import ca.mcgill.ecse321.onlinegallery.model.Artwork;
//import ca.mcgill.ecse321.onlinegallery.model.Customer;
//import ca.mcgill.ecse321.onlinegallery.model.Purchase;
//
//
//
//@ExtendWith(SpringExtension.class)
//@SpringBootTest
//public class OnlineGalleryPersistenceTest {
//
//	@Autowired
//	private CustomerRepository customerRepository;
//	@Autowired
//	private ArtworkRepository artworkRepository;
//	@Autowired
//	private PurchaseRepository purchaseRepository;	
//	//Order is a reserved word in Postgres, absolutely do not name any class Order
//	
//
//	@AfterEach
//	public void clearDatabase() {
//		purchaseRepository.deleteAll();
////		gotta delete purchase first! probably a general pattern to delete the 
////		'root' of an association group
//		
//		customerRepository.deleteAll();
//		artworkRepository.deleteAll();
//	}
//	
//	@Test
//	public void testPersistAndLoadCustomer() {
//		String name = "Homer Simpson";
//		
//		Customer customer = new Customer();
//		customer.setName(name);
//		
//		customerRepository.save(customer);
//		
//		customer=null;
//		
//		customer=customerRepository.findCustomerByName(name);
//		assertNotNull(customer);
//		assertEquals(name,customer.getName());
//		
//	}
//	
//	@Test
//	public void testPersistenceAndLoadArtwork() {
//		String artname="Donut Stilllife";
//		double price=100.5;
//		double xDim=24.3;
//		double yDim=12.8;
//		
//		Artwork art=new Artwork();
//		art.setName(artname);
//		art.setPrice(price);
//		art.setXDim(xDim);
//		art.setYDim(yDim);
//		
//		artworkRepository.save(art);
//		
//		art=null;
//		
//		art=artworkRepository.findArtworkByName(artname);
//		assertNotNull(art);
//		assertEquals(artname,art.getName());
//		assertEquals(price,art.getPrice());
//		assertEquals(xDim,art.getXDim());
//		assertEquals(yDim,art.getYDim());
//	}
//	
//	@Test
//	public void testPersistenceAndLoadOrder() {
//		String name = "Lisa Simpson";
//		Customer customer = new Customer();
//		customer.setName(name);
//		customerRepository.save(customer);
//		
//		String artname="Blue Saxophone";
//		double price=15;
//		double xDim=9;
//		double yDim=16;
//		
//		Artwork art=new Artwork();
//		art.setName(artname);
//		art.setPrice(price);
//		art.setXDim(xDim);
//		art.setYDim(yDim);
//		artworkRepository.save(art);
//		
//		Integer purchaseNum=123;
//		Purchase purchase = new Purchase();
//		purchase.setId(purchaseNum);
//		purchase.setArtwork(art);
//		purchase.setCustomer(customer);
//		
//		purchaseRepository.save(purchase);
//		
//		purchase=null;
//		
//		purchase=purchaseRepository.findByCustomerAndArtwork(customer, art);
//		assertNotNull(purchase);
//		
//		assertEquals(purchase.getCustomer().getName(),customer.getName());
//		
//		assertEquals(purchase.getArtwork().getName(),art.getName());
//		assertEquals(purchase.getArtwork().getPrice(),art.getPrice());
//		assertEquals(purchase.getArtwork().getXDim(),art.getXDim());
//		assertEquals(purchase.getArtwork().getYDim(),art.getYDim());
//		
//		assertEquals(purchase.getId(),purchaseNum);
//		
//		assertEquals(true,purchaseRepository.existsByCustomerAndArtwork(customer, art));
//		assertEquals(1,purchaseRepository.findByCustomer(customer).size());
//		
//			
//	}
//}
