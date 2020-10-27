package ca.mcgill.ecse321.onlinegallery.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.onlinegallery.dao.ArtworkRepository;
import ca.mcgill.ecse321.onlinegallery.dao.GalleryRegistrationRepository;
import ca.mcgill.ecse321.onlinegallery.dao.OnlineGalleryRepository;
import ca.mcgill.ecse321.onlinegallery.dao.PhysicalGalleryRepository;
import ca.mcgill.ecse321.onlinegallery.dao.ArtistRepository;
import ca.mcgill.ecse321.onlinegallery.dto.ArtworkDto;
import ca.mcgill.ecse321.onlinegallery.model.Artist;
import ca.mcgill.ecse321.onlinegallery.model.Artwork;
import ca.mcgill.ecse321.onlinegallery.model.ArtworkStatus;
import ca.mcgill.ecse321.onlinegallery.model.GalleryRegistration;
import ca.mcgill.ecse321.onlinegallery.model.OnlineGallery;
import ca.mcgill.ecse321.onlinegallery.model.PhysicalGallery;
import ca.mcgill.ecse321.onlinegallery.service.exception.ArtworkException;

@Service
public class ArtworkService {

	@Autowired
	ArtworkRepository artworkRepo;
	
	@Autowired
	ArtistRepository artRepo;
	
	@Autowired
	GalleryRegistrationRepository regRepo;
	

	
	@Transactional
	public Artwork getAvailableArtworkDetail(Long artworkId) throws ArtworkException{
		if (!artworkRepo.existsByArtworkId(artworkId)) {
			throw new  ArtworkException("No Available Artwork with artworkID ["+artworkId+"] exists");
		} 

		Artwork artwork = artworkRepo.findArtworkByArtworkId(artworkId);
		if (artwork.getStatus() == ArtworkStatus.UNAVAILABLE) {
			throw new  ArtworkException("No AvailableArtwork with artworkID ["+artworkId+"] exists");
		} 
		return artwork;
	}
	
	@Transactional 
	public Set<Artwork> getAvailableArtworkByArtistId(Long artistId) throws ArtworkException{
		if (!artRepo.existsByArtistId(artistId)) {
			throw new  ArtworkException("No artist with artistId ["+artistId+"] exists");
		} 
		Artist artist = artRepo.findArtistByArtistId(artistId);
		return artist.getArtwork();
	}
	
//	Public  List<Artwork> retrieveRandomAvailableArtworks(int numToRetrieve) throws ArtworkException{
//		
//		ArrayList<Artwork> artworkList = new ArrayList<Artwork>();
//	
//		Iterator<Artwork> artworkIterable = (Iterator<Artwork>) artworkRepo.findAll().iterator();
//        for (Iterator<Artwork> t : artworkIterable) 
//        	artworkList.add((Artwork) t); 
//  
//
//		return artworkList;
//	
//		
//	}
	


}
