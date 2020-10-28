package ca.mcgill.ecse321.onlinegallery.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

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
		
		String username=dto.getUsername();		
		if (!regRepo.existsByUserName(username)) {
			throw new ArtworkException("No GalleryRegistration with username ["+username+"] exists");
		}
		
		GalleryRegistration reg=regRepo.findGalleryRegisrationByUserName(username);
		if (reg.getArtist()==null) {
			throw new ArtworkException("No artist associated with username ["+username+"]");
		}
		
		Artist artist = reg.getArtist();
		Artwork art = new Artwork();
		
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
	
	@Transactional 
	public Set<Artwork> getAvailableArtworkByArtistId(Long artistId) throws ArtworkException{
		if (!artistRepo.existsById(artistId)) {
			throw new  ArtworkException("No artist with artistId ["+artistId+"] exists");
		} 
		Artist artist = artistRepo.findArtistByArtistId(artistId);
		return artist.getArtwork();
	}
	
	@Transactional 
	public  List<Artwork> retrieveRandomAvailableArtworks(int numToRetrieve) throws ArtworkException{
		
		List<Artwork> artworkList = new ArrayList<Artwork>();
		artworkList = toList((Iterable<Artwork>) artworkRepo.findAll());
		List<Artwork> randomList = new ArrayList<Artwork>();
		Random rand = new Random();
		
		if (numToRetrieve > artworkList.size()) {
			throw new  ArtworkException("there is less than ["+numToRetrieve+"] artworks");
		} 
		
		for (int i = 0; i < numToRetrieve; i++) {
	        int randomIndex = rand.nextInt(artworkList.size());
	        randomList.add(artworkList.get(randomIndex));
	        artworkList.remove(randomIndex);
	    }
		return randomList;
	
		
	}
	@Transactional 
	public  List<Artwork> getAllArtworks() throws ArtworkException{
		
		List<Artwork> artworkList = new ArrayList<Artwork>();
		artworkList = toList((Iterable<Artwork>) artworkRepo.findAll());

		if (artworkList.size() == 0) {
			throw new  ArtworkException("there is no artworks");
		} 
		

		return artworkList;
	
		
	}
	
	private <T> List<T> toList(Iterable<T> iterable){
		List<T> resultList = new ArrayList<T>();
		for (T t : iterable) {
			resultList.add(t);
		}
		return resultList;
	}
	

}
