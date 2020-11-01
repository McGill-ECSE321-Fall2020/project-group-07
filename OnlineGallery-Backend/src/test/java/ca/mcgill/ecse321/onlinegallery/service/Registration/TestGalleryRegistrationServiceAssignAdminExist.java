package ca.mcgill.ecse321.onlinegallery.service.Registration;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import ca.mcgill.ecse321.onlinegallery.dao.*;
import ca.mcgill.ecse321.onlinegallery.model.*;
import ca.mcgill.ecse321.onlinegallery.service.GalleryRegistrationService;
import ca.mcgill.ecse321.onlinegallery.service.exception.*;

@ExtendWith(MockitoExtension.class)
public class TestGalleryRegistrationServiceAssignAdminExist {

	@Mock
	private GalleryRegistrationRepository regRepo;

	@Mock
	private OnlineGalleryRepository ogRepo;
	
	@Mock
	private GalleryAdminRepository adminRepo;

	@InjectMocks
	private GalleryRegistrationService service;

	private static final String VALID_USERNAME = "ValidUserName";
	private static final String INVALID_USERNAMENONEXIST = "nonexistentUser";
	

	@BeforeEach
	public void setMockOutput() {
		
		Long adminCount= (long) 1;
		
		Answer<?> paramAsAnswer = (InvocationOnMock invocation) -> {
			return invocation.getArgument(0);
		};

		lenient().when(regRepo.existsByUserName(anyString())).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(INVALID_USERNAMENONEXIST)) {
				return false;
			} else {
				return true;
			}
		});
		
		lenient().when(regRepo.findGalleryRegisrationByUserName(anyString()))
				.thenAnswer((InvocationOnMock invocation) -> {
					if (invocation.getArgument(0).equals(VALID_USERNAME)) {
						GalleryRegistration reg = new GalleryRegistration();
						reg.setUserName(VALID_USERNAME);
						reg.setFirstName("john");
						reg.setLastName("smith");
						reg.setEmail("jhonsmo@gmail.com");
						reg.setPassWord("Password0!");
						reg.setIsLoggedIn(false);

						return reg;
					}
					else {
						return null;
					}
				});
		
		lenient().when(adminRepo.count()).thenReturn(adminCount);
		lenient().when(regRepo.save(any(GalleryRegistration.class))).thenAnswer(paramAsAnswer);

	}
	
	@Test
	public void testAssignAdminSystemAlreadyHasAdmin() {
		GalleryRegistration reg=null;
		String error =null;
		try{
			reg=service.setAdmin(VALID_USERNAME);
		}
		catch(GalleryRegistrationException e) {
			error=e.getMessage();
		}
		
		assertNull(reg);
		assertEquals(error,"there is already an Admin in database");
	}

}
