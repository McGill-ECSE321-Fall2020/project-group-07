package ca.mcgill.ecse321.onlinegallery.serviceAll;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import ca.mcgill.ecse321.onlinegallery.dao.*;
import ca.mcgill.ecse321.onlinegallery.dto.*;
import ca.mcgill.ecse321.onlinegallery.model.*;
import ca.mcgill.ecse321.onlinegallery.service.GalleryRegistrationService;
import ca.mcgill.ecse321.onlinegallery.service.exception.*;

@ExtendWith(MockitoExtension.class)
public class TestGalleryRegistrationServiceDelete {

	@Mock
	private GalleryRegistrationRepository regRepo;

	@Mock
	private OnlineGalleryRepository ogRepo;

	@InjectMocks
	private GalleryRegistrationService service;

	private static final String VALID_USERNAME = "ValidUserName";
	private static final String INVALID_USERNAMENONEXIST = "nonexistentUser";

	private static final String VALID_FIRSTNAME = "John";
	private static final String VALID_LASTNAME = "smith";

	private static final String VALID_PASSWORD = "ecsE321!GRp7";
	private static final String VALID_EMAIL = "johnsmith@gmail.com";


	@BeforeEach
	public void setMockOutput() {

		Answer<?> paramAsAnswer = (InvocationOnMock invocation) -> {
			return invocation.getArgument(0);
		};

		lenient().when(regRepo.existsByUserName(anyString())).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(INVALID_USERNAMENONEXIST)) {
				return false;
			} else if (invocation.getArgument(0).equals(VALID_USERNAME)) {
				return true;
			}
			return false;
		});
 
		lenient().when(regRepo.findGalleryRegisrationByUserName(anyString()))
				.thenAnswer((InvocationOnMock invocation) -> {
					if (invocation.getArgument(0).equals(VALID_USERNAME)) {
						GalleryRegistration reg = new GalleryRegistration();
						reg.setUserName(VALID_USERNAME);
						reg.setFirstName(VALID_FIRSTNAME);
						reg.setLastName(VALID_LASTNAME);
						reg.setEmail(VALID_EMAIL);
						reg.setPassWord(VALID_PASSWORD);
						reg.setIsLoggedIn(false);

						return reg;
					}

					return null;
				});
		
		lenient().when(ogRepo.findAll()).thenAnswer((i)->{
			Set<OnlineGallery> ogSet = new HashSet<OnlineGallery>();
			
			OnlineGallery og = new OnlineGallery();
			GalleryRegistration reg = new GalleryRegistration();
			
			reg.setUserName(VALID_USERNAME);
			reg.setFirstName(VALID_FIRSTNAME);
			reg.setLastName(VALID_LASTNAME);
			reg.setEmail(VALID_EMAIL);
			reg.setPassWord(VALID_PASSWORD);
			reg.setIsLoggedIn(false);
			
			og.getAllRegistrations().add(reg);	
			ogSet.add(og);

			return ogSet;
		});

		lenient().doAnswer((i)->null).when(regRepo).delete(any(GalleryRegistration.class));
	}

	@Test
	public void deleteExistingUsername() {

		GalleryRegistration reg = null;
		try {
			reg = service.deleteGalleryRegistration(VALID_USERNAME);
		} catch (GalleryRegistrationException e) {
			fail();
		}
		assertNotNull(reg);
		assertEquals(reg.getUserName(),VALID_USERNAME);
	}
	
	@Test
	public void deleteNonExistingUsername() {

		GalleryRegistration reg = null;
		String error = null;
		try {
			reg = service.deleteGalleryRegistration(INVALID_USERNAMENONEXIST);
		} catch (GalleryRegistrationException e) {
			error=e.getMessage();
		}
		assertNull(reg);
		assertEquals(error,"No GalleryRegistration with username ["+INVALID_USERNAMENONEXIST+"] exists");
	}



}
