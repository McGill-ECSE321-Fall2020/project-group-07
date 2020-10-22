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
		Artwork art = artworkRepo.findArtworkByArtworkId(artworkId);
		
		if (reg.getCustomer()==null) {
			reg.setCustomer(new Customer());
		}
		
		Customer customer=reg.getCustomer();
		
		Purchase purchase=new Purchase();
		
		purchase.setArtwork(art);
		art.setPurchase(purchase);
				
		customer.getPurchase().add(purchase);
		purchase.setCustomer(customer);
		
		custRepo.save(customer);
		
		return purchase;
	}
	
	@Transactional
	public Purchase getPurchaseByRegistrationAndArtwork(PurchaseForm form) {
		
		String username=form.getUserName();
		Long artworkId=form.getArtworkId();
		
		System.out.println(username);
		System.out.println(artworkId);
		
		if (!regRepo.existsByUserName(username)) {return null;}
		if (!artworkRepo.existsByArtworkId(artworkId)) {return null;}
		
		GalleryRegistration reg = regRepo.findGalleryRegisrationByUserName(username);
		
		Customer customer = reg.getCustomer();
		Artwork artwork = artworkRepo.findArtworkByArtworkId(artworkId);
		
		if (customer==null || artwork==null) {return null;};
		
		System.out.println(customer==null);
		System.out.println(artwork==null);
		
		Purchase purchase = purchaseRepo.findByCustomerAndArtwork(customer,artwork);
		
		System.out.println(purchase==null);
		return purchase;
	}

}
