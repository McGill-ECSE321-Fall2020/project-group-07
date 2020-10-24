package ca.mcgill.ecse321.onlinegallery.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.onlinegallery.dto.OnlineGalleryDto;
import ca.mcgill.ecse321.onlinegallery.model.OnlineGallery;
import ca.mcgill.ecse321.onlinegallery.service.OnlineGalleryService;
import ca.mcgill.ecse321.onlinegallery.service.exception.OnlineGalleryException;

@CrossOrigin(origins="*")
@RestController
public class OnlineGalleryRestController {

	@Autowired
	OnlineGalleryService service;
	
	@PostMapping(value = { "/createOnlineGallery", "/createOnlineGallery/" })
	public ResponseEntity<?> createOnlineGallery() throws  OnlineGalleryException{
		try {
			OnlineGallery og = service.createOnlineGallery();
			return new ResponseEntity<>(convertToDto(og),HttpStatus.OK);
		}
		catch(OnlineGalleryException e){
			return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping(value = { "/getOnlineGallery/{systemId}", "/getOnlineGallery/{systemId}/" })
	public ResponseEntity<?> findOnlineGalleryBySystemId(@PathVariable("systemId") Long systemId) throws  OnlineGalleryException{
		try {
			OnlineGallery og = service.findOnlineGalleryBySystemId(systemId);
			return new ResponseEntity<>(convertToDto(og),HttpStatus.OK);
		}
		catch(OnlineGalleryException e){
			return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping(value = { "/updateOnlineGallery/{daysUp}", "/updateOnlineGallery/{daysUp}/" })
	public ResponseEntity<?> findOnlineGalleryBySystemId(@PathVariable("daysUp") int daysUp) throws  OnlineGalleryException{
		try {
			OnlineGallery og = service.updateDaysUp(daysUp);
			return new ResponseEntity<>(convertToDto(og),HttpStatus.OK);
		}
		catch(OnlineGalleryException e){
			return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping(value = { "/deleteOnlineGallery", "/deleteOnlineGallery/" })
	public ResponseEntity<?> deleteOnlineGallery() throws  OnlineGalleryException{
		try {
			OnlineGallery og = service.deleteOnlineGallery();
			return new ResponseEntity<>(convertToDto(og),HttpStatus.OK);
		}
		catch(OnlineGalleryException e){
			return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
	}
	
	private OnlineGalleryDto convertToDto(OnlineGallery og) {

		OnlineGalleryDto ogDto = new OnlineGalleryDto(og);
		return ogDto;
	}
}
