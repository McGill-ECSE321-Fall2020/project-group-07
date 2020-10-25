package ca.mcgill.ecse321.onlinegallery.service.Registration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import static org.junit.jupiter.api.Assertions.fail;
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
import ca.mcgill.ecse321.onlinegallery.dto.*;
import ca.mcgill.ecse321.onlinegallery.model.*;
import ca.mcgill.ecse321.onlinegallery.service.GalleryRegistrationService;
import ca.mcgill.ecse321.onlinegallery.service.exception.*;

@ExtendWith(MockitoExtension.class)
public class TestGalleryRegistrationServiceUpdate {

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
	private static final String NEW_PASSWORD = "Sprint2Pwd!";
	private static final String INVALID_PASSWORD = "noNumInPwd";

	private static final String VALID_EMAIL = "johnsmith@gmail.com";
	private static final String NEW_EMAIL = "johnnyboy@gmail.com";
	private static final String INVALID_EMAIL = "johnsmith@";

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

					return false;
				});

		lenient().when(regRepo.save(any(GalleryRegistration.class))).thenAnswer(paramAsAnswer);

	}

	@Test
	public void updateEmailValidUserValidEmail() {
		UpdateEmailDto dto = new UpdateEmailDto();
		dto.setUsername(VALID_USERNAME);
		dto.setEmail(NEW_EMAIL);

		GalleryRegistration reg = null;
		try {
			reg = service.updateEmail(dto);
		} catch (GalleryRegistrationException e) {
			fail();
		}
		assertNotNull(reg);
		assertEquals(reg.getEmail(), NEW_EMAIL);
	}

	@Test
	public void updateEmailValidUserEmptyEmail() {
		UpdateEmailDto dto = new UpdateEmailDto();
		dto.setUsername(VALID_USERNAME);
		dto.setEmail("");

		GalleryRegistration reg = null;
		String error = null;
		try {
			reg = service.updateEmail(dto);
		} catch (GalleryRegistrationException e) {
			error = e.getMessage();
		}
		assertNull(reg);
		assertEquals(error, "email address cannot be empty");
	}

	@Test
	public void updateEmailValidUserInvalidEmail() {
		UpdateEmailDto dto = new UpdateEmailDto();
		dto.setUsername(VALID_USERNAME);
		dto.setEmail(INVALID_EMAIL);

		GalleryRegistration reg = null;
		String error = null;
		try {
			reg = service.updateEmail(dto);
		} catch (GalleryRegistrationException e) {
			error = e.getMessage();
		}
		assertNull(reg);
		assertEquals(error, "invalid email address");
	}

	@Test
	public void updateEmailNonExistUserValidEmail() {
		UpdateEmailDto dto = new UpdateEmailDto();
		dto.setUsername(INVALID_USERNAMENONEXIST);
		dto.setEmail(NEW_EMAIL);

		GalleryRegistration reg = null;
		String error = null;
		try {
			reg = service.updateEmail(dto);
		} catch (GalleryRegistrationException e) {
			error = e.getMessage();
		}
		assertNull(reg);
		assertEquals(error, "No GalleryRegistration with username [" + INVALID_USERNAMENONEXIST + "] found");
	}

	@Test
	public void updateEmailNonExistValidUserEmptyEmail() {
		UpdateEmailDto dto = new UpdateEmailDto();
		dto.setUsername(INVALID_USERNAMENONEXIST);
		dto.setEmail("");

		GalleryRegistration reg = null;
		String error = null;
		try {
			reg = service.updateEmail(dto);
		} catch (GalleryRegistrationException e) {
			error = e.getMessage();
		}
		assertNull(reg);
		assertEquals(error, "No GalleryRegistration with username [" + INVALID_USERNAMENONEXIST + "] found");
	}

	@Test
	public void updateEmailNonExistdUserInvalidEmail() {
		UpdateEmailDto dto = new UpdateEmailDto();
		dto.setUsername(INVALID_USERNAMENONEXIST);
		dto.setEmail(INVALID_EMAIL);

		GalleryRegistration reg = null;
		String error = null;
		try {
			reg = service.updateEmail(dto);
		} catch (GalleryRegistrationException e) {
			error = e.getMessage();
		}
		assertNull(reg);
		assertEquals(error, "No GalleryRegistration with username [" + INVALID_USERNAMENONEXIST + "] found");
	}

	@Test
	public void updatePasswordValidUserValidPassword() {
		UpdatePasswordDto dto = new UpdatePasswordDto();
		dto.setUsername(VALID_USERNAME);
		dto.setpassWord(NEW_PASSWORD);

		GalleryRegistration reg = null;
		try {
			reg = service.updatePassowrd(dto);
		} catch (GalleryRegistrationException e) {
			fail();
		} 
		assertNotNull(reg);
		assertEquals(reg.getPassWord(), NEW_PASSWORD);
	}

	@Test
	public void updatePasswordValidUserEmptyPassword() {
		UpdatePasswordDto dto = new UpdatePasswordDto();
		dto.setUsername(VALID_USERNAME);
		dto.setpassWord("");

		GalleryRegistration reg = null;
		String error = null;
		try {
			reg = service.updatePassowrd(dto);
		} catch (GalleryRegistrationException e) {
			error = e.getMessage();
		}
		assertNull(reg);
		assertEquals(error, "password cannot be empty");
	}

	@Test
	public void updatePasswordValidUserInvalidPassword() {
		UpdatePasswordDto dto = new UpdatePasswordDto();
		dto.setUsername(VALID_USERNAME);
		dto.setpassWord(INVALID_PASSWORD);

		GalleryRegistration reg = null;
		String error = null;
		try {
			reg = service.updatePassowrd(dto);
		} catch (GalleryRegistrationException e) {
			error = e.getMessage();
		}
		assertNull(reg);
		assertEquals(error, "password has to contain at least one number");
	}
	

	@Test
	public void updatePasswordInvalidNonExistUserValidPassword() {
		UpdatePasswordDto dto = new UpdatePasswordDto();
		dto.setUsername(INVALID_USERNAMENONEXIST);
		dto.setpassWord(NEW_PASSWORD);

		GalleryRegistration reg = null;
		String error=null;
		try {
			reg = service.updatePassowrd(dto);
		} catch (GalleryRegistrationException e) {
			error=e.getMessage();
		} 
		assertNull(reg);
		assertEquals(error,"No GalleryRegistration with username [" + INVALID_USERNAMENONEXIST + "] found");
	}


}
