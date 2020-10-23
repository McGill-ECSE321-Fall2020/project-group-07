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
import ca.mcgill.ecse321.onlinegallery.model.Artist;
import ca.mcgill.ecse321.onlinegallery.model.Artwork;
import ca.mcgill.ecse321.onlinegallery.model.ArtworkForm;
import ca.mcgill.ecse321.onlinegallery.model.GalleryRegistration;
import ca.mcgill.ecse321.onlinegallery.model.OnlineGallery;
import ca.mcgill.ecse321.onlinegallery.model.PhysicalGallery;

@Service
public class ArtworkService {

	@Autowired
	ArtworkRepository artRepo;
	
	@Autowired
	GalleryRegistrationRepository regRepo;

	@Transactional
	public Artwork createArtworkInstance(ArtworkForm form) {
		
		String username=form.getUserName();
		System.out.println(form);

		if (!regRepo.existsByUserName(username)) {
			return null;
			}
		
		GalleryRegistration reg=regRepo.findGalleryRegisrationByUserName(username);
		if (reg.getArtist()==null) {
			reg.setArtist(new Artist());;
			}
		
		Artist artist = regRepo.findGalleryRegisrationByUserName(username).getArtist();
		Artwork art = new Artwork();
		
		art.setName(form.getName());
		art.setDescription(form.getDescription());
		art.setPrice(form.getPrice());
		art.setStatus(form.getStatus());
		art.setOnSite(form.isOnSite());
		art.setNumViews(form.getNumViews());
		art.setDimension(form.getDimension());
		art.setWeight(form.getWeight());
		art.setShippingCost(form.getShippingcost());
		
		
		artist.getArtwork().add(art);
		art.setArtist(artist);
		
		artRepo.save(art);
		
		return art;
		
		
	}


}
