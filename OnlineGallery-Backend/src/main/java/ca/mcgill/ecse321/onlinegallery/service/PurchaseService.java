package ca.mcgill.ecse321.onlinegallery.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.onlinegallery.model.*;
import ca.mcgill.ecse321.onlinegallery.dao.*;

@Service
public class PurchaseService {
	
	@Autowired
	GalleryRegistrationRepository regRepo;

	@Autowired
	ShipmentRepository shipRepo;

	@Autowired
	PurchaseRepository purchaseRepo;
	
	@Autowired
	CustomerRepository custRepo;
	
	@Autowired
	ArtworkRepository artworkRepo;

	@Transactional
	public Purchase createPurchase(PurchaseForm form) {
		
		String username=form.getUserName();
		Long artworkId=form.getArtworkId();
		
		if (!regRepo.existsByUserName(username)) {return null;}
		if (!artworkRepo.existsByArtworkId(artworkId)) {return null;}
		
		GalleryRegistration reg = regRepo.findGalleryRegisrationByUserName(username);
		
		if (reg.getCustomer()==null) {
			reg.setCustomer(new Customer());
			
		}
		
		Customer customer=reg.getCustomer();
		
		Purchase purchase=new Purchase();
				
		customer.getPurchase().add(purchase);
		
		purchaseRepo.save(purchase);
		
		return purchase;
	}

}
