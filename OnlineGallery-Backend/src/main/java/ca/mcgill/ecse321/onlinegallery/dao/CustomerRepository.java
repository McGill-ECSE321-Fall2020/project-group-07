package ca.mcgill.ecse321.onlinegallery.dao;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.onlinegallery.model.*;
public interface CustomerRepository extends CrudRepository<Customer, Long> {
//	Customer findCustomerByBankInfo (String bankInfo);
//	List<Customer> findByBrowseArtworks (Artwork art);
//	Customer findByGalleryRegistration (GalleryRegistration galleryReg);
//	Customer findByPurchases (Purchase purchase);
	
	Customer findCustomerByCustomerId(Long customerId);
	boolean existsByCustomerId(Long customerId);
	
}
