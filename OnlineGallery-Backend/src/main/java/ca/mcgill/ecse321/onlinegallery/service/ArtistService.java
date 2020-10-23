package ca.mcgill.ecse321.onlinegallery.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.onlinegallery.dao.ArtistRepository;
import ca.mcgill.ecse321.onlinegallery.dao.GalleryRegistrationRepository;
import ca.mcgill.ecse321.onlinegallery.model.Artist;
import ca.mcgill.ecse321.onlinegallery.model.ArtistForm;
import ca.mcgill.ecse321.onlinegallery.model.GalleryRegistration;

@Service
public class ArtistService {

	@Autowired
	ArtistRepository artistRepo;
	
	@Autowired
	GalleryRegistrationRepository regRepo;
	
	@Transactional
	public Artist createArtist(ArtistForm form) {
		
		String username = form.getUserName();
		
		if (!regRepo.existsByUserName(username)) {
			
			return null;
		}
		
		GalleryRegistration reg = regRepo.findGalleryRegisrationByUserName(username);
		if (reg.getArtist()==null) {
			
			reg.setArtist(new Artist());;
		}
		
		Artist artist = regRepo.findGalleryRegisrationByUserName(username).getArtist();
		
		artist.setBankInfo(form.getBankInfo());
		
		artistRepo.save(artist);
		
		return artist;
	}
}
