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
	public Artwork createArtwork(ArtworkDto dto) throws ArtworkException, ArtistException {
		
		String artistUserName = dto.getUsername();
		if(!regRepo.existsByUserName(dto.getUsername())){ 
			throw new ArtistException("User does not exist"); 
		}
		
		GalleryRegistration reg = regRepo.findGalleryRegisrationByUserName(artistUserName);
		
		if(reg.getArtist() == null) {
			throw new ArtistException("User is not an artist");
		}
		
		Artist artist = reg.getArtist();
		
		Artwork art = new Artwork();
		
;
		
		if(dto.getName() == null || dto.getDescription() == null || 
				dto.getPrice() <= 0 || dto.getDimension() == null || dto.getNumViews() != 0 ||
						dto.getStatus() == null || dto.getWeight() <= 0 || dto.getCommission() == 0){
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
	public List<Artwork> getAllAvailableArtwork() throws ArtworkException{
		
		if(toList(artworkRepo.findAll()).size() == 0) {
			throw new  ArtworkException("No artwork exists");
		} 

		List<Artwork> artworkList = toList(artworkRepo.findAll());
		List<Artwork> availableArtwork = new ArrayList<Artwork>();
		for(Artwork a: artworkList) {
			if (a.getStatus() == ArtworkStatus.AVAILABLE) {
				availableArtwork.add(a);
			} 
		}
		if(availableArtwork.size()==0) {
			throw new ArtworkException("No available artwork exists");
		}
		return availableArtwork;
	}
	
	@Transactional
	public Artwork getAvailableArtworkDetail(Long artworkId) throws ArtworkException{
		if (!artworkRepo.existsByArtworkId(artworkId)) {
			throw new  ArtworkException("No artwork with artworkID ["+artworkId+"] exists");
		} 

		Artwork artwork = artworkRepo.findArtworkByArtworkId(artworkId);
		if (artwork.getStatus() == ArtworkStatus.UNAVAILABLE) {
			throw new  ArtworkException("Artwork with artworkID ["+artworkId+"] is unavailable");
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
