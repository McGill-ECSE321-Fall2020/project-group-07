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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import ca.mcgill.ecse321.onlinegallery.dto.*;
import ca.mcgill.ecse321.onlinegallery.model.*;
import ca.mcgill.ecse321.onlinegallery.service.*;

import ca.mcgill.ecse321.onlinegallery.service.exception.PurchaseException;

@CrossOrigin(origins = "*")
@RestController
public class PurchaseRestController {

	@Autowired
	PurchaseService service;

	
	@GetMapping(value = { "/getPurchase","/getPurchase/"})
	public ResponseEntity<?> getPurchaseByUserNameAndArtId(@RequestBody PurchaseForm form) throws PurchaseException {
		try {
			Purchase purchase=service.getPurchaseByUserNameAndArtId(form);
			return new ResponseEntity<>(convertToDto(purchase),HttpStatus.OK);
		}
		catch (PurchaseException e){
			return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
	}


	@PostMapping(value = { "/createPurchase","/createPurchase/"})
	public ResponseEntity<?> createPurchase(@RequestBody PurchaseForm form) throws PurchaseException {
		try {
			Purchase purchase=service.createPurchase(form);
			return new ResponseEntity<>(convertToDto(purchase),HttpStatus.OK);
		}
		catch (PurchaseException e){
			return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping(value = { "/deletePurchase","/deletePurchase/"})
	public ResponseEntity<?> deletePurchase(@RequestBody PurchaseForm form) throws PurchaseException {
		
		try {
			Purchase purchase=service.deletePurchaseByUserNameAndArtId(form);
			return new ResponseEntity<>(convertToDto(purchase),HttpStatus.OK);
		}
		catch (PurchaseException e){
			return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping(value = { "/updatePurchase","/updatePurchase/"})
	public ResponseEntity<?> updatePurchase(@RequestBody PurchaseUpdateForm form) throws PurchaseException {
		try {
			Purchase purchase=service.updatePurchase(form);
			return new ResponseEntity<>(convertToDto(purchase),HttpStatus.OK);
		}
		catch (PurchaseException e){
			return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
	}
	

	private PurchaseDto convertToDto(Purchase purchase) {
		PurchaseDto dto = new PurchaseDto(purchase);
				
		return dto;
	}

}
