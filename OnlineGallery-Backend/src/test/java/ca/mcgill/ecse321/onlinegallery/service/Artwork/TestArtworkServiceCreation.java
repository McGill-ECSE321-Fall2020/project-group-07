package ca.mcgill.ecse321.onlinegallery.service.Artwork;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
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
import ca.mcgill.ecse321.onlinegallery.dao.ArtworkRepository;
import ca.mcgill.ecse321.onlinegallery.dao.GalleryRegistrationRepository;
import ca.mcgill.ecse321.onlinegallery.dto.ArtworkDto;
import ca.mcgill.ecse321.onlinegallery.model.Artist;
import ca.mcgill.ecse321.onlinegallery.model.Artwork;
import ca.mcgill.ecse321.onlinegallery.model.ArtworkStatus;
import ca.mcgill.ecse321.onlinegallery.model.Customer;
import ca.mcgill.ecse321.onlinegallery.model.GalleryRegistration;
import ca.mcgill.ecse321.onlinegallery.model.Purchase;
import ca.mcgill.ecse321.onlinegallery.service.ArtworkService;
import ca.mcgill.ecse321.onlinegallery.service.exception.ArtistException;
import ca.mcgill.ecse321.onlinegallery.service.exception.ArtworkException;

@ExtendWith(MockitoExtension.class)
public class TestArtworkServiceCreation {

	@Mock
	private GalleryRegistrationRepository regRepo;
	
	@Mock
	private ArtistRepository artistRepo;
	
	@Mock
	private ArtworkRepository artworkRepo;
	
	@InjectMocks
	private ArtworkService service;

	private static final String VALID_USERNAME="validUsername";
	private static final String INVALID_USERNAMENONEXIST="nonexistUsername";
	private static final String INVALID_USERNAMENOTANARTIST="userNotArtist";

	@SuppressWarnings("deprecation")
	private static final Long VALID_ARTISTID=new Long(1);
	@SuppressWarnings("deprecation")
	private static final Long INVALID_ARTWORKIDNONEXIST=new Long(2);
	@SuppressWarnings("deprecation")
	private static final Long INVALID_ARTWORKIDNOTAVAILABLE=new Long (3);
	
	private static final Long ARTWORK_ID=new Long(4);
	@BeforeEach
	public void setMockOutput() {
		Answer<?> paramAsAnswer = (InvocationOnMock invocation)->{
			return invocation.getArgument(0);
		};
		
		lenient().when(regRepo.existsByUserName(anyString())).thenAnswer((InvocationOnMock invocation)->{
			if (invocation.getArgument(0).equals(VALID_USERNAME)) {
				return true;
			}
			if (invocation.getArgument(0).equals(INVALID_USERNAMENOTANARTIST)) {
				return true;
			}
			else {
				return false;
			}
		});
		lenient().when(artistRepo.findArtistByArtistId(anyLong())).thenAnswer((InvocationOnMock invocation)->{
			if (invocation.getArgument(0).equals(VALID_ARTISTID)) {
				Artist a = new Artist();
				a.setArtistId(VALID_ARTISTID);
				return a;
			}
			else {
				return null;
			}
		});
		lenient().when(artworkRepo.save(any(Artwork.class))).thenAnswer((InvocationOnMock invocation)->{
			GalleryRegistration reg = new GalleryRegistration();			
			Artist a = new Artist();
			Artwork art = new Artwork();
			a.setArtistId(VALID_ARTISTID);
			
			reg.setUserName(VALID_USERNAME);
			art.setArtworkId(ARTWORK_ID);
			art.setStatus(ArtworkStatus.UNAVAILABLE);
			
			a.setGalleryRegistration(reg);
			reg.setArtist(a);
			
			art.setArtist(a);
			a.getArtwork().add(art);

			return art;
		});
	}
	@Test
	public void testValidArtworkCreation() {
		
		ArtworkDto dto = new ArtworkDto();
		Artwork a = null;
		try {
			a = service.createArtwork(VALID_ARTISTID, dto);
		}catch(ArtworkException e) {
			e.getMessage();
		}catch(ArtistException e) {
			e.getMessage();
		}
		assertNotNull(a);
		assertEquals(a.getArtist().getGalleryRegistration().getUserName(),VALID_USERNAME);
		assertEquals(a.getArtist().getArtistId(),VALID_ARTISTID);
	}
}
