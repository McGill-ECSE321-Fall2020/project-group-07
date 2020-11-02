package ca.mcgill.ecse321.onlinegallery.dao;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.onlinegallery.model.*;
public interface GalleryRegistrationRepository extends CrudRepository<GalleryRegistration, String> {
	
	GalleryRegistration findGalleryRegisrationByUserName(String userName);
	GalleryRegistration findGalleryRegistrationByEmailAndPassword(String email, String password);
	boolean existsByUserName(String userName);
	boolean existsByEmailAndPassword(String email, String password);
	
}
