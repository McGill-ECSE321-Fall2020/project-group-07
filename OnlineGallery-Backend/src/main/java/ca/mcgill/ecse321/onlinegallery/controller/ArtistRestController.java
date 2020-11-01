package ca.mcgill.ecse321.onlinegallery.controller;

import java.util.ArrayList;

import java.util.List;

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

import ca.mcgill.ecse321.onlinegallery.dto.ArtistDto;
import ca.mcgill.ecse321.onlinegallery.dto.ProfileDto;
import ca.mcgill.ecse321.onlinegallery.model.Artist;
import ca.mcgill.ecse321.onlinegallery.model.Profile;
import ca.mcgill.ecse321.onlinegallery.service.ArtistService;
import ca.mcgill.ecse321.onlinegallery.service.exception.ArtistException;

@CrossOrigin(origins="*")
@RestController
public class ArtistRestController {
	
	@Autowired
	ArtistService service;

	@GetMapping(value = { "/getArtistByUsername/{username}", "/getArtistByUsername/{username}/" })
	public ResponseEntity<?> findArtistByUsername(@PathVariable("username") String username) throws ArtistException {
		try {
			Artist artist=service.findArtistByUsername(username);
			return new ResponseEntity<>(convertToDto(artist), HttpStatus.OK);
		}
		catch(ArtistException e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
			
		}
	}
	@GetMapping(value = { "/getArtistById/{id}", "/getArtistById/{id}/" })
	public ResponseEntity<?> findArtistById(@PathVariable("id") Long id) throws ArtistException {
		try {
			Artist artist=service.findArtistById(id);
			return new ResponseEntity<>(convertToDto(artist), HttpStatus.OK);
		}
		catch(ArtistException e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
			
		}
	}
	
	@GetMapping(value={"/getAllArtists","/getAllArtists/"})
	public ResponseEntity<?> findAllArtists(){
		List<ArtistDto> artistsDto = new ArrayList<ArtistDto>();
		try {
			for (Artist a:service.findAllArtist()) {
				artistsDto.add(convertToDto(a));
			}
			return new ResponseEntity<>(artistsDto,HttpStatus.OK);
		}
		catch(ArtistException e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping(value = { "/createProfile", "/createProfile/" })
	public ResponseEntity<?> createProfile(@RequestBody ProfileDto dto) throws ArtistException {
		try {
			Artist artist=service.createProfile(dto);
			return new ResponseEntity<>(convertToDto(artist), HttpStatus.CREATED);
		}
		catch(ArtistException e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
			
		}
	}
	@PutMapping(value = { "/updateProfile", "/updateProfile/" })
	public ResponseEntity<?> updateProfile(@RequestBody ProfileDto dto) throws ArtistException {
		try {
			Artist artist=service.updateProfile(dto);
			return new ResponseEntity<>(convertToDto(artist), HttpStatus.OK);
		}
		catch(ArtistException e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
			
		}
	}
	
	@DeleteMapping(value = { "/deleteArtistByUsername/{username}", "/deleteArtistByUsername/{username}/" })
	public ResponseEntity<?> deleteArtistByUsername(@PathVariable("username") String username) throws ArtistException {
		try {
			Artist artist=service.deleteArtistByUsername(username);
			return new ResponseEntity<>(convertToDto(artist), HttpStatus.OK);
		}
		catch(ArtistException e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
			
		}
	}
	
	private ArtistDto convertToDto(Artist artist) {

		ArtistDto artistDto = new ArtistDto();

		artistDto.setArtistId(artist.getArtistId());
		artistDto.setBankInfo(artist.getBankInfo());
		if(artist.getGalleryRegistration()!=null) {
			artistDto.setUsername(artist.getGalleryRegistration().getUserName());
		}else {
			artistDto.setUsername("no username associated with deleted artist!");
		}
		artistDto.setNumSold(artist.getProfile().getNumSold());
		artistDto.setProfileId(artist.getProfile().getProfileId());
		artistDto.setRating(artist.getProfile().getRating());
		artistDto.setSelfDescription(artist.getProfile().getSelfDescription());
		artistDto.setTotalEarnings(artist.getProfile().getTotalEarnings());
		
		return artistDto;
	}



}
