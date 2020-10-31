package ca.mcgill.ecse321.onlinegallery.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.onlinegallery.dao.ArtistRepository;
import ca.mcgill.ecse321.onlinegallery.dao.GalleryRegistrationRepository;
import ca.mcgill.ecse321.onlinegallery.dao.ProfileRepository;
import ca.mcgill.ecse321.onlinegallery.dao.PurchaseRepository;
import ca.mcgill.ecse321.onlinegallery.dto.ProfileDto;
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
	public Artist findArtistById(Long artistId) throws ArtistException {
				
		if (!artistRepo.existsById(artistId)) {
			
			throw new ArtistException("No artist exists under the username ["+artistId+"]");
		}
		
		Artist artist = artistRepo.findArtistByArtistId(artistId);	
		return artist;
	}
	
	@Transactional
	public List<Artist> findAllArtist() throws ArtistException {
				
		if (toList(artistRepo.findAll()).size() == 0) {
			
			throw new ArtistException("No artists exists.");
		}
		
		List<Artist> artistList = toList(artistRepo.findAll());
		return artistList;
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
	public Artist createProfile(ProfileDto profileDto) throws ArtistException{
		
		String username = profileDto.getUsername();
		
		if(!regRepo.existsByUserName(username)) {
			throw new ArtistException("No registration exists under the username ["+username+"]");
		}
	
		GalleryRegistration reg = regRepo.findGalleryRegisrationByUserName(username);
	
		if (reg.getArtist()==null) {
			throw new ArtistException("No artist exists under the username ["+username+"]");
		}
		
		if (reg.getArtist().getProfile()!=null) {
			throw new ArtistException("This artist already has a profile");
		}

		Artist artist = reg.getArtist();
		Profile profile = new Profile();
		profile.setSelfDescription(profileDto.getSelfDescription());
		profile.setNumSold(0);
		profile.setRating(0.0);
		profile.setTotalEarnings(0.0);
		artist.setProfile(profile);	
	
		artist = artistRepo.save(artist);
		return artist;
	}
	
	@Transactional
	public Artist updateProfile(ProfileDto profileDto) throws ArtistException{
		
		String username = profileDto.getUsername();
		
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
		profile.setSelfDescription(profileDto.getSelfDescription());
		profile.setNumSold(profileDto.getNumSold());
		profile.setRating(profileDto.getRating());
		profile.setTotalEarnings(profileDto.getTotalEarnings());
		artistRepo.save(artist);
		
		return artist;
	}
	
	private <T> List<T> toList(Iterable<T> iterable){
		List<T> resultList = new ArrayList<T>();
		for (T t : iterable) {
			resultList.add(t);
		}
		return resultList;
	}
	
}
