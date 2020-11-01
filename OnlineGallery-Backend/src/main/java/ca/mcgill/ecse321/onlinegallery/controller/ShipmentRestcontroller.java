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

import ca.mcgill.ecse321.onlinegallery.dto.GalleryRegistrationDto;
import ca.mcgill.ecse321.onlinegallery.dto.PaymentDto;
import ca.mcgill.ecse321.onlinegallery.dto.PurchaseDto;
import ca.mcgill.ecse321.onlinegallery.dto.ShipmentDto;
import ca.mcgill.ecse321.onlinegallery.dto.UpdateEmailDto;
import ca.mcgill.ecse321.onlinegallery.model.GalleryRegistration;
import ca.mcgill.ecse321.onlinegallery.model.Purchase;
import ca.mcgill.ecse321.onlinegallery.model.Shipment;
import ca.mcgill.ecse321.onlinegallery.model.ShipmentStatus;
import ca.mcgill.ecse321.onlinegallery.service.ShipmentService;
import ca.mcgill.ecse321.onlinegallery.service.exception.CreditCardException;
import ca.mcgill.ecse321.onlinegallery.service.exception.GalleryRegistrationException;
import ca.mcgill.ecse321.onlinegallery.service.exception.PurchaseException;
import ca.mcgill.ecse321.onlinegallery.service.exception.ShipmentException;

@CrossOrigin(origins = "*")
@RestController
public class ShipmentRestcontroller {
	
	@Autowired
	ShipmentService service;
	
	@GetMapping(value={"/getAllShipments","/getAllShipments/"})
	public ResponseEntity<?> findAllShipments() throws ShipmentException{
		List<ShipmentDto> allShipDto = new ArrayList<ShipmentDto>();
		try {
			for (Shipment ship :service.getAllShipments()) {
				allShipDto.add(convertToShipmentDto(ship));
			}
			return new ResponseEntity<>(allShipDto,HttpStatus.OK);
		}
		catch(ShipmentException e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(value = { "/getShipment/{shipmentId}", "/getRegistration/{shipmentId}/" })
	public ResponseEntity<?> findGalleryRegistrationByUserName(@PathVariable("shipmentId") Long shipmentId) throws ShipmentException  {
		try {
			Shipment  ship =service.getShipment(shipmentId);
			return new ResponseEntity<>(convertToShipmentDto(ship), HttpStatus.OK);
		}
		catch(ShipmentException e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
			
		}
	}
	
	
	@PostMapping(value = { "/createShipment", "/createShipment/" })
	public ResponseEntity<?> createShipment(@RequestBody ShipmentDto dto) throws ShipmentException, PurchaseException{
		try {
			Shipment ship =service.createShipment(dto);
			return new ResponseEntity<>(convertToShipmentDto(ship), HttpStatus.CREATED);
		}
		catch(ShipmentException| PurchaseException e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping(value= {"/addToShipment/{shipmentId}/{purchaseId}","/addToShipment/{shipmentId}/{purchaseId}/"})
	public ResponseEntity<?> addToShipment(@PathVariable("shipmentId") Long shipmentId,@PathVariable("purchaseId") Long purchaseId ) throws ShipmentException, PurchaseException {
		try {
			Shipment ship = service.addToShipment(shipmentId, purchaseId);
			return new ResponseEntity<>(convertToShipmentDto(ship),HttpStatus.OK);
		}
		catch(ShipmentException | PurchaseException e){
			return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping(value= {"/payShipment","/payShipment/"})
	public ResponseEntity<?> payShipment(@RequestBody PaymentDto dto ) throws ShipmentException, CreditCardException {
		try {
			Shipment ship = service.payShipment(dto);
			//make sure response entity is paymentDto
			return new ResponseEntity<>(convertToShipmentDto(ship),HttpStatus.OK);
		}
		catch(ShipmentException | CreditCardException e){
			return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping(value = { "/deleteShipment/{shipmentId}", "/deleteShipment/{shipmentId}/" })
	public ResponseEntity<?> deleteShipment(@PathVariable ("shipmentId") Long shipmentId) throws ShipmentException  {
		try {
			Shipment ship=service.deleteShipment(shipmentId);
			return new ResponseEntity<>(convertToShipmentDto(ship), HttpStatus.OK);
		}
		catch(ShipmentException e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
			
		}
	}
	
	@DeleteMapping(value = { "/deleteAllShipments", "/deleteAllShipments/" })
	public ResponseEntity<?> deleteAllShipments() throws ShipmentException {
		List<ShipmentDto> allShipDto = new ArrayList<ShipmentDto>();
		try {
			for (Shipment ship :service.deleteAllShipments()) {
				allShipDto.add(convertToShipmentDto(ship));
			}
			return new ResponseEntity<>(allShipDto,HttpStatus.OK);
		}
		catch(ShipmentException e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
	}
	
	
	private ShipmentDto convertToShipmentDto (Shipment shipment) {
		System.out.println(shipment.getShipmentId());

		long shipmentId = shipment.getShipmentId();
		String sourceAddress = shipment.getSourceAddress();
		String destinationAddress = shipment.getDestinationAddress();
		double shippingCost = shipment.getShippingCost();
		double totalCost = shipment.getTotalAmount();
		String recipient = shipment.getRecipientName();
		ShipmentStatus status = shipment.getShipmentStatus();
		
		ShipmentDto shipDto = new ShipmentDto();
		List<Long> purchases = new ArrayList<Long>();
		long customerId  = 0;
		for (Purchase p : shipment.getPurchase()) {
			customerId = p.getCustomer().getCustomerId();
			purchases.add(p.getPurchaseId());
		}
		
		shipDto.setPurchases(purchases);
		shipDto.setShipmentId(shipmentId);
		shipDto.setSourceAddress(sourceAddress);
		shipDto.setDestinationAddress(destinationAddress);
		shipDto.setShippingCost(shippingCost);
		shipDto.setTotalCost(totalCost);
		shipDto.setRecipientName(recipient);
		shipDto.setCustomerId(customerId);
		shipDto.setShipmentStatus(status);
	
		
		
		return shipDto;
		
	}
	
	

}
