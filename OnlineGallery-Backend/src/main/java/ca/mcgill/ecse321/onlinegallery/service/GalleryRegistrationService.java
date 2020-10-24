package ca.mcgill.ecse321.onlinegallery.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.onlinegallery.model.*;
import ca.mcgill.ecse321.onlinegallery.dao.*;
import ca.mcgill.ecse321.onlinegallery.service.exception.GalleryRegistrationException;

@Service
public class GalleryRegistrationService {

	@Autowired
	OnlineGalleryRepository ogRepo;

	@Autowired
	GalleryRegistrationRepository regRepo;

	@Transactional
	public GalleryRegistration createGalleryRegistration(String username) throws GalleryRegistrationException{

		if (regRepo.existsByUserName(username)) {
			throw new GalleryRegistrationException("The username ["+username+"] already exists, pick a new one");
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
	public GalleryRegistration findGalleryRegistrationByUserName(String username) throws GalleryRegistrationException{
		
		GalleryRegistration reg=regRepo.findGalleryRegisrationByUserName(username);
		if (reg==null) {
			throw new GalleryRegistrationException("There is no GalleryRegistration associated with the username ["+username+"]");
		}
		return reg;
	}

	@Transactional
	public GalleryRegistration deleteGalleryRegistrationByUserName(String username) throws GalleryRegistrationException{
		if (!regRepo.existsByUserName(username)) {
			throw new GalleryRegistrationException("There is no GalleryRegistration associated with the username ["+username+"]");
		}

		OnlineGallery og = ogRepo.findAll().iterator().next();
		GalleryRegistration reg = regRepo.findGalleryRegisrationByUserName(username);
		

		og.getAllRegistrations().remove(reg);

		regRepo.delete(reg);
		ogRepo.save(og);

		return reg;

	}

	@Transactional
	public GalleryRegistration updateRegistrationInfo(RegistrationUpdateForm form) throws GalleryRegistrationException{

		String username = form.getUserName();

		if (!regRepo.existsByUserName(username)) {
			throw new GalleryRegistrationException("There is no GalleryRegistration associated with the username ["+username+"]");
		}

		GalleryRegistration reg = regRepo.findGalleryRegisrationByUserName(username);

		reg.setFirstName(form.getFirstName());
		reg.setLastName(form.getLastName());
		reg.setEmail(form.getEmail());
		reg.setPhoneNumber(form.getPhoneNumber());

		regRepo.save(reg);

		return reg;
	}
	
	@Transactional
	public GalleryRegistration changePassword(PasswordUpdateForm form) throws GalleryRegistrationException{
		
		String username = form.getUserName();

		if (!regRepo.existsByUserName(username)) {
			throw new GalleryRegistrationException("There is no GalleryRegistration associated with the username ["+username+"]");
		}
		
		GalleryRegistration reg = regRepo.findGalleryRegisrationByUserName(username);

		reg.setPassWord(form.getPassword());

		regRepo.save(reg);

		return reg;
	}
	
	@Transactional
	public GalleryRegistration toggleLoggedInStatus(String username) throws GalleryRegistrationException{
		
		if (!regRepo.existsByUserName(username)) {
			throw new GalleryRegistrationException("There is no GalleryRegistration associated with the username ["+username+"]");
		}
		
		
		GalleryRegistration reg = regRepo.findGalleryRegisrationByUserName(username);

		reg.setIsLoggedIn(!reg.getIsLoggedIn());

		regRepo.save(reg);

		return reg;
	}
	

}
