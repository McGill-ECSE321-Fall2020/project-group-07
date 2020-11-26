package ca.mcgill.ecse321.onlinegallery.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

import ca.mcgill.ecse321.onlinegallery.dto.ApplicationDto;
import ca.mcgill.ecse321.onlinegallery.dto.PurchaseDto;
import ca.mcgill.ecse321.onlinegallery.dto.PurchaseSummaryDto;
import ca.mcgill.ecse321.onlinegallery.model.Purchase;
import ca.mcgill.ecse321.onlinegallery.model.ShipmentType;
import ca.mcgill.ecse321.onlinegallery.service.PurchaseService;
import ca.mcgill.ecse321.onlinegallery.service.SoldArtworksSummaryEntry;
import ca.mcgill.ecse321.onlinegallery.service.exception.PurchaseException;

@CrossOrigin(origins = "*")
@RestController
public class PurchaseRestController {

	@Autowired
	PurchaseService service;
	
	@PostMapping(value = { "/createPurchase", "/createPurchase/" })
	public ResponseEntity<?> createPurchase(@RequestBody PurchaseDto dto) throws PurchaseException {
		try {
			Purchase purchase = service.createPurchase(dto);
			return new ResponseEntity<>(convertToDto(purchase), HttpStatus.CREATED);			
		} catch(PurchaseException e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(value = { "/getPurchase", "/getPurchase/" })
	public ResponseEntity<?> getPurchase(@RequestBody PurchaseDto dto) throws PurchaseException {
		try {
			Purchase purchase = service.getPurchase(dto);
			return new ResponseEntity<>(convertToDto(purchase), HttpStatus.OK);			
		} catch(PurchaseException e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(value = { "/getPurchasesByCustomerUsername/{username}", "/getPurchasesByCustomerUsername/{username}/" })
	public ResponseEntity<?> getPurchasesByCustomerUsername(@PathVariable("username") String username) throws PurchaseException {
		try {
			List<Purchase> purchases = service.getPurchasesByCustomerUsername(username);
			return new ResponseEntity<>(convertToDto(purchases), HttpStatus.OK);			
		} catch(PurchaseException e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(value = { "/getAllPurchases", "/getAllPurchases/" })
	public ResponseEntity<?> getAllPurchases() throws PurchaseException {
		List<PurchaseDto> allPurchaseDto = new ArrayList<PurchaseDto>();
		try {
			for (Purchase purchase:service.getAllPurchases()) {
				allPurchaseDto.add(convertToDto(purchase)); 
			}
			return new ResponseEntity<>(allPurchaseDto,HttpStatus.OK);
		}
		catch(PurchaseException e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping(value = { "/updatePurchase", "/updatePurchase/" })
	public ResponseEntity<?> updatePurchaseShipment(@RequestBody PurchaseDto dto) throws PurchaseException {
		try {
			Purchase purchase = service.updatePurchaseShipment(dto);
			return new ResponseEntity<>(convertToDto(purchase),HttpStatus.OK);
		}
		catch(PurchaseException e){
			return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping(value = { "/deletePurchase", "/deletePurchase/" })
	public ResponseEntity<?> deletePurchase(@RequestBody PurchaseDto dto) throws PurchaseException {
		try {
			Purchase purchase = service.deletePurchase(dto);
			return new ResponseEntity<>(convertToDto(purchase),HttpStatus.OK);
		}
		catch(PurchaseException e){
			return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
	}
	
	private PurchaseDto convertToDto(Purchase purchase) {

		PurchaseDto purchaseDto = new PurchaseDto();
		purchaseDto.setPurchaseId(purchase.getPurchaseId());
		purchaseDto.setArtworkId(purchase.getArtwork().getArtworkId());
		purchaseDto.setUsername(purchase.getCustomer().getGalleryRegistration().getUserName());
		purchaseDto.setShipmentType(purchase.getShipmentType());
	
		return purchaseDto;
	}
	
	private List<PurchaseSummaryDto> convertToDto(List<Purchase> purchases) {

		List<PurchaseSummaryDto> dtos = new ArrayList<PurchaseSummaryDto>();
		
		for(int i=0; i<purchases.size(); i++) {
			
			PurchaseSummaryDto purchaseDto = new PurchaseSummaryDto();
			purchaseDto.setArtistName(purchases.get(i).getArtwork().getArtist().getGalleryRegistration().getUserName());
			purchaseDto.setCommission(purchaseDto.getComission());
			purchaseDto.setDatePurchased(purchases.get(i).getDate());
			purchaseDto.setName(purchases.get(i).getArtwork().getName());
			purchaseDto.setPrice(purchases.get(i).getArtwork().getPrice());
			purchaseDto.setShipmentType(purchases.get(i).getShipmentType());
			purchaseDto.setArtworkUrl(purchases.get(i).getArtwork().getUrl());
			
			dtos.add(purchaseDto);
			
		}
		
		return dtos;
	}

}