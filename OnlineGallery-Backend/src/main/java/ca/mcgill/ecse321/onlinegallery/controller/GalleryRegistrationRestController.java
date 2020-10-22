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

import ca.mcgill.ecse321.onlinegallery.dto.*;
import ca.mcgill.ecse321.onlinegallery.model.*;
import ca.mcgill.ecse321.onlinegallery.service.*;

@CrossOrigin(origins = "*")
@RestController
public class GalleryRegistrationRestController {

	@Autowired
	GalleryRegistrationService service;

	@GetMapping(value = { "/getRegistration/{username}", "/getRegistration/{username}/" })
	public GalleryRegistrationDto findGalleryRegistrationByUserName(@PathVariable("username") String username) throws IllegalArgumentException {
		GalleryRegistration reg=service.findGalleryRegistrationByUserName(username);
		GalleryRegistrationDto dto;
		
		try {
			dto=convertToDto(reg);
		}
		catch(IllegalArgumentException e){
			dto=null;
		}
		return dto;
	}
	
	@PutMapping(value={"/updateRegistration","/updateRegistration/"})
	public GalleryRegistrationDto updateRegistration(@RequestBody RegistrationUpdateForm form) {
		GalleryRegistration reg=service.updateRegistrationInfo(form);
		GalleryRegistrationDto dto;
		
		try {
			dto=convertToDto(reg);
		}
		catch(IllegalArgumentException e){
			dto=null;
		}
		
		return dto;
	}

	@PostMapping(value = { "/createRegistration/{username}", "/createRegistration/{username}/" })
	public GalleryRegistrationDto createGalleryRegistration(@PathVariable("username") String username) throws IllegalArgumentException {
		GalleryRegistration reg=service.createGalleryRegistration(username);
		GalleryRegistrationDto dto;
		
		try {
			dto=convertToDto(reg);
		}
		catch(IllegalArgumentException e){
			dto=null;
		}
		
		return dto;
	}
	
	@DeleteMapping(value = { "/deleteRegistration/{username}", "/deleteRegistration/{username}/" })
	public GalleryRegistrationDto deleteGalleryRegistration(@PathVariable("username") String username) throws IllegalArgumentException {
		GalleryRegistration reg=service.deleteGalleryRegistrataionByUserName(username);
		GalleryRegistrationDto dto;
		
		try {
			dto=convertToDto(reg);
		}
		catch(IllegalArgumentException e){
			dto=null;
		}
		
		return dto;
	}

	private GalleryRegistrationDto convertToDto(GalleryRegistration reg) {
		if (reg == null) {
				throw new IllegalArgumentException("either new username already exists or queried username not in system");	
		}

		boolean loginStatus;

		if (reg.getIsLoggedIn() == null) {
			loginStatus = false;
		} else {
			loginStatus = reg.getIsLoggedIn();
		}

		GalleryRegistrationDto regDto = new GalleryRegistrationDto(reg.getUserName(), reg.getFirstName(),
				reg.getLastName(), reg.getEmail(), reg.getPhoneNumber(), reg.getPassWord(), loginStatus);
		return regDto;
	}
}
