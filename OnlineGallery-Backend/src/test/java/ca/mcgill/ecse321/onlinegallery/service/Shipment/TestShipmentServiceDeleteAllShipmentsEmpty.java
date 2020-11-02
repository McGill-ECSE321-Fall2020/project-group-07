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

import ca.mcgill.ecse321.onlinegallery.dao.PurchaseRepository;
import ca.mcgill.ecse321.onlinegallery.dao.ShipmentRepository;
import ca.mcgill.ecse321.onlinegallery.model.Shipment;
import ca.mcgill.ecse321.onlinegallery.service.ShipmentService;
import ca.mcgill.ecse321.onlinegallery.service.exception.ShipmentException;

@ExtendWith(MockitoExtension.class)
public class TestShipmentServiceDeleteAllShipmentsEmpty {
	
	@Mock
	private ShipmentRepository shipRepo; 
	
	@Mock
	private PurchaseRepository purchaseRepo;
	
	@InjectMocks
	private ShipmentService service;
	
	@BeforeEach
	public void setMockOutput() {
	
		lenient().when(shipRepo.count()).thenReturn((long) 0);
	}
	
	@Test
	public void testDeleteAllTwoShipmentsEmpty() {
		List<Shipment> allReg = null;
		String error = null; 
		try {
			allReg = service.deleteAllShipments();
		} catch (ShipmentException e) {
			error = e.getMessage();
		}
		assertNull(allReg);
		assertEquals(error, "no Shipments in system"); 
	}

}
