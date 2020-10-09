package ca.mcgill.ecse321.onlinegallery.dao;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.onlinegallery.model.*;
public interface ArtistRepository extends CrudRepository<Artist, Long> {
	
	// find object by ID
	Artist findArtistByArtistId(Long artistId);
}