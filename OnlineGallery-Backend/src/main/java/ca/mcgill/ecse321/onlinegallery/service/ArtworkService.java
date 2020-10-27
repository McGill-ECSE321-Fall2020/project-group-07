package ca.mcgill.ecse321.onlinegallery.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.onlinegallery.dao.ArtistRepository;
import ca.mcgill.ecse321.onlinegallery.dao.ArtworkRepository;
import ca.mcgill.ecse321.onlinegallery.dao.GalleryRegistrationRepository;
import ca.mcgill.ecse321.onlinegallery.dto.ArtworkDto;
import ca.mcgill.ecse321.onlinegallery.model.Artist;
import ca.mcgill.ecse321.onlinegallery.model.Artwork;
import ca.mcgill.ecse321.onlinegallery.model.ArtworkStatus;
import ca.mcgill.ecse321.onlinegallery.model.GalleryRegistration;
import ca.mcgill.ecse321.onlinegallery.service.exception.ArtistException;
import ca.mcgill.ecse321.onlinegallery.service.exception.ArtworkException;

@Service
public class ArtworkService {

	@Autowired
	ArtworkRepository artworkRepo;
	
	@Autowired
	ArtistRepository artistRepo;
	
	@Autowired
	GalleryRegistrationRepository regRepo;
	

	@Transactional
	public Artwork createArtwork(Long artistId, ArtworkDto dto) throws ArtworkException, ArtistException {
		
		if(!artistRepo.existsById(artistId)) {
			throw new ArtistException("No artist with ID ["+artistId+"] exists");
		}
		
		Artist artist = artistRepo.findArtistByArtistId(artistId);
		GalleryRegistration reg= artist.getGalleryRegistration();
		
		Artwork art = new Artwork();
		
		if(dto.getName() == null || dto.getDescription() == null || 
				dto.getPrice() <= 0 || dto.getDimension() == null || dto.getNumViews() != 0 ||
						dto.getStatus() == null || dto.getWeight() <= 0){
			throw new ArtworkException("Invalid artwork attributes");	
						}
		
		art.setName(dto.getName());
		art.setDescription(dto.getDescription());
		art.setPrice(dto.getPrice());
		art.setStatus(dto.getStatus());
		art.setNumViews(dto.getNumViews());
		art.setDimension(dto.getDimension());
		art.setWeight(dto.getWeight());
		art.setCommission(dto.getCommission());
		
		
		artist.getArtwork().add(art);
		art.setArtist(artist);
		
		artworkRepo.save(art);
		
		return art;
	}
	
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
}
