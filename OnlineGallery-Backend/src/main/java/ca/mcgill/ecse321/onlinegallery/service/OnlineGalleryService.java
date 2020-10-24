package ca.mcgill.ecse321.onlinegallery.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.onlinegallery.dao.OnlineGalleryRepository;
import ca.mcgill.ecse321.onlinegallery.model.OnlineGallery;
import ca.mcgill.ecse321.onlinegallery.service.exception.OnlineGalleryException;

@Service
public class OnlineGalleryService {

	@Autowired
	OnlineGalleryRepository ogRepo;
	
	@Transactional
	public OnlineGallery createOnlineGallery(Long systemId) throws OnlineGalleryException{
		
		if(ogRepo.existsById(systemId)) {
			
			throw new OnlineGalleryException("An online gallery of ID ["+systemId+"] already exists in system.");
		}
		
		OnlineGallery og = new OnlineGallery();
		og.setDaysUp(0);
		og.setSystemId(systemId);
		
		ogRepo.save(og);
		
		return og;
	}
	
	@Transactional
	public OnlineGallery findOnlineGalleryBySystemId(Long systemId) throws OnlineGalleryException {
		
		if(!exist(systemId)) {
			
			throw new OnlineGalleryException("An online gallery of ID ["+systemId+"] does not exist.");
		}
		
		return ogRepo.findOnlineGalleryBySystemId(systemId);
	}
	
	@Transactional
	public OnlineGallery deleteOnlineGalleryBySystemId(Long systemId) throws OnlineGalleryException {
		
		if(!exist(systemId)) {
			
			throw new OnlineGalleryException("An online gallery of ID ["+systemId+"] does not exist.");
		}
			
		OnlineGallery og = ogRepo.findOnlineGalleryBySystemId(systemId);
		
		ogRepo.delete(og);
		
		return og;
	}
	
	@Transactional
	public OnlineGallery updateDaysUp(int daysUp) throws OnlineGalleryException {
		
		if(!ogRepo.findAll().iterator().hasNext()) {
			
			throw new OnlineGalleryException("No online gallery exists.");
		}
			
		OnlineGallery og = ogRepo.findAll().iterator().next();
		og.setDaysUp(daysUp);
		
		ogRepo.save(og);
		
		return og;
	}
	
	
	private boolean exist(Long systemId) {
		
		if (!ogRepo.existsById(systemId)) {
			
			return false;
		}
		
		return true;
	}
}
