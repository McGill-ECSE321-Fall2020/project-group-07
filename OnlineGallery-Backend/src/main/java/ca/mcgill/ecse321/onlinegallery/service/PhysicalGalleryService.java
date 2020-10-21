package ca.mcgill.ecse321.onlinegallery.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.onlinegallery.dao.OnlineGalleryRepository;
import ca.mcgill.ecse321.onlinegallery.dao.PhysicalGalleryRepository;
import ca.mcgill.ecse321.onlinegallery.model.OnlineGallery;
import ca.mcgill.ecse321.onlinegallery.model.PhysicalGallery;

@Service
public class PhysicalGalleryService {

	@Autowired
	OnlineGalleryRepository onlineRepo;

	@Autowired
	PhysicalGalleryRepository physicalRepo;

	@Transactional
	public PhysicalGallery createPhysicalGallery(String address) {
		
		if (physicalRepo.findAll().iterator().hasNext()) {
			return physicalRepo.findAll().iterator().next();
		}
		
		OnlineGallery og;
		
		if (onlineRepo.findAll().iterator().hasNext()) {
			og = onlineRepo.findAll().iterator().next();
		} else {
			og = new OnlineGallery();
			og.setDaysUp(0);
		}

		PhysicalGallery pg = new PhysicalGallery();
		pg.setAddress(address);

		og.setPhysicalGallery(pg);

		onlineRepo.save(og);
		return pg;
	}

	@Transactional
	public PhysicalGallery updateAddress(String newAddress) {
		
		if (!physicalRepo.findAll().iterator().hasNext()) {
			return null;
		}
		
		OnlineGallery og;
		PhysicalGallery pg;

		og = onlineRepo.findAll().iterator().next();
		pg=physicalRepo.findAll().iterator().next();
		pg.setAddress(newAddress);
		
		onlineRepo.save(og);
		return pg;
	}

	@Transactional
	public PhysicalGallery getPhysicalGallery() {
		if (physicalRepo.findAll().iterator().hasNext()) {
			return physicalRepo.findAll().iterator().next();
		} else {
			return null;
		}
	}
	
	@Transactional
	public PhysicalGallery deletePhysicalGallery() {
		OnlineGallery og;
		
		if (onlineRepo.findAll().iterator().hasNext()) {
			og = onlineRepo.findAll().iterator().next();
		} else {
			og = new OnlineGallery();
			og.setDaysUp(0);
		}
		
		PhysicalGallery pg=og.getPhysicalGallery();
		og.setPhysicalGallery(null);
		
		physicalRepo.deleteAll();
		
		return pg;
	}

}
