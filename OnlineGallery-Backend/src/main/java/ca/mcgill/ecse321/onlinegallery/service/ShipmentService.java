package ca.mcgill.ecse321.onlinegallery.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.onlinegallery.model.*;
import ca.mcgill.ecse321.onlinegallery.dao.*;
import ca.mcgill.ecse321.onlinegallery.dto.*;
import ca.mcgill.ecse321.onlinegallery.service.exception.*;

@Transactional
public class ShipmentService {
	
	@Autowired
	ShipmentRepository shipRepo;
	
	@Autowired
	PurchaseRepository purchaseRepo;
	

	@Transactional
	public Shipment addToShipment(Long shipmentId, Long purchaseId) throws ShipmentException, PurchaseException{
		if (!shipRepo.existsById(shipmentId)) {
			throw new ShipmentException("no Shipment with id ["+shipmentId+"] found");
		}
		if (!purchaseRepo.existsById(purchaseId)) {
			throw new PurchaseException("no Purchase with id ["+purchaseId+"] found");
		}
		
		Purchase purchase = purchaseRepo.findPurchaseByPurchaseId(purchaseId);
		Shipment shipment = shipRepo.findShipmentByShipmentId(shipmentId);
		
		if (shipment.getPurchase().iterator().hasNext()) {
			ShipmentType existingType = shipment.getPurchase().iterator().next().getShipmentType();
			ShipmentType newType = purchase.getShipmentType();
			if (!(existingType.name().equals(newType.name()))) {
				throw new ShipmentException("incompatible shipment types between existing Purchases in Shipment and Shipment to add");
			}
		}
		
		shipment.getPurchase().add(purchase);
		purchase.setShipment(shipment);
		shipRepo.save(shipment);
		
		return shipment;
	}
	
}
