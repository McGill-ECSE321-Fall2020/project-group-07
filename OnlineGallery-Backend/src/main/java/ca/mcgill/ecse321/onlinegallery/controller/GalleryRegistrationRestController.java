package ca.mcgill.ecse321.onlinegallery.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import ca.mcgill.ecse321.onlinegallery.dto.*;
import ca.mcgill.ecse321.onlinegallery.model.*;
import ca.mcgill.ecse321.onlinegallery.service.*;
import ca.mcgill.ecse321.onlinegallery.service.exception.GalleryRegistrationException;

@CrossOrigin(origins = "*")
@RestController
public class GalleryRegistrationRestController {

	@Autowired
	GalleryRegistrationService service;

	@GetMapping(value = { "/getRegistration/{username}", "/getRegistration/{username}/" })
	public ResponseEntity<?> findGalleryRegistrationByUserName(@PathVariable("username") String username) throws GalleryRegistrationException {
		try {
			GalleryRegistration reg=service.findGalleryRegistrationByUserName(username);
			return new ResponseEntity<>(convertToDto(reg), HttpStatus.OK);
		}
		catch(GalleryRegistrationException e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
			
		}
	}
	
	@PostMapping(value = { "/createRegistration/{username}", "/createRegistration/{username}/" })
	public ResponseEntity<?> createGalleryRegistration(@PathVariable("username") String username) throws GalleryRegistrationException{
		try {
			GalleryRegistration reg=service.createGalleryRegistration(username);
			return new ResponseEntity<>(convertToDto(reg), HttpStatus.OK);
		}
		catch(GalleryRegistrationException e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
			
		}
	}
	
	@PutMapping(value={"/updateRegistration","/updateRegistration/"})
	public ResponseEntity<?> updateRegistration(@RequestBody RegistrationUpdateForm form) throws GalleryRegistrationException {
		try {
			GalleryRegistration reg=service.updateRegistrationInfo(form);
			return new ResponseEntity<>(convertToDto(reg), HttpStatus.OK);
		}
		catch(GalleryRegistrationException e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
			
		}
	}
	
	@DeleteMapping(value = { "/deleteRegistration/{username}", "/deleteRegistration/{username}/" })
	public ResponseEntity<?> deleteGalleryRegistration(@PathVariable("username") String username) throws GalleryRegistrationException {
		try {
			GalleryRegistration reg=service.deleteGalleryRegistrationByUserName(username);
			return new ResponseEntity<>(convertToDto(reg), HttpStatus.OK);
		}
		catch(GalleryRegistrationException e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
			
		}
	}
	
	@PutMapping(value={"/changePassword","/changePassword/"}) 
	public ResponseEntity<?> changePassowrd(@RequestBody PasswordUpdateForm form) throws GalleryRegistrationException {
		try {
			GalleryRegistration reg=service.changePassword(form);
			return new ResponseEntity<>(convertToDto(reg), HttpStatus.OK);
		}
		catch(GalleryRegistrationException e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
			
		}
	}
	
	@PutMapping(value={"/toggleLoggedIn/{username}","/toggleLoggedIn/{username}/"})
	public ResponseEntity<?> toggleLoggedIn(@PathVariable("username") String username) throws GalleryRegistrationException {
		try {
			GalleryRegistration reg=service.toggleLoggedInStatus(username);
			return new ResponseEntity<>(convertToDto(reg), HttpStatus.OK);
		}
		catch(GalleryRegistrationException e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
			
		}
	}
	

	private GalleryRegistrationDto convertToDto(GalleryRegistration reg) {


		GalleryRegistrationDto regDto = new GalleryRegistrationDto(reg);
		return regDto;
	}
}
