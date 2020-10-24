package ca.mcgill.ecse321.onlinegallery.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.onlinegallery.dao.ProfileRepository;
import ca.mcgill.ecse321.onlinegallery.model.Profile;
import ca.mcgill.ecse321.onlinegallery.model.ProfileUpdateForm;
import ca.mcgill.ecse321.onlinegallery.service.exception.ProfileException;

@Service
public class ProfileService {
	
	@Autowired
	ProfileRepository profileRepo;
	
	@Transactional
	public Profile createProfile(Long id) throws ProfileException{
		return new Profile();
	}
	
	@Transactional
	public Profile findPurchaseByPurchaseId(Long id) throws ProfileException{
		return new Profile();
	}
	
	@Transactional
	public Profile updateProfileInfo(ProfileUpdateForm form) throws ProfileException{
		return new Profile();
	}
	
	@Transactional
	public Profile deleteProfileByProfileId(Long id) throws ProfileException{
		return new Profile();
	}

}
