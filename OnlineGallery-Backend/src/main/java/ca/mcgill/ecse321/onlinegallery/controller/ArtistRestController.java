package ca.mcgill.ecse321.onlinegallery.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.onlinegallery.dto.ArtistDto;
import ca.mcgill.ecse321.onlinegallery.model.Artist;
import ca.mcgill.ecse321.onlinegallery.model.ArtistForm;
import ca.mcgill.ecse321.onlinegallery.service.ArtistService;
import ca.mcgill.ecse321.onlinegallery.service.exception.ArtistException;

@CrossOrigin(origins="*")
@RestController
public class ArtistRestController {
	
	@Autowired
	ArtistService service;

	@PostMapping(value = { "/getArtist", "/getArtist/" })
	public ResponseEntity<?> findArtistByUsername(@RequestBody ArtistForm form) throws ArtistException {
		try {
			Artist artist=service.findArtistByUsername(form);
			return new ResponseEntity<>(convertToDto(artist), HttpStatus.OK);
		}
		catch(ArtistException e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
			
		}
	}
	
	@PostMapping(value = { "/createArtist", "/createArtist/" })
	public ResponseEntity<?> createArtist(@RequestBody ArtistForm form) throws ArtistException {
		try {
			Artist artist=service.createArtist(form);
			return new ResponseEntity<>(convertToDto(artist), HttpStatus.OK);
		}
		catch(ArtistException e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
			
		}
	}
	
	@PostMapping(value = { "/updateBankInfo", "/updateBankInfo/" })
	public ResponseEntity<?> updateArtistBankInfo(@RequestBody ArtistForm form) throws ArtistException {
		try {
			Artist artist=service.updateBankInfo(form);
			return new ResponseEntity<>(convertToDto(artist), HttpStatus.OK);
		}
		catch(ArtistException e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
			
		}
	}
	@PostMapping(value = { "/deleteArtist", "/deleteArtist/" })
	public ResponseEntity<?> deleteArtist(@RequestBody ArtistForm form) throws ArtistException {
		try {
			Artist artist=service.deleteArtistByUserName(form);
			return new ResponseEntity<>(convertToDto(artist), HttpStatus.OK);
		}
		catch(ArtistException e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
			
		}
	}
	
	private ArtistDto convertToDto(Artist artist) {

		ArtistDto artistDto = new ArtistDto(artist);
		return artistDto;
	}
}
