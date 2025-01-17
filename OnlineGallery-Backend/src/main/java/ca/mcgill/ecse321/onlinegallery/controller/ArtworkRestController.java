package ca.mcgill.ecse321.onlinegallery.controller;

import java.util.ArrayList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.onlinegallery.dto.ArtworkDto;
import ca.mcgill.ecse321.onlinegallery.model.Artwork;
import ca.mcgill.ecse321.onlinegallery.service.ArtworkService;
import ca.mcgill.ecse321.onlinegallery.service.exception.ArtistException;
import ca.mcgill.ecse321.onlinegallery.service.exception.ArtworkException;

@CrossOrigin(origins="*")
@RestController
public class ArtworkRestController {
	
	@Autowired
	ArtworkService service;
	
	@PostMapping(value = { "/createArtwork", "/createArtwork/" })
	public ResponseEntity<?> createArtwork(@RequestBody ArtworkDto dto) throws ArtworkException, ArtistException {
		
		try {
			Artwork artwork = service.createArtwork(dto);
			return new ResponseEntity<>(convertToDto(artwork), HttpStatus.OK);
		}
		catch(ArtworkException e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
			
		}
	
	}
	
	@GetMapping(value = { "/getAllAvailableArtwork", "/getAllAvailableArtwork/" })
	public ResponseEntity<?> getAllAvailableArtwork() throws ArtworkException {
		
		List<ArtworkDto> artworkDto = new ArrayList<ArtworkDto>();
		
		try {
			for (Artwork a:service.getAllAvailableArtwork()) {
				artworkDto.add(convertToDto(a));
			}
			return new ResponseEntity<>(artworkDto,HttpStatus.OK);
		}
		catch(ArtworkException e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
			
		}
	
	}
	
	@GetMapping(value = { "/countAvailableArtworks", "/countAvailableArtworks/" })
	public ResponseEntity<?> countAvailableArtworks() throws ArtworkException {
		
		int numAvailable;
		AvailableCount countDto = new AvailableCount();
		
		try {
			numAvailable = service.countAvailableArtworks();
			countDto.setAvailable(numAvailable);
			return new ResponseEntity<>(countDto,HttpStatus.OK);
		}
		catch(ArtworkException e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
			
		}
	
	}
	
	
	@GetMapping(value = { "/getAllArtworks", "/getAllArtworks/" })
	public ResponseEntity<?> getAllArtworks() throws ArtworkException {
		
		List<ArtworkDto> artworkDto = new ArrayList<ArtworkDto>();
		
		try {
			for (Artwork a:service.getAllArtworks()) {
				artworkDto.add(convertToDto(a));
			}
			return new ResponseEntity<>(artworkDto,HttpStatus.OK);
		}
		catch(ArtworkException e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
			}
	
	}
	
	@GetMapping(value = { "/getAvailableArtworkByArtistId/{artistId}", "/getAvailableArtworkByArtistId/{artistId}/" })
	public ResponseEntity<?> getAvailableArtworkByArtistId(@PathVariable("artistId") Long artistId) throws ArtworkException {
		
		List<ArtworkDto> artworkDto = new ArrayList<ArtworkDto>();
		
		try {
			for (Artwork a:service.getAvailableArtworkByArtistId(artistId)) {
				artworkDto.add(convertToDto(a));
			}
			return new ResponseEntity<>(artworkDto,HttpStatus.OK);
		}
		catch(ArtworkException e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
			
		}
	}
	
	@GetMapping(value = { "/getAvailableArtworkDetail/{artworkId}", "/getAvailableArtworkDetail/{artworkId}/" })
	public ResponseEntity<?> getAvailableArtworkDetail(@PathVariable("artworkId") Long artworkId) throws ArtworkException, ArtistException {
		try {
			Artwork artwork = service.getAvailableArtworkDetail(artworkId);
			return new ResponseEntity<>(convertToDto(artwork), HttpStatus.OK);
		}
		catch(ArtworkException e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
			
		}
	
	}
	
	
	@GetMapping(value = { "/retrieveRandomAvailableArtworks/{numToGet}", "/retrieveRandomAvailableArtworks/{numToGet}/"})
	public ResponseEntity<?> retrieveRandomAvailableArtworks(@PathVariable("numToGet") int numToGet) throws ArtworkException {
		
		List<ArtworkDto> artworkDto = new ArrayList<ArtworkDto>();
		
		try {
			for (Artwork a:service.retrieveRandomAvailableArtworks(numToGet)) {
				artworkDto.add(convertToDto(a));
			}
			return new ResponseEntity<>(artworkDto,HttpStatus.OK);
		}
		catch(ArtworkException e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
			}
	
	}
	
	private ArtworkDto convertToDto(Artwork artwork) {

		ArtworkDto artworkDto = new ArtworkDto();
		artworkDto.setUsername(artwork.getArtist().getGalleryRegistration().getUserName());
		artworkDto.setCommission(artwork.getComission());
		artworkDto.setDescription(artwork.getDescription());
		artworkDto.setDimension(artwork.getDimension());
		artworkDto.setName(artwork.getName());
		artworkDto.setNumViews(artwork.getNumViews());
		artworkDto.setPrice(artwork.getPrice());
		artworkDto.setStatus(artwork.getStatus());
		artworkDto.setWeight(artwork.getWeight());
		artworkDto.setArtworkId(artwork.getArtworkId());
		artworkDto.setUrl(artwork.getUrl());
		artworkDto.setMedium(artwork.getMedium());

		return artworkDto;
	}
	
	private class AvailableCount{
		int available;
		
		public void setAvailable(int available) {
			this.available=available;
		}
		public int getAvailable() {
			return this.available;
		}
	}


}
