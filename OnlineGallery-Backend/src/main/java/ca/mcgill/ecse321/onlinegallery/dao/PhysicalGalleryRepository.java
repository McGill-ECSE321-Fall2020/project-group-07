package ca.mcgill.ecse321.onlinegallery.dao;


import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.onlinegallery.model.*;

public interface PhysicalGalleryRepository extends CrudRepository<PhysicalGallery, Long> {
	PhysicalGallery findPhysicalGalleryByGalleryId (Long id);	
}