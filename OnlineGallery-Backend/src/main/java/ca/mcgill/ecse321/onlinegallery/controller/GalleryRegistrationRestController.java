 package ca.mcgill.ecse321.onlinegallery.controller;

import java.util.ArrayList;
import java.util.List;

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
			GalleryRegistration reg=service.getGalleryRegistration(username);
			return new ResponseEntity<>(convertToDto(reg), HttpStatus.OK);
		}
		catch(GalleryRegistrationException e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST); 
			
		}
	}
	
	@GetMapping(value={"/getAllRegistrations","/getAllRegistrations/"})
	public ResponseEntity<?> findAllGalleryRegistrations(){
		List<GalleryRegistrationDto> allRegDto = new ArrayList<GalleryRegistrationDto>();
		try {
			for (GalleryRegistration reg:service.getAllGalleryRegistrations()) {
				allRegDto.add(convertToDto(reg));
			}
			return new ResponseEntity<>(allRegDto,HttpStatus.OK);
		}
		catch(GalleryRegistrationException e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping(value = { "/createRegistration", "/createRegistration/" })
	public ResponseEntity<?> createGalleryRegistration(@RequestBody GalleryRegistrationDto dto) throws GalleryRegistrationException{
		try {
			GalleryRegistration reg=service.createGalleryRegistration(dto);
			return new ResponseEntity<>(convertToDto(reg), HttpStatus.CREATED);
		}
		catch(GalleryRegistrationException e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping(value = { "/deleteRegistration/{username}", "/deleteRegistration/{username}/" })
	public ResponseEntity<?> deleteGalleryRegistration(@PathVariable("username") String username) throws GalleryRegistrationException {
		try {
			GalleryRegistration reg=service.deleteGalleryRegistration(username);
			return new ResponseEntity<>(convertToDto(reg), HttpStatus.OK);
		}
		catch(GalleryRegistrationException e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
			
		}
	}
	
	@DeleteMapping(value = { "/deleteAllRegistrations", "/deleteAllRegistration/" })
	public ResponseEntity<?> deleteAllGalleryRegistrations() throws GalleryRegistrationException {
		List<GalleryRegistrationDto> allRegDto = new ArrayList<GalleryRegistrationDto>();
		try {
			for (GalleryRegistration reg:service.deleteAllGalleryRegistration()) {
				allRegDto.add(convertToDto(reg));
			}
			return new ResponseEntity<>(allRegDto,HttpStatus.OK);
		}
		catch(GalleryRegistrationException e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping(value= {"/changeEmail","/changeEmail/"})
	public ResponseEntity<?> updateGalleryRegistrationEmail(@RequestBody UpdateEmailDto dto) throws GalleryRegistrationException{
		try {
			GalleryRegistration reg = service.updateEmail(dto);
			return new ResponseEntity<>(convertToDto(reg),HttpStatus.OK);
		}
		catch(GalleryRegistrationException e){
			return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping(value= {"/changePassword","/changePassword/"})
	public ResponseEntity<?> udpateGalleryRegistrationPassword(@RequestBody UpdatePasswordDto dto) throws GalleryRegistrationException{
		try {
			GalleryRegistration reg = service.updatePassowrd(dto);
			return new ResponseEntity<>(convertToDto(reg),HttpStatus.OK);
		}
		catch(GalleryRegistrationException e){
			return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
	}

	
	@PutMapping(value= {"/setCustomer/{username}","/setCustomer/{username}/"})
	public ResponseEntity<?> setCustomer(@PathVariable("username") String username) throws GalleryRegistrationException{
		try {
			GalleryRegistration reg = service.setCustomer(username);
			return new ResponseEntity<>(convertToDto(reg),HttpStatus.OK);
		}
		catch(GalleryRegistrationException e){
			return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping(value= {"/setArtist/{username}","/setArtist/{username}/"})
	public ResponseEntity<?> setArtist(@PathVariable("username") String username) throws GalleryRegistrationException{
		try {
			GalleryRegistration reg = service.setArtist(username);
			return new ResponseEntity<>(convertToDto(reg),HttpStatus.OK);
		}
		catch(GalleryRegistrationException e){
			return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping(value= {"/setAdmin/{username}","/setAdmin/{username}/"})
	public ResponseEntity<?> setAdmin(@PathVariable("username") String username) throws GalleryRegistrationException{
		try {
			GalleryRegistration reg = service.setAdmin(username);
			return new ResponseEntity<>(convertToDto(reg),HttpStatus.OK);
		}
		catch(GalleryRegistrationException e){
			return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
	}
	

	private GalleryRegistrationDto convertToDto(GalleryRegistration reg) {


		GalleryRegistrationDto regDto = new GalleryRegistrationDto();
		regDto.setUsername(reg.getUserName());
		regDto.setFirstName(reg.getFirstName());
		regDto.setLastName(reg.getLastName());
		regDto.setEmail(reg.getEmail());
		regDto.setPassword(reg.getPassWord());
		regDto.setLoggedIn(reg.getIsLoggedIn());
		
		if (reg.getCustomer()!=null) {
			regDto.setCustomerId(reg.getCustomer().getCustomerId());
		}
		else {
			regDto.setCustomerId(null);
		}
		
		if (reg.getArtist()!=null) {
			regDto.setArtistId(reg.getArtist().getArtistId());			
		}
		else {
			regDto.setArtistId(null);
		}
		
		if (reg.getAdmin()!=null) {
			regDto.setAdminId(reg.getAdmin().getAdminId());
		}
		else {
			regDto.setAdminId(null);
		}
		return regDto;
	}
}
