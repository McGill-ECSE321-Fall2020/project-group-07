package ca.mcgill.ecse321.onlinegallery.controller;

import java.sql.Date;

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
public class ArtworkRestController {

	@Autowired
	ArtworkService service;

	
//	@PutMapping(value={"/updateRegistration","/updateRegistration/"})
//	public GalleryRegistrationDto updateRegistration(@RequestBody RegistrationUpdateForm form) throws IllegalArgumentException {

	@PostMapping(value = { "/createArtwork","/createArtwork/"})
	public ArtworkDto createArtwork(@RequestBody ArtworkForm form) throws IllegalArgumentException {
		
		Artwork art=service.createArtworkInstance(form);
		ArtworkDto dto;
		
		try {
			dto=convertToDto(art);
		}
		catch(IllegalArgumentException e){
			dto=null;
		}
		
		return dto;
		
	}
	

	private ArtworkDto convertToDto(Artwork art) {
		if (art == null) {
				throw new IllegalArgumentException("artist with this username doesn't exist");	
		}

		ArtworkDto dto = new ArtworkDto(art.getName(),
										art.getDescription(),
										art.getPrice(),
										art.getStatus(),
										art.isOnSite(),
										art.getNumViews(),
										art.getDimension(),
										art.getWeight(),
										art.getShippingCost());
		return dto;
	}
}
