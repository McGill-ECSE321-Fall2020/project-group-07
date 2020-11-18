package ca.mcgill.ecse321.onlinegallery.service.Shipment;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.lenient;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ca.mcgill.ecse321.onlinegallery.dao.*;
import ca.mcgill.ecse321.onlinegallery.model.*;
import ca.mcgill.ecse321.onlinegallery.service.ShipmentService;
import ca.mcgill.ecse321.onlinegallery.service.exception.*;

@ExtendWith(MockitoExtension.class)
public class TestShipmentServiceGetAllEmpty {

	@Mock
	private ShipmentRepository shipRepo;

	@InjectMocks
	private ShipmentService service;

	@BeforeEach
	public void setMockOutput() {

		lenient().when(shipRepo.count()).thenReturn((long) 0);
	}

	@Test
	public void testGetAllShipmentNonEmpty() {

		List<Shipment> allS = null;
		String error = null;
		try {
			allS = service.getAllShipments();
		} catch (ShipmentException e) {
			error = e.getMessage();
		}
		assertNull(allS);
		assertEquals(error, "no Shipments in system");

	}

}
