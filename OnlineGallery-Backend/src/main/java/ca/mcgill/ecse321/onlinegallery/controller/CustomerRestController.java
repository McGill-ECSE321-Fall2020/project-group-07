package ca.mcgill.ecse321.onlinegallery.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.onlinegallery.dto.CustomerDto;
import ca.mcgill.ecse321.onlinegallery.model.Customer;
import ca.mcgill.ecse321.onlinegallery.service.CustomerService;
import ca.mcgill.ecse321.onlinegallery.service.exception.CustomerException;

@CrossOrigin(origins = "*")
@RestController
public class CustomerRestController {

	@Autowired
	CustomerService service;
	
	@GetMapping(value = { "/getCustomerByUsername/{username}", "/getCustomerByUsername/{username}/" })
	public ResponseEntity<?> findCustomerByUsername(@PathVariable("username") String username) throws CustomerException {
		try {
			Customer customer = service.findCustomerByUsername(username);
			return new ResponseEntity<>(convertToDto(customer), HttpStatus.OK);
		}
		catch(CustomerException e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(value={"/getAllCustomers","/getAllCustomers/"})
	public ResponseEntity<?> getAllCustomer() throws CustomerException{
		List<CustomerDto> customersDto = new ArrayList<CustomerDto>();
		try {
			for (Customer customer : service.findAllCustomers()) {
				customersDto.add(convertToDto(customer));
			}
			return new ResponseEntity<>(customersDto,HttpStatus.OK);
		}
		catch(CustomerException e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
	}
	
	private CustomerDto convertToDto(Customer customer) {
		
		CustomerDto dto = new CustomerDto();
		dto.setBankInfo(customer.getBankInfo());
		dto.setCustomerId(customer.getCustomerId());
		dto.setUsername(customer.getGalleryRegistration().getUserName());
		
		return dto;
	}
	
}
