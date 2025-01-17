package ca.mcgill.ecse321.onlinegallery.dao;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.onlinegallery.model.*;

public interface OnlineGalleryRepository extends CrudRepository<OnlineGallery, Long> {

	OnlineGallery findOnlineGalleryBySystemId(Long systemId);
}
