package ca.mcgill.ecse321.onlinegallery.service.Registration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
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

import ca.mcgill.ecse321.onlinegallery.dao.GalleryRegistrationRepository;
import ca.mcgill.ecse321.onlinegallery.dto.LogginCredentialsDto;
import ca.mcgill.ecse321.onlinegallery.model.Artist;
import ca.mcgill.ecse321.onlinegallery.model.Customer;
import ca.mcgill.ecse321.onlinegallery.model.GalleryAdmin;
import ca.mcgill.ecse321.onlinegallery.model.GalleryRegistration;
import ca.mcgill.ecse321.onlinegallery.service.GalleryRegistrationService;

@ExtendWith(MockitoExtension.class)
public class TestGalleryRegistrationLogin {

	@Mock
	private GalleryRegistrationRepository regRepo;
	
	@InjectMocks
	private GalleryRegistrationService service;
	
	private static final String VALID_EMAIL = "correct@hotmail.com";
	private static final String VALID_PASSWORD = "Heyyyy1234!!!";
	private static final String INVALID_EMAIL = "incorrect@hotmail.com";
	private static final String INVALID_PASSWORD = "Byeeee@1234!!!";
	
	@BeforeEach
	public void setMockOutput() {
		
		Answer<?> paramAsAnswer = (InvocationOnMock invocation) -> {
			return invocation.getArgument(0);
		};
		
		lenient().when(regRepo.existsByEmailAndPassword(VALID_EMAIL, VALID_PASSWORD)).then((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(VALID_EMAIL) && invocation.getArgument(1).equals(VALID_PASSWORD)) {
				return true;
			} else {
				return false;
			}
		});
		
		lenient().when(regRepo.findGalleryRegistrationByEmailAndPassword(VALID_EMAIL, VALID_PASSWORD)).then((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(VALID_EMAIL) && invocation.getArgument(1).equals(VALID_PASSWORD)) {
				GalleryRegistration reg = new GalleryRegistration();
				reg.setEmail(VALID_EMAIL);
				reg.setPassWord(VALID_PASSWORD);
				
				return reg;
			} else {
				return null;
			}
		});
		
		lenient().when(regRepo.save(any(GalleryRegistration.class))).thenAnswer(paramAsAnswer);

	}
	
	@Test
	public void testLoginValidUser() {
	
		GalleryRegistration reg = null;
		LogginCredentialsDto dto = new LogginCredentialsDto();
		dto.setPassword(VALID_PASSWORD);
		dto.setEmail(VALID_EMAIL);
		
		try {
			reg = service.login(dto);
		} catch (Exception e) {
			fail();
		}
		
		assertNotNull(reg);
		assertEquals(reg.getIsLoggedIn(), true);
		
	}
	
	@Test
	public void testLoginInvalidUser() {
		
		GalleryRegistration reg = null;
		LogginCredentialsDto dto = new LogginCredentialsDto();
		dto.setPassword(INVALID_PASSWORD);
		dto.setEmail(VALID_EMAIL);
		
		String error = null;
		
		try {
			reg = service.login(dto);
		} catch (Exception e) {
			error = e.getMessage();
		}
		
		assertNull(reg);
		assertEquals(error, "No GalleryRegistration with email [" + VALID_EMAIL + "] and password [" + INVALID_PASSWORD + "] found");
		
	}
}
