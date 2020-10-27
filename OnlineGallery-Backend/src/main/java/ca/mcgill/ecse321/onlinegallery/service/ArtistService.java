package ca.mcgill.ecse321.onlinegallery.service;

import javax.transaction.Transactional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.onlinegallery.dao.ArtistRepository;
import ca.mcgill.ecse321.onlinegallery.dao.GalleryRegistrationRepository;
import ca.mcgill.ecse321.onlinegallery.dao.ProfileRepository;
import ca.mcgill.ecse321.onlinegallery.dao.PurchaseRepository;
import ca.mcgill.ecse321.onlinegallery.model.Artist;
import ca.mcgill.ecse321.onlinegallery.model.GalleryRegistration;
import ca.mcgill.ecse321.onlinegallery.model.Profile;
import ca.mcgill.ecse321.onlinegallery.service.exception.ArtistException;

@Service
public class ArtistService {

	@Autowired
	ArtistRepository artistRepo;
	
	@Autowired
	GalleryRegistrationRepository regRepo;
	
	@Autowired
	ProfileRepository profileRepo;
	
	@Transactional
	public Artist findArtistByUsername(String username) throws ArtistException {
				
		if (!regRepo.existsByUserName(username)) {
			
			throw new ArtistException("No registration exists under the username ["+username+"]");
		}
		
		GalleryRegistration reg = regRepo.findGalleryRegisrationByUserName(username);
		
		if (reg.getArtist()==null) {
			
			throw new ArtistException("No artist exists under the username ["+username+"]");
		}
		Artist artist = reg.getArtist();	
		return artist;
	}
	
	
	@Transactional
	public Artist deleteArtistByUsername(String username) throws ArtistException {

		if (!regRepo.existsByUserName(username)) {
			
			throw new ArtistException("No registration exists under the username ["+username+"]");
		}
		
		GalleryRegistration reg = regRepo.findGalleryRegisrationByUserName(username);

		if(reg.getArtist() == null) {
			
			throw new ArtistException("No artist exists under the id ["+username+"]");
		}
		
		Artist artist = reg.getArtist();
		
		reg.setArtist(null);
		artistRepo.delete(artist);
		
		return artist;
	}
	
	@Transactional
	public Profile createProfile(String username, String newDesc) throws ArtistException{
		
		if(!regRepo.existsByUserName(username)) {
			throw new ArtistException("No registration exists under the username ["+username+"]");
		}
		
		GalleryRegistration reg = regRepo.findGalleryRegisrationByUserName(username);
		
		if(reg.getArtist() == null) {
			throw new ArtistException("No artist exists under the username ["+username+"]");
		}

		Artist artist = reg.getArtist();
		Profile profile = new Profile();
		profile.setSelfDescription(newDesc);
		artist.setProfile(profile);
		
		return profileRepo.save(profile);
	}
	
	@Transactional
	public Profile updateProfile(String username, String newDesc) throws ArtistException{
		
		if(!regRepo.existsByUserName(username)) {
			throw new ArtistException("No registration exists under the username ["+username+"]");
		}
		
		GalleryRegistration reg = regRepo.findGalleryRegisrationByUserName(username);
		
		if(reg.getArtist() == null) {
			throw new ArtistException("No artist exists under the username ["+username+"]");
		}
		
		if(reg.getArtist().getProfile() == null) {
			throw new ArtistException("No profile exists for this artist ["+username+"]");
		}

		Artist artist = reg.getArtist();
		Profile profile = artist.getProfile();
		profile.setSelfDescription(newDesc);
		
		return profileRepo.save(profile);
	}
	
}
