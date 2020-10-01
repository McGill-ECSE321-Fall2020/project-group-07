package ca.mcgill.ecse321.onlinegallery.dao;

import java.util.*;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.onlinegallery.model.Order;
import ca.mcgill.ecse321.onlinegallery.model.Artwork;
import ca.mcgill.ecse321.onlinegallery.model.Customer;

public interface OrderRepository extends CrudRepository<Order, Integer> {
	List<Order> findByCustomer(Customer customerName);
	boolean existsByCustomerAndArtwork(Customer customer, Artwork artName);
	Order findByCustomerAndArtwork(Customer customer, Artwork artName);	
}
