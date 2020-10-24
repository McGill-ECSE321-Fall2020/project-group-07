package ca.mcgill.ecse321.onlinegallery.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.onlinegallery.model.*;
import ca.mcgill.ecse321.onlinegallery.dao.*;
import ca.mcgill.ecse321.onlinegallery.service.exception.PurchaseException;

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
	public Purchase createPurchase(PurchaseForm form) throws PurchaseException{
		
		String username=form.getUserName();
		Long artworkId=form.getArtworkId();
		Customer customer;
		
		if (!regRepo.existsByUserName(username)) {
			throw new PurchaseException("no customer with the username ["+username+"] found in system");
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
			customer=new Customer();
			reg.setCustomer(customer);
			customer.setGalleryRegistration(reg);

		}
		else {
			customer=reg.getCustomer();
		}
		
		
		if (purchaseRepo.existsByCustomerAndArtwork(customer, art)) {
			throw new PurchaseException("a Purchase between an Artwork with id [" + artworkId
					+ "] and a Customer associated with a GalleryRegistration with username [" + username
					+ "] already exists");
		}
		
		Purchase purchase=new Purchase();
		
		purchase.setArtwork(art);
		art.setPurchase(purchase);
		art.setStatus(ArtworkStatus.SOLD);
				
		customer.getPurchase().add(purchase);
		purchase.setCustomer(customer);
		
		custRepo.save(customer);
		
		return purchase;
	}
	
	@Transactional
	public Purchase getPurchaseByUserNameAndArtId(PurchaseForm form) throws PurchaseException{
		
		String username=form.getUserName();
		Long artworkId=form.getArtworkId();
		
		
		if (!regRepo.existsByUserName(username)) {
			throw new PurchaseException("no customer with the username ["+username+"] found in system");
			}
		if (!artworkRepo.existsByArtworkId(artworkId)) {
			throw new PurchaseException("no artwork with id ["+artworkId+"] found in system");
		}
		
		GalleryRegistration reg = regRepo.findGalleryRegisrationByUserName(username);
		
		Customer customer = reg.getCustomer();
		Artwork artwork = artworkRepo.findArtworkByArtworkId(artworkId);
		
		if (customer==null) {
			throw new PurchaseException("GalleryRegistration with username ["+username+"] has no Customer associated");
		}
		
		Purchase purchase = purchaseRepo.findByCustomerAndArtwork(customer,artwork);
		return purchase;
	}
	
	@Transactional
	public Purchase updatePurchase(PurchaseUpdateForm form)  throws PurchaseException{
		
		String username=form.getUserName();
		Long artworkId=form.getArtworkId();
		
		
		if (!regRepo.existsByUserName(username)) {
			throw new PurchaseException("no customer with the username ["+username+"] found in system");
			}
		if (!artworkRepo.existsByArtworkId(artworkId)) {
			throw new PurchaseException("no artwork with id ["+artworkId+"] found in system");
		}
		
		GalleryRegistration reg = regRepo.findGalleryRegisrationByUserName(username);
		
		Customer customer = reg.getCustomer();
		Artwork artwork = artworkRepo.findArtworkByArtworkId(artworkId);
		
		if (customer==null) {
			throw new PurchaseException("GalleryRegistration with username ["+username+"] has no Customer associated");
		}
		
		Purchase purchase = purchaseRepo.findByCustomerAndArtwork(customer,artwork);
		
		if (purchase==null) {
			throw new PurchaseException("no such Purchase between an Artwork with id [" + artworkId
					+ "] and a Customer associated with a GalleryRegistration with username [" + username+"]");
		}
		
		purchase.setCommission(form.getCommission());
		purchase.setShipmentType(form.getShipmentType());
		purchase.setPaymentMethod(form.getPaymentMethod());
		purchase.setPaid(form.isPaid());
		
		return purchase;
	}
	
	@Transactional
	public Purchase deletePurchaseByUserNameAndArtId(PurchaseForm form) throws PurchaseException{
		
		String username=form.getUserName();
		Long artworkId=form.getArtworkId();
		
		
		if (!regRepo.existsByUserName(username)) {
			throw new PurchaseException("no customer with the username ["+username+"] found in system");
			}
		if (!artworkRepo.existsByArtworkId(artworkId)) {
			throw new PurchaseException("no artwork with id ["+artworkId+"] found in system");
		}
		
		GalleryRegistration reg = regRepo.findGalleryRegisrationByUserName(username);
		
		Customer customer = reg.getCustomer();
		Artwork artwork = artworkRepo.findArtworkByArtworkId(artworkId);
		
		if (customer==null) {
			throw new PurchaseException("GalleryRegistration with username ["+username+"] has no Customer associated");
		}
		
		Purchase purchase = purchaseRepo.findByCustomerAndArtwork(customer,artwork);
		if (purchase==null) {
			throw new PurchaseException("no such Purchase between an Artwork with id [" + artworkId
					+ "] and a Customer associated with a GalleryRegistration with username [" + username+"]");
		}
		
		
		customer.getPurchase().remove(purchase);
		artwork.setPurchase(null);
		artwork.setStatus(ArtworkStatus.AVAILABLE);
			
		purchaseRepo.delete(purchase);
		
		return purchase;
	}
}
