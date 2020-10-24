package ca.mcgill.ecse321.onlinegallery.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.onlinegallery.dao.OnlineGalleryRepository;
import ca.mcgill.ecse321.onlinegallery.model.OnlineGallery;
import ca.mcgill.ecse321.onlinegallery.service.exception.OnlineGalleryException;
import ca.mcgill.ecse321.onlinegallery.service.exception.PhysicalGalleryException;

@Service
public class OnlineGalleryService {

	@Autowired
	OnlineGalleryRepository ogRepo;
	
	@Transactional
	public OnlineGallery createOnlineGallery(Long systemId) throws OnlineGalleryException{
		
		if(ogRepo.existsById(systemId)) {
			
			throw new OnlineGalleryException("an online gallery of ID ["+systemId+"] already exists in system");
		}
		
		OnlineGallery og = new OnlineGallery();
		og.setDaysUp(0);
		og.setSystemId(systemId);
		
		ogRepo.save(og);
		
		return og;
	}
	
	@Transactional
	public OnlineGallery findOnlineGalleryBySystemId(Long systemId) {
		
		if(!exist(systemId)) {
			
			return null;
		}
		
		return ogRepo.findOnlineGalleryBySystemId(systemId);
	}
	
	@Transactional
	public OnlineGallery deleteOnlineGalleryBySystemId(Long systemId) {
		
		if(!exist(systemId)) {
			
			return null;
		}
			
		OnlineGallery og = ogRepo.findOnlineGalleryBySystemId(systemId);
		
		ogRepo.delete(og);
		
		return og;
	}
	
	@Transactional
	public OnlineGallery updateDaysUp(int daysUp) {
		
		if(!ogRepo.findAll().iterator().hasNext()) {
			
			return null;
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
