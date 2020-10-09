package ca.mcgill.ecse321.onlinegallery.dao;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.onlinegallery.model.*;

public interface OnlineGalleryRepository extends CrudRepository<OnlineGallery, Long> {

	// find object by ID
	OnlineGallery findOnlineGalleryBySystemId(Long systemId);
}
