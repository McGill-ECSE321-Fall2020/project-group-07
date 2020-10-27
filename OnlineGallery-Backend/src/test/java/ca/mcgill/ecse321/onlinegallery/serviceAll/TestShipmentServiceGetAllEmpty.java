package ca.mcgill.ecse321.onlinegallery.serviceAll;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import ca.mcgill.ecse321.onlinegallery.dao.*;
import ca.mcgill.ecse321.onlinegallery.dto.*;
import ca.mcgill.ecse321.onlinegallery.model.*;
import ca.mcgill.ecse321.onlinegallery.service.GalleryRegistrationService;
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
