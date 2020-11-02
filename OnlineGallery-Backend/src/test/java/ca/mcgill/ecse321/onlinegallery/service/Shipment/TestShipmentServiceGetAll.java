package ca.mcgill.ecse321.onlinegallery.service.Shipment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.lenient;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;

import ca.mcgill.ecse321.onlinegallery.dao.*;
import ca.mcgill.ecse321.onlinegallery.model.*;
import ca.mcgill.ecse321.onlinegallery.service.ShipmentService;
import ca.mcgill.ecse321.onlinegallery.service.exception.*;

@ExtendWith(MockitoExtension.class)
public class TestShipmentServiceGetAll {

	@Mock
	private ShipmentRepository shipRepo;


	@InjectMocks
	private ShipmentService service;



	@BeforeEach
	public void setMockOutput() {



		lenient().when(shipRepo.count()).thenReturn((long) 2);
		lenient().when(shipRepo.findAll()).thenAnswer((InvocationOnMock invocation)->{
			Shipment s1 = new Shipment();
			s1.setShipmentId((long) 1);
			
			Shipment s2 = new Shipment();
			s2.setShipmentId((long) 2);
			
			Set<Shipment> allS = new HashSet<Shipment>();
			
			allS.add(s1);
			allS.add(s2);
			
			return allS;
		});
	}

	@Test
	public void testGetAllShipmentNonEmpty() {

		List<Shipment> allS = null;
		try {
			allS=service.getAllShipments();
		} catch (ShipmentException e) {
			fail();
		}
		assertNotNull(allS);
		assertEquals(allS.size(),2);
		
		List<Long> expectedIds=new ArrayList<Long>(List.of((long) 1, (long) 2));
		
		for (Shipment s:allS) {
			assertEquals(true,expectedIds.contains(s.getShipmentId()));
		}
		
		
	} 




}
