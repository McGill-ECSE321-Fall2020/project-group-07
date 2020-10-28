package ca.mcgill.ecse321.onlinegallery.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.onlinegallery.dao.CustomerRepository;
import ca.mcgill.ecse321.onlinegallery.dao.GalleryRegistrationRepository;
import ca.mcgill.ecse321.onlinegallery.model.Customer;
import ca.mcgill.ecse321.onlinegallery.model.GalleryRegistration;
import ca.mcgill.ecse321.onlinegallery.service.exception.CustomerException;

@Service
public class CustomerService {
	
	@Autowired
	GalleryRegistrationRepository regRepo;
	
	@Autowired
	CustomerRepository cusRepo;
	
	@Transactional
	public Customer findCustomerByUsername(String username) throws CustomerException {
		
		if (!regRepo.existsByUserName(username)) {
			
			throw new CustomerException("No registration exists under the username ["+username+"]");
		}
		
		GalleryRegistration reg = regRepo.findGalleryRegisrationByUserName(username);
		
		if (reg.getCustomer() == null) {
			
			throw new CustomerException("No customer exists under the username ["+username+"]");
		}
		Customer customer = reg.getCustomer();	
		return customer;
	}
	
	@Transactional
	public List<Customer> findAllCustomers() throws CustomerException {
		
		List<Customer> customers = toList(cusRepo.findAll());
		
		if(customers.size() == 0) throw new CustomerException("No customer exists.");
		
		return customers;
	}

	private <T> List<T> toList(Iterable<T> iterable){
		List<T> resultList = new ArrayList<T>();
		for (T t : iterable) {
			resultList.add(t);
		}
		return resultList;
	}
	
}
