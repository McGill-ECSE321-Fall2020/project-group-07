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
	

	@Transactional Shipment addToShipment(Long shipmentId, Long purchaseId) throws ShipmentException, PurchaseException{
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
				throw new ShipmentException("Shipment type of purchase with id [" + purchaseId + "] is [" + newType
						+ "], which is incompatible with shipment types of existing purchases in Shipment with id ["
						+ shipmentId + "], which is [" + existingType + "]");
			}
		}
		
		shipment.getPurchase().add(purchase);
		shipment=shipRepo.save(shipment);
		
		return shipment;
	}
	
}
