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
public class PurchaseRestController {

	@Autowired
	PurchaseService service;

	
	@GetMapping(value = { "/getPurchase","/getPurchase/"})
	public PurchaseDto getPurchaseByUserNameAndArtId(@RequestBody PurchaseForm form) throws IllegalArgumentException {
		Purchase purchase=service.getPurchaseByUserNameAndArtId(form);
		PurchaseDto dto=this.postcondition(purchase);
		return dto;
	}


	@PostMapping(value = { "/createPurchase","/createPurchase/"})
	public PurchaseDto createPurchase(@RequestBody PurchaseForm form) throws IllegalArgumentException {
		
		Purchase purchase=service.createPurchase(form);
		PurchaseDto dto=this.postcondition(purchase);
		return dto;
	}
	
	@DeleteMapping(value = { "/deletePurchase","/deletePurchase/"})
	public PurchaseDto deletePurchase(@RequestBody PurchaseForm form) throws IllegalArgumentException {
		
		Purchase purchase=service.deletePurchaseByUserNameAndArtId(form);
		PurchaseDto dto=this.postcondition(purchase);
		return dto;
	}
	
	@PutMapping(value = { "/updatePurchase","/updatePurchase/"})
	public PurchaseDto updatePurchase(@RequestBody PurchaseUpdateForm form) throws IllegalArgumentException {
		
		Purchase purchase=service.updatePurchase(form);
		PurchaseDto dto=this.postcondition(purchase);
		return dto;
	}
	

	private PurchaseDto convertToDto(Purchase purchase) {
		if (purchase == null) {
				throw new IllegalArgumentException("customer with this username doesn't exist");	
		}

		PurchaseDto dto = new PurchaseDto(purchase.getCommission(),
										  purchase.getShipmentType(),
										  purchase.getPaymentMethod(),
										  purchase.isPaid(),
										  purchase.getDate());
				
		return dto;
	}
	
	private PurchaseDto postcondition(Purchase purchase) {
		PurchaseDto dto;
		
		try {
			dto=convertToDto(purchase);
		}
		catch(IllegalArgumentException e){
			dto=null;
		}
		
		return dto;
	}
}
