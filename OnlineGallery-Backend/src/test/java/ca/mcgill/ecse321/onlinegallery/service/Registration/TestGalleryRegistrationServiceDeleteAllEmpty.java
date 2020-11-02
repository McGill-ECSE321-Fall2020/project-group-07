package ca.mcgill.ecse321.onlinegallery.service.Registration;

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
import ca.mcgill.ecse321.onlinegallery.service.GalleryRegistrationService;
import ca.mcgill.ecse321.onlinegallery.service.exception.*;

@ExtendWith(MockitoExtension.class)
public class TestGalleryRegistrationServiceDeleteAllEmpty {

	@Mock
	private GalleryRegistrationRepository regRepo;

	@Mock
	private OnlineGalleryRepository ogRepo;

	@Mock
	private GalleryAdminRepository adminRepo;

	@Mock
	private CustomerRepository custRepo;

	@Mock
	private ArtistRepository artistRepo;

	@InjectMocks
	private GalleryRegistrationService service;


	@BeforeEach
	public void setMockOutput() {

		lenient().when(regRepo.count()).thenReturn((long) 0);
	}

	@Test
	public void testGetAllRegistratrionsIsEmpty() {

		List<GalleryRegistration> allReg = null;
		String error = null; 
		try {
			allReg = service.deleteAllGalleryRegistration();
		} catch (GalleryRegistrationException e) {
			error = e.getMessage();
		}
		assertNull(allReg);
		assertEquals(error, "no GalleryRegistrations in system"); 

	}
}
