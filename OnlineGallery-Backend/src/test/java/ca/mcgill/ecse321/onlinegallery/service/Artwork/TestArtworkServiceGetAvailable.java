package ca.mcgill.ecse321.onlinegallery.service.Artwork;

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
import org.mockito.stubbing.Answer;

import ca.mcgill.ecse321.onlinegallery.dao.ArtistRepository;
import ca.mcgill.ecse321.onlinegallery.dao.ArtworkRepository;
import ca.mcgill.ecse321.onlinegallery.model.Artist;
import ca.mcgill.ecse321.onlinegallery.model.Artwork;
import ca.mcgill.ecse321.onlinegallery.model.ArtworkStatus;
import ca.mcgill.ecse321.onlinegallery.model.GalleryRegistration;
import ca.mcgill.ecse321.onlinegallery.service.ArtworkService;
import ca.mcgill.ecse321.onlinegallery.service.exception.ArtworkException;

@ExtendWith(MockitoExtension.class)
public class TestArtworkServiceGetAvailable {

	@Mock
	private ArtworkRepository artworkRepo;
	
	@InjectMocks
	private ArtworkService service;
	
	private static final Long ARTWORKTID = (long) 1;

	private static final String VALID_USERNAME = "ValidUserName";

	@BeforeEach
	public void setMockOutput() {
		Answer<?> paramAsAnswer = (InvocationOnMock invocation)->{
			return invocation.getArgument(0);
		};
		
		lenient().when(artworkRepo.findAll()).thenAnswer((InvocationOnMock invocation)->{
			
			List<Artwork> list = new ArrayList<Artwork>();
			GalleryRegistration reg = new GalleryRegistration();
			reg.setUserName(VALID_USERNAME);
			Artwork a = new Artwork();
			Artist artist = new Artist();
			a.setArtist(artist);
			a.setArtworkId(ARTWORKTID);
			artist.getArtwork().add(a);
			a.setStatus(ArtworkStatus.AVAILABLE);
			reg.setArtist(artist);
			artist.setGalleryRegistration(reg);
			list.add(a);
			
			return list;
		});
	}
	@Test
	public void testValidFindAll() {
		
		List<Artwork> a = null;
		try {
			a = service.getAllAvailableArtwork();
		}catch(ArtworkException e) {
			fail();
		}
		assertNotNull(a);
	}
}