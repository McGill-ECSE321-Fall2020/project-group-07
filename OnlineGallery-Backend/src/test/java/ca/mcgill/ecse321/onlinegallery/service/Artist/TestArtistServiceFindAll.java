package ca.mcgill.ecse321.onlinegallery.service.Artist;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.lenient;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import ca.mcgill.ecse321.onlinegallery.dao.ArtistRepository;
import ca.mcgill.ecse321.onlinegallery.model.Artist;
import ca.mcgill.ecse321.onlinegallery.model.GalleryRegistration;
import ca.mcgill.ecse321.onlinegallery.service.ArtistService;
import ca.mcgill.ecse321.onlinegallery.service.exception.ArtistException;

@ExtendWith(MockitoExtension.class)
public class TestArtistServiceFindAll {
	
	@Mock
	private ArtistRepository artistRepo;
	
	@InjectMocks
	private ArtistService service;
	
	private static final Long ARTISTID = (long) 1;
	private static final String BANKINFO = "moonnneyyyyyy";
	private static final String VALID_USERNAME = "ValidUserName";
	@BeforeEach
	public void setMockOutput() {		
		lenient().when(artistRepo.findAll()).thenAnswer((InvocationOnMock invocation) -> {
			
			List<Artist> list = new ArrayList<Artist>();
			GalleryRegistration reg = new GalleryRegistration();
			reg.setUserName(VALID_USERNAME);
			Artist a = new Artist();
			a.setArtistId(ARTISTID);
			a.setBankInfo(BANKINFO);
			reg.setArtist(a);
			a.setGalleryRegistration(reg);
			list.add(a);
			
			return null;
		});
	}
	@Test
	public void testValidFindAll() {
		
		List<Artist> a = null;
		try {
			a = service.findAllArtist();
		}catch(ArtistException e) {
			fail();
		}
		assertNotNull(a);
	}
}