package ca.mcgill.ecse321.onlinegallery.dao;

import java.util.*;
import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.onlinegallery.model.Artwork;
import ca.mcgill.ecse321.onlinegallery.model.Customer;
import ca.mcgill.ecse321.onlinegallery.model.Purchase;

public interface PurchaseRepository extends CrudRepository<Purchase, Integer> {
	List<Purchase> findByCustomer(Customer customer);

	boolean existsByCustomerAndArtwork(Customer customer, Artwork artwork);

	Purchase findByCustomerAndArtwork(Customer customer, Artwork artwork);
	
}
