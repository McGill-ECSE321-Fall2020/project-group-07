package ca.mcgill.ecse321.onlinegallery.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.anyLong;
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
import ca.mcgill.ecse321.onlinegallery.dto.*;
import ca.mcgill.ecse321.onlinegallery.model.*;
import ca.mcgill.ecse321.onlinegallery.service.exception.*;

@ExtendWith(MockitoExtension.class)
public class TestGalleryRegistrationServiceAssign {

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
	private static final String INVALID_USERNAMEALREADYHASCUSTOMER = "alreadyHasCustomer";
	private static final String INVALID_USERNAMEALREADYHASARTIST = "alreadyHasArtist";
	private static final String INVALID_USERNAMEALREADYHASADMIN = "alreadyHasAdmin";
	

	@BeforeEach
	public void setMockOutput() {
		
		Long adminCount1= (long) 0;
		Long adminCount2= (long) 0;
		Long adminCount3= (long) 0;
		Long adminCount4= (long) 1;
		
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
		
		lenient().when(adminRepo.count()).thenReturn(adminCount1, adminCount4);

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
					
					if (invocation.getArgument(0).equals(INVALID_USERNAMEALREADYHASCUSTOMER)) {
						GalleryRegistration reg = new GalleryRegistration();
						reg.setUserName(VALID_USERNAME);
						reg.setFirstName("john");
						reg.setLastName("smith");
						reg.setEmail("jhonsmo@gmail.com");
						reg.setPassWord("Password0!");
						reg.setIsLoggedIn(false);
						reg.setCustomer(new Customer());

						return reg;
					}
					
					if (invocation.getArgument(0).equals(INVALID_USERNAMEALREADYHASARTIST)) {
						GalleryRegistration reg = new GalleryRegistration();
						reg.setUserName(VALID_USERNAME);
						reg.setFirstName("john");
						reg.setLastName("smith");
						reg.setEmail("jhonsmo@gmail.com");
						reg.setPassWord("Password0!");
						reg.setIsLoggedIn(false);
						reg.setArtist(new Artist());

						return reg;
					}
					
					if (invocation.getArgument(0).equals(INVALID_USERNAMEALREADYHASADMIN)) {
						GalleryRegistration reg = new GalleryRegistration();
						reg.setUserName(VALID_USERNAME);
						reg.setFirstName("john");
						reg.setLastName("smith");
						reg.setEmail("jhonsmo@gmail.com");
						reg.setPassWord("Password0!");
						reg.setIsLoggedIn(false);
						reg.setAdmin(new GalleryAdmin());

						return reg;
					}

					return false;
				});

		lenient().when(regRepo.save(any(GalleryRegistration.class))).thenAnswer(paramAsAnswer);

	}
//	
//	6. assignAdmin(username)
//	- exception: username not found
//	- exception: Registrartion already assigned Admin
//	- exception: an Admin already exists in sys

	@Test
	public void testAssignAdminValidUser() {
		GalleryRegistration reg=null;
		try{
			reg=service.setAdmin(VALID_USERNAME);
		}
		catch(GalleryRegistrationException e) {
			fail();
		}
		
		assertNotNull(reg);
		assertNotNull(reg.getAdmin());
	}
	
	@Test
	public void testAssignAdminInvalidUserNoExist() {
		GalleryRegistration reg=null;
		String error =null;
		try{
			reg=service.setAdmin(INVALID_USERNAMENONEXIST);
		}
		catch(GalleryRegistrationException e) {
			error=e.getMessage();
		}
		
		assertNull(reg);
		assertEquals(error,"No GalleryRegistration with username [" + INVALID_USERNAMENONEXIST + "] found");
	}
	
	@Test
	public void testAssignAdminInvalidUserAlreadyAdmin() {
		GalleryRegistration reg=null;
		String error =null;
		try{
			reg=service.setAdmin(INVALID_USERNAMEALREADYHASADMIN);
		}
		catch(GalleryRegistrationException e) {
			error=e.getMessage();
		}
		
		assertNull(reg);
		assertEquals(error,"GalleryRegistration with username [" + INVALID_USERNAMEALREADYHASADMIN + "] already associated with a Admin");
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
