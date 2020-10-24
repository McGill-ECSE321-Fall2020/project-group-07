package ca.mcgill.ecse321.onlinegallery.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.onlinegallery.dao.ArtistRepository;
import ca.mcgill.ecse321.onlinegallery.dao.GalleryRegistrationRepository;
import ca.mcgill.ecse321.onlinegallery.model.Artist;
import ca.mcgill.ecse321.onlinegallery.model.ArtistForm;
import ca.mcgill.ecse321.onlinegallery.model.GalleryRegistration;
import ca.mcgill.ecse321.onlinegallery.service.exception.ArtistException;
import ca.mcgill.ecse321.onlinegallery.service.exception.GalleryRegistrationException;

@Service
public class ArtistService {

	@Autowired
	ArtistRepository artistRepo;
	
	@Autowired
	GalleryRegistrationRepository regRepo;
	
	@Transactional
	public Artist createArtist(ArtistForm form) throws ArtistException {
		
		String username = form.getUserName();
		
		if (!regRepo.existsByUserName(username)) {
			
			throw new ArtistException("An artist is already associated to the username ["+username+"]");
		}
		
		GalleryRegistration reg = regRepo.findGalleryRegisrationByUserName(username);
		
		if (reg.getArtist()==null) {
			
			reg.setArtist(new Artist());
		}
		
		Artist artist = reg.getArtist();
		
		artist.setBankInfo(form.getBankInfo());

		artistRepo.save(artist);
		
		return artist;
	}
	
	@Transactional
	public Artist findArtistByUsername(ArtistForm form) {
		
		String username = form.getUserName();
		
		if (!regRepo.existsByUserName(username)) {
			
			return null;
		}
		
		GalleryRegistration reg = regRepo.findGalleryRegisrationByUserName(username);
		
		if (reg.getArtist()==null) {
			
			return null;
		}
				
		return reg.getArtist();
	}
	
	@Transactional
	public Artist updateBankInfo(ArtistForm form) {
		
		String username = form.getUserName();

		if (!regRepo.existsByUserName(username)) {
			
			return null;
		}
		
		GalleryRegistration reg = regRepo.findGalleryRegisrationByUserName(username);
		Artist artist = reg.getArtist();
		
		if(artist == null) {
			
			return null;
		}
		
		artist.setBankInfo(form.getBankInfo());

		artistRepo.save(artist);
		
		return artist;
	}
	
	@Transactional
	public Artist deleteArtistByUserName(ArtistForm form) {
		
		String username = form.getUserName();

		if (!regRepo.existsByUserName(username)) {
			
			return null;
		}
		
		GalleryRegistration reg = regRepo.findGalleryRegisrationByUserName(username);
		Artist artist = reg.getArtist();
		
		if(artist == null) {
			
			return null;
		}
		
		reg.setArtist(null);
		artistRepo.delete(artist);
		
		return artist;
	}
	
}
