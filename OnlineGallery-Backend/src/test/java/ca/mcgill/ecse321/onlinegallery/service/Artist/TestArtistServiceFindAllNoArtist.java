package ca.mcgill.ecse321.onlinegallery.service.Artist;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.assertNull;
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
import ca.mcgill.ecse321.onlinegallery.model.Artwork;
import ca.mcgill.ecse321.onlinegallery.model.ArtworkStatus;
import ca.mcgill.ecse321.onlinegallery.model.GalleryRegistration;
import ca.mcgill.ecse321.onlinegallery.service.ArtistService;
import ca.mcgill.ecse321.onlinegallery.service.exception.ArtistException;

@ExtendWith(MockitoExtension.class)
public class TestArtistServiceFindAllNoArtist {
	
	@Mock
	private ArtistRepository artistRepo;
	
	@InjectMocks
	private ArtistService service;
	
	private static final Long ARTWORKTID = (long) 1;

	private static final String VALID_USERNAME = "ValidUserName";
	@BeforeEach
	public void setMockOutput() {		
		lenient().when(artistRepo.findAll()).thenAnswer((InvocationOnMock invocation) -> {
			
			List<Artwork> list = new ArrayList<Artwork>();
			GalleryRegistration reg = new GalleryRegistration();
			reg.setUserName(VALID_USERNAME);
			Artwork a = new Artwork();
			Artist artist = new Artist();
			a.setArtist(artist);
			a.setArtworkId(ARTWORKTID);
			artist.getArtwork().add(a);
			a.setStatus(ArtworkStatus.UNAVAILABLE);
			reg.setArtist(artist);
			artist.setGalleryRegistration(reg);
			list.add(a);
			
			return list;
		});
	}
	@Test
	public void testInvalidFindAll() {
		
		List<Artist> a = null;
		String error = null;
		try {
			a = service.findAllArtist();
		}catch(ArtistException e) {
			error = e.getMessage();
		}
		assertNull(a);
		assertEquals(error, "No artists exists.");
	}
}