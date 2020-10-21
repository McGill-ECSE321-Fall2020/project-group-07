package ca.mcgill.ecse321.onlinegallery.dao;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.onlinegallery.model.*;
public interface ArtworkRepository extends CrudRepository<Artwork, Long> {
	
	Artwork findArtworkByArtworkId(Long artworkId);

}