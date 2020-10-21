package ca.mcgill.ecse321.onlinegallery.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.onlinegallery.model.*;
import ca.mcgill.ecse321.onlinegallery.dao.*;


@Service
public class GalleryRegistrationService {
	
	@Autowired
	OnlineGalleryRepository ogRepo;
	
	@Autowired
	GalleryRegistrationRepository regRepo;
	
	
	@Transactional
	public GalleryRegistration createGalleryRegistration(String username) {
		
		if (regRepo.existsByUserName(username)){
			return null;
		}
		
		OnlineGallery og;
		
		if (ogRepo.findAll().iterator().hasNext()) {
			og = ogRepo.findAll().iterator().next();
		} else {
			og = new OnlineGallery();
			og.setDaysUp(0);
		}
		
		GalleryRegistration reg = new GalleryRegistration();
		reg.setUserName(username);
		
		og.getAllRegistrations().add(reg);
		
		ogRepo.save(og);
		
		return reg;	
	}
	
	@Transactional
	public GalleryRegistration findGalleryRegistrationByUserName(String username) {
		return regRepo.findGalleryRegisrationByUserName(username);
	}
	
	@Transactional
	public GalleryRegistration deleteGalleryRegistrataionByUserName(String username) {
		if (!regRepo.existsByUserName(username)){
			return null;
		}
		
		if (ogRepo.count()==0) {
			return null;
		}
		
		OnlineGallery og=ogRepo.findAll().iterator().next();
		GalleryRegistration reg = regRepo.findGalleryRegisrationByUserName(username);
		
		og.getAllRegistrations().remove(reg);
		
		regRepo.delete(reg);
		ogRepo.save(og);
		
		return reg;
		
	}

}
