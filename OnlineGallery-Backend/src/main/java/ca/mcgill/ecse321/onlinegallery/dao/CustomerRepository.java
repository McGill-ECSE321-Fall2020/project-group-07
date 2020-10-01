package ca.mcgill.ecse321.onlinegallery.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.onlinegallery.model.Customer;

public interface CustomerRepository extends CrudRepository<Customer, String>{

	Customer findCustomerByName(String name);

}