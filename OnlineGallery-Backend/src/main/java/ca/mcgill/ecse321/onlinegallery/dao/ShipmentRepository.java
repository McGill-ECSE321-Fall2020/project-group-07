package ca.mcgill.ecse321.onlinegallery.dao;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.onlinegallery.model.*;
public interface ShipmentRepository extends CrudRepository<Shipment, Long> {
	Shipment findShipmentByShipmentId(Long id);
}
