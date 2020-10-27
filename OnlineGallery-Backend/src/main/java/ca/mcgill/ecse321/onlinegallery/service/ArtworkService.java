package ca.mcgill.ecse321.onlinegallery.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.onlinegallery.dao.ArtworkRepository;
import ca.mcgill.ecse321.onlinegallery.dao.GalleryRegistrationRepository;
import ca.mcgill.ecse321.onlinegallery.dao.OnlineGalleryRepository;
import ca.mcgill.ecse321.onlinegallery.dao.PhysicalGalleryRepository;
import ca.mcgill.ecse321.onlinegallery.dto.ArtworkDto;
import ca.mcgill.ecse321.onlinegallery.model.Artist;
import ca.mcgill.ecse321.onlinegallery.model.Artwork;
import ca.mcgill.ecse321.onlinegallery.model.GalleryRegistration;
import ca.mcgill.ecse321.onlinegallery.model.OnlineGallery;
import ca.mcgill.ecse321.onlinegallery.model.PhysicalGallery;
import ca.mcgill.ecse321.onlinegallery.service.exception.ArtworkException;

@Service
public class ArtworkService {

	@Autowired
	ArtworkRepository artRepo;
	
	@Autowired
	GalleryRegistrationRepository regRepo;

	@Transactional
	public Artwork createArtwork(ArtworkDto dto) throws ArtworkException {
		
		String username=dto.getUsername();
				
		if (!regRepo.existsByUserName(username)) {
			return null;
			}
		
		GalleryRegistration reg=regRepo.findGalleryRegisrationByUserName(username);
		if (reg.getArtist()==null) {
			reg.setArtist(new Artist());;
			}
		
		Artist artist = regRepo.findGalleryRegisrationByUserName(username).getArtist();
		Artwork art = new Artwork();
		
		art.setName(dto.getName());
		art.setDescription(dto.getDescription());
		art.setPrice(dto.getPrice());
		art.setStatus(dto.getStatus());
		art.setNumViews(dto.getNumViews());
		art.setDimension(dto.getDimension());
		art.setWeight(dto.getWeight());
		art.setCommission(dto.getComission());
		
		
		artist.getArtwork().add(art);
		art.setArtist(artist);
		
		artRepo.save(art);
		
		return art;
		
		
	}


}
