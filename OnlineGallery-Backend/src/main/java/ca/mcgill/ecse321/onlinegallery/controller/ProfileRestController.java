package ca.mcgill.ecse321.onlinegallery.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.onlinegallery.dto.ProfileDto;
import ca.mcgill.ecse321.onlinegallery.model.Profile;
import ca.mcgill.ecse321.onlinegallery.model.ProfileUpdateForm;
import ca.mcgill.ecse321.onlinegallery.service.ProfileService;
import ca.mcgill.ecse321.onlinegallery.service.exception.ProfileException;

@CrossOrigin(origins = "*")
@RestController
public class ProfileRestController {

	@Autowired
	ProfileService service;
	
	@GetMapping(value = { "/getProfile/{id}", "/getProfile/{id}/" })
	public ResponseEntity<?> getPurchaseByPurchaseId(@PathVariable("id") Long id) throws ProfileException {
		try {
			Profile profile=service.findPurchaseByPurchaseId(id);
			return new ResponseEntity<>(convertToDto(profile), HttpStatus.OK);
		}
		catch(ProfileException e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping(value = { "/createProfile/{id}", "/createProfile/{id}/" })
	public ResponseEntity<?> createProfile(@PathVariable("id") Long id) throws ProfileException{
		try {
			Profile profile=service.createProfile(id);
			return new ResponseEntity<>(convertToDto(profile), HttpStatus.OK);
		}
		catch(ProfileException e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping(value={"/updateProfile","/updateProfile/"})
	public ResponseEntity<?> updateProfile(@RequestBody ProfileUpdateForm form) throws ProfileException {
		try {
			Profile profile=service.updateProfileInfo(form);
			return new ResponseEntity<>(convertToDto(profile), HttpStatus.OK);
		}
		catch(ProfileException e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);	
		}
	}
	
	@DeleteMapping(value = { "/deleteProfile/{id}", "/deleteProfile/{id}/" })
	public ResponseEntity<?> deleteProfile(@PathVariable("id") Long id) throws ProfileException {
		try {
			Profile profile=service.deleteProfileByProfileId(id);
			return new ResponseEntity<>(convertToDto(profile), HttpStatus.OK);
		}
		catch(ProfileException e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
	}
	
	private ProfileDto convertToDto(Profile profile) {
		ProfileDto dto = new ProfileDto(profile);
		return dto;
	}
}
