package ca.mcgill.ecse321.onlinegallery.service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.onlinegallery.model.*;
import ca.mcgill.ecse321.onlinegallery.dao.*;
import ca.mcgill.ecse321.onlinegallery.dto.PurchaseDto;
import ca.mcgill.ecse321.onlinegallery.service.exception.PurchaseException;

@Service
public class PurchaseService {
	
	@Autowired
	GalleryRegistrationRepository regRepo;

	@Autowired
	PurchaseRepository purchaseRepo;
	
	@Autowired
	CustomerRepository custRepo;
	
	@Autowired
	ArtworkRepository artworkRepo;

	@Transactional
	public Purchase createPurchase(PurchaseDto dto) throws PurchaseException{ 
		
		String username=dto.getUsername();
		Long artworkId=dto.getArtworkId();
		Customer customer; 
		
		if (!regRepo.existsByUserName(username)) {
			throw new PurchaseException("no GalleryRegistration with the username ["+username+"] found in system");
			}
		if (!artworkRepo.existsByArtworkId(artworkId)) {
			throw new PurchaseException("no artwork with id ["+artworkId+"] found in system");
			}
		
		GalleryRegistration reg = regRepo.findGalleryRegisrationByUserName(username);
		Artwork art = artworkRepo.findArtworkByArtworkId(artworkId);
		
		if (art.getStatus()!=ArtworkStatus.AVAILABLE) {
			throw new PurchaseException("Artwork with id ["+artworkId+"] no longer available for sale");	
		} 
				
		
		if (reg.getCustomer()==null) {
			throw new PurchaseException("no Customer associated with the GalleryRegistratrion username ["+username+"]");
		}
		
		customer=reg.getCustomer();
		
		Purchase purchase=new Purchase();
		
		purchase.setArtwork(art);
		art.setPurchase(purchase);
		art.setStatus(ArtworkStatus.UNAVAILABLE);
				
		customer.getPurchase().add(purchase);
		purchase.setCustomer(customer);
		
		purchase.setDate(Date.valueOf(LocalDate.now()));
		
		return purchaseRepo.save(purchase);
	
	}
	
	 
	@Transactional
	public Purchase getPurchase(PurchaseDto dto) throws PurchaseException{
		
		String username=dto.getUsername();
		Long artworkId=dto.getArtworkId();

		
		if (!regRepo.existsByUserName(username)) {
			throw new PurchaseException("no customer with the username ["+username+"] found in system");
			}
		if (!artworkRepo.existsByArtworkId(artworkId)) {
			throw new PurchaseException("no artwork with id ["+artworkId+"] found in system");
		}
		
		GalleryRegistration reg = regRepo.findGalleryRegisrationByUserName(username);
		Artwork artwork = artworkRepo.findArtworkByArtworkId(artworkId);
		
		Customer customer = reg.getCustomer();
		
		if (customer==null) {
			throw new PurchaseException("GalleryRegistration with username ["+username+"] has no Customer associated");
		}
		
		Purchase purchase = purchaseRepo.findByCustomerAndArtwork(customer,artwork);
		if (purchase==null) {
			throw new PurchaseException("No purchase exist between an artwork with id [" + artworkId
					+ "] and a Customer associated with username [" + username + "]");
		}
		return purchase;
	}
	
	@Transactional
	public List<Purchase> getAllPurchases() throws PurchaseException{
		if (purchaseRepo.count()==0) {
			throw new PurchaseException("no Purchase in system");
		}
		
		List<Purchase> allP = new ArrayList<Purchase>();
		
		for (Purchase p:purchaseRepo.findAll()) {
			allP.add(p);
		}
		return allP;
	}

	@Transactional
	public List<Purchase> getAllPurchases() throws PurchaseException{
		if (purchaseRepo.count()==0) {
			throw new PurchaseException("no Purchase in system");
		}
		
		List<Purchase> allP = new ArrayList<Purchase>();
		
		for (Purchase p:purchaseRepo.findAll()) {
			allP.add(p);
		}
		return allP;
	}
	
	@Transactional
	public Purchase updatePurchaseShipment(PurchaseDto dto)  throws PurchaseException{ 
		
		Purchase purchase=this.getPurchase(dto);
		purchase.setShipmentType(dto.getShipmentType());
		
		purchase=purchaseRepo.save(purchase);
		
		return purchase;
	}
	
	
	@Transactional
	public Purchase deletePurchase(PurchaseDto dto) throws PurchaseException{
		
		String username=dto.getUsername();
		Long artworkId=dto.getArtworkId();
		
		Purchase purchase=this.getPurchase(dto);
		Customer customer=regRepo.findGalleryRegisrationByUserName(username).getCustomer();
		Artwork artwork=artworkRepo.findArtworkByArtworkId(artworkId);
		
		customer.getPurchase().remove(purchase);
		artwork.setPurchase(null);
		artwork.setStatus(ArtworkStatus.AVAILABLE);
			
		purchaseRepo.delete(purchase);
		
		return purchase;
	}
}
