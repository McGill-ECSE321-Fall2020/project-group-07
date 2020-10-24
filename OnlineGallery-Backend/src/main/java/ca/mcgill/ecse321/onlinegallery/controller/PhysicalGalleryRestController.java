package ca.mcgill.ecse321.onlinegallery.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;


import ca.mcgill.ecse321.onlinegallery.dto.PhysicalGalleryDto;
import ca.mcgill.ecse321.onlinegallery.model.PhysicalGallery;
import ca.mcgill.ecse321.onlinegallery.service.PhysicalGalleryService;
import ca.mcgill.ecse321.onlinegallery.service.exception.PhysicalGalleryException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@CrossOrigin(origins="*")
@RestController
public class PhysicalGalleryRestController {
	
	@Autowired
	PhysicalGalleryService service;
	
	@PostMapping(value = { "/create_physicalgallery/{address}", "/create_physicalgallery/{address}/" })
	public ResponseEntity<?> createPhysicalGallery(@PathVariable("address") String address) throws  PhysicalGalleryException{
		try {
			PhysicalGallery pg = service.createPhysicalGallery(address);
			return new ResponseEntity<>(convertToDto(pg),HttpStatus.OK);
		}
		catch(PhysicalGalleryException e){
			return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping(value = { "/update_physicaladdress/{address}", "/update_physicaladdress/{address}/" })
	public ResponseEntity<?> updatePhysicalAddress(@PathVariable("address") String address) throws PhysicalGalleryException {
		try {
			PhysicalGallery pg = service.updateAddress(address);
			return new ResponseEntity<>(convertToDto(pg),HttpStatus.OK);
		}
		catch(PhysicalGalleryException e){
			return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping(value = { "/deletephysical", "/deletephysical/" }) 
	public ResponseEntity<?> deletePhysicalGallery() throws PhysicalGalleryException {
		try {
			PhysicalGallery pg = service.deletePhysicalGallery();
			return new ResponseEntity<>(convertToDto(pg),HttpStatus.OK);
		}
		catch(PhysicalGalleryException e){
			return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(value= {"/physicalgallery","physicalgallery/"})
	public ResponseEntity<?> getPhyiscalGallery() throws PhysicalGalleryException{
		try {
			PhysicalGallery pg = service.getPhysicalGallery();
			return new ResponseEntity<>(convertToDto(pg),HttpStatus.OK);
		}
		catch(PhysicalGalleryException e){
			return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
	}
	
	private PhysicalGalleryDto convertToDto(PhysicalGallery pg) {
		PhysicalGalleryDto pgDto=new PhysicalGalleryDto(pg.getAddress());
		return pgDto;
	}
	
}
