package ca.mcgill.ecse321.onlinegallery.service;

import javax.transaction.Transactional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.onlinegallery.dao.ArtistRepository;
import ca.mcgill.ecse321.onlinegallery.dao.GalleryRegistrationRepository;
import ca.mcgill.ecse321.onlinegallery.dto.ArtistDto;
import ca.mcgill.ecse321.onlinegallery.model.Artist;
import ca.mcgill.ecse321.onlinegallery.model.ArtistForm;
import ca.mcgill.ecse321.onlinegallery.model.GalleryRegistration;
import ca.mcgill.ecse321.onlinegallery.service.exception.ArtistException;

@Service
public class ArtistService {

	@Autowired
	ArtistRepository artistRepo;
	
	@Autowired
	GalleryRegistrationRepository regRepo;
	
	@Transactional
	public Artist createArtist(ArtistDto dto) throws ArtistException {
		
		String username = dto.getUsername();
		
		if (!regRepo.existsByUserName(username)) {
			
			throw new ArtistException("An artist is already associated to the username ["+username+"]");
		}
		
		GalleryRegistration reg = regRepo.findGalleryRegisrationByUserName(username);
		
		if (reg.getArtist()==null) {
			
			reg.setArtist(new Artist());
		}
		
		Artist artist = reg.getArtist();
		
		artist.setBankInfo(dto.getBankInfo());

		artistRepo.save(artist);
		
		return artist;
	}
	
	@Transactional
	public Artist findArtistByUsername(ArtistDto dto) throws ArtistException {
		
		String username = dto.getUsername();
		
		if (!regRepo.existsByUserName(username)) {
			
			throw new ArtistException("No registration exists under the username ["+username+"]");
		}
		
		GalleryRegistration reg = regRepo.findGalleryRegisrationByUserName(username);
		
		if (reg.getArtist()==null) {
			
			throw new ArtistException("No artist exists under the username ["+username+"]");
		}
				
		return reg.getArtist();
	}
	
	@Transactional
	public Artist updateBankInfo(ArtistDto dto) throws ArtistException {
		
		String username = dto.getUsername();

		if (!regRepo.existsByUserName(username)) {
			
			throw new ArtistException("No registration exists under the username ["+username+"]");
		}
		
		GalleryRegistration reg = regRepo.findGalleryRegisrationByUserName(username);
		Artist artist = reg.getArtist();
		
		if(artist == null) {
			
			throw new ArtistException("No artist exists under the username ["+username+"]");
		}
		
		artist.setBankInfo(dto.getBankInfo());

		artistRepo.save(artist);
		
		return artist;
	}
	
	@Transactional
	public Artist deleteArtistByUserName(ArtistDto dto) throws ArtistException {
		
		String username = dto.getUsername();

		if (!regRepo.existsByUserName(username)) {
			
			throw new ArtistException("No registration exists under the username ["+username+"]");
		}
		
		GalleryRegistration reg = regRepo.findGalleryRegisrationByUserName(username);
		Artist artist = artistRepo.findArtistByArtistId(dto.getArtistId());

		if(artist == null) {
			
			throw new ArtistException("No artist exists under the username ["+username+"]");
		}
		
		reg.setArtist(null);
		artistRepo.delete(artist);
		
		return artist;
	}
	
}
