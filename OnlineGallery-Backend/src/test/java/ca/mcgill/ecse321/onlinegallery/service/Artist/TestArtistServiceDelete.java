package ca.mcgill.ecse321.onlinegallery.service.Artist;

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

import ca.mcgill.ecse321.onlinegallery.dao.ArtistRepository;
import ca.mcgill.ecse321.onlinegallery.dao.GalleryRegistrationRepository;
import ca.mcgill.ecse321.onlinegallery.model.Artist;
import ca.mcgill.ecse321.onlinegallery.model.GalleryRegistration;
import ca.mcgill.ecse321.onlinegallery.service.ArtistService;
import ca.mcgill.ecse321.onlinegallery.service.exception.ArtistException;

@ExtendWith(MockitoExtension.class)
public class TestArtistServiceDelete {

	@Mock
	private GalleryRegistrationRepository regRepo;
	
	@Mock
	private ArtistRepository artistRepo;
	
	@InjectMocks
	private ArtistService service;
	
	private static final Long ARTISTID = (long) 1;
		
	private static final String VALID_USERNAME = "ValidUserName";
	private static final String INVALID_USERNAMENONEXIST = "nonexistentUser";
	private static final String INVALID_USERNAMEEXISTSNONARTIST = "nonArtistUser";

	@BeforeEach
	public void setMockOutput() {
		Answer<?> paramAsAnswer = (InvocationOnMock invocation)->{
			return invocation.getArgument(0);
		};
		lenient().when(regRepo.existsByUserName(anyString())).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(INVALID_USERNAMENONEXIST)) {
				return false;
			}
			return true;
		});
		lenient().when(regRepo.findGalleryRegisrationByUserName(anyString())).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(VALID_USERNAME)) {
				GalleryRegistration reg = new GalleryRegistration();
				reg.setUserName(VALID_USERNAME);
				Artist artist = new Artist();
				artist.setArtistId(ARTISTID);
				reg.setArtist(artist);
				artist.setGalleryRegistration(reg);
				return reg;
			}
			if (invocation.getArgument(0).equals(INVALID_USERNAMEEXISTSNONARTIST)) {
				GalleryRegistration reg = new GalleryRegistration();
				reg.setUserName(INVALID_USERNAMEEXISTSNONARTIST);
				return reg;
			}
			return null;
		});

		lenient().doAnswer((i)->null).when(artistRepo).delete(any(Artist.class));
	}
	@Test
	public void testValidArtistDelete() {
		
		Artist a = null;
		try {
			a = service.deleteArtistByUsername(VALID_USERNAME);
		}catch(ArtistException e) {
			fail();
		}
		assertNotNull(a);
		assertEquals(a.getArtistId(), ARTISTID);
	}
	@Test
	public void testInvalidUsernameNoArtist() {
		
		Artist a = null;
		String error = null;
		try {
			a = service.deleteArtistByUsername(INVALID_USERNAMEEXISTSNONARTIST);
		}catch(ArtistException e) {
			error = e.getMessage();
		}
		assertNull(a);
		assertEquals(error, "No artist exists under the id ["+INVALID_USERNAMEEXISTSNONARTIST+"]");
	}
	@Test
	public void testInvalidNonExistentUser() {
		
		Artist a = null;
		String error = null;
		try {
			a = service.deleteArtistByUsername(INVALID_USERNAMENONEXIST);
		}catch(ArtistException e) {
			error = e.getMessage();
		}
		assertNull(a);
		assertEquals(error, "No registration exists under the username ["+INVALID_USERNAMENONEXIST+"]");
	}
	
}
