package ca.mcgill.ecse321.onlinegallery.dao;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.onlinegallery.model.*;
public interface PurchaseRepository extends CrudRepository<Purchase, Long> {
	
	Purchase findPurchaseByPurchaseId(Long purchaseId);
	
	Purchase findByCustomerAndArtwork(Customer customer,Artwork artwork);
	
	boolean existsByCustomerAndArtwork(Customer customer,Artwork artwork);
	
	boolean existsByPurchaseId(Long purchaseId);

}
