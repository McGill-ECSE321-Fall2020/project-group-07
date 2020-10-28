package ca.mcgill.ecse321.onlinegallery.service.Artist;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.ArgumentMatchers.anyLong;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import ca.mcgill.ecse321.onlinegallery.dao.ArtistRepository;
import ca.mcgill.ecse321.onlinegallery.model.Artist;
import ca.mcgill.ecse321.onlinegallery.model.GalleryRegistration;
import ca.mcgill.ecse321.onlinegallery.service.ArtistService;
import ca.mcgill.ecse321.onlinegallery.service.exception.ArtistException;

@ExtendWith(MockitoExtension.class)
public class TestArtistServiceFindById {
	
	@Mock
	private ArtistRepository artistRepo;
	
	@InjectMocks
	private ArtistService service;
	
	private static final Long VALIDARTISTID = (long) 1;
	private static final Long INVALIDARTISTID = (long) 2;
	private static final String BANKINFO = "moonnneyyyyyy";
	private static final String VALID_USERNAME = "ValidUserName";
	
	@BeforeEach
	public void setMockOutput() {
		Answer<?> paramAsAnswer = (InvocationOnMock invocation)->{
			return invocation.getArgument(0);
		};
		lenient().when(artistRepo.existsById(anyLong())).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(VALIDARTISTID)) {
				
				GalleryRegistration reg = new GalleryRegistration();
				reg.setUserName(VALID_USERNAME);
				Artist a = new Artist();
				a.setArtistId(VALIDARTISTID);
				a.setBankInfo(BANKINFO);
				reg.setArtist(a);
				a.setGalleryRegistration(reg);
			}
			if (invocation.getArgument(0).equals(INVALIDARTISTID)) {
				Artist a = new Artist();
				return a;
			}
			return true;
		});

		lenient().doAnswer((i)->null).when(artistRepo).delete(any(Artist.class));
	}
	@Test
	public void testValidArtistFindById() {
		
		Artist a = null;
		try {
			a = service.findArtistById(VALIDARTISTID);
		}catch(ArtistException e) {
			fail();
		}
		assertNotNull(a);
		assertEquals(a.getArtistId(), VALIDARTISTID);
	}
	@Test
	public void testInvalidArtistFindById() {
		
		Artist a = null;
		String error = null;
		try {
			a = service.findArtistById(INVALIDARTISTID);
		}catch(ArtistException e) {
			error = e.getMessage();
		}
		assertNull(a);
		assertEquals(error, "No artist exists under the username ["+INVALIDARTISTID+"]");
	}
}