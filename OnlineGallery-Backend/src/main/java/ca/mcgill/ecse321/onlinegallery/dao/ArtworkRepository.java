package ca.mcgill.ecse321.onlinegallery.dao;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.onlinegallery.model.Artwork;

public interface ArtworkRepository extends CrudRepository<Artwork, String> {
	Artwork findArtworkByName(String name);
}
