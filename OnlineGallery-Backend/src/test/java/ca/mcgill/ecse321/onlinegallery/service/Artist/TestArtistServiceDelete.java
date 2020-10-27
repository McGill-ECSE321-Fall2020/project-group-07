package ca.mcgill.ecse321.onlinegallery.service.Artist;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
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
import ca.mcgill.ecse321.onlinegallery.dto.ArtistDto;
import ca.mcgill.ecse321.onlinegallery.model.Artist;
import ca.mcgill.ecse321.onlinegallery.model.Artwork;
import ca.mcgill.ecse321.onlinegallery.model.ArtworkStatus;
import ca.mcgill.ecse321.onlinegallery.model.Customer;
import ca.mcgill.ecse321.onlinegallery.model.GalleryRegistration;
import ca.mcgill.ecse321.onlinegallery.model.Purchase;
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
	
	@SuppressWarnings("deprecation")
	private static final Long VALID_ARTISTID= new Long(1);
	@SuppressWarnings("deprecation")
	private static final Long INVALID_ARTISTID= new Long(2);
	
	private static final String VALID_USERNAME = "ValidUserName";
	private static final String INVALID_USERNAMENONEXIST = "nonexistentUser";
	
	private static final String VALID_BANKINFO = "moneymoneymoney is so funny";
	private static final String INVALID_BANKINFO = "moneymoneymoney is NOT so funny";

	@BeforeEach
	public void setMockOutput() {
		Answer<?> paramAsAnswer = (InvocationOnMock invocation)->{
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
		
		lenient().when(artistRepo.existsByArtistId(anyLong())).thenAnswer((InvocationOnMock invocation)->{
			if (invocation.getArgument(0).equals(VALID_ARTISTID)) {
				return true;
			}
			if (invocation.getArgument(0).equals(INVALID_ARTISTID)) {
				return false;
			}
			else {
				return false;
			}
		});
		lenient().when(artistRepo.save(any(Artist.class))).thenAnswer((InvocationOnMock invocation)->{
			GalleryRegistration reg = new GalleryRegistration();			
			Artist a = new Artist();

			reg.setUserName(VALID_USERNAME);
			a.setArtistId(VALID_ARTISTID);
			
			a.setGalleryRegistration(reg);
			reg.setArtist(a);
			
			a.setBankInfo(((Artist)invocation.getArgument(0)).getBankInfo());

			return a;
		});
		lenient().when(artistRepo.findArtistByArtistId(anyLong())).thenAnswer((InvocationOnMock invocation)->{
			if (invocation.getArgument(0).equals(VALID_ARTISTID)) {
				Artist artist = new Artist();
				artist.setArtistId(VALID_ARTISTID);
				artist.setBankInfo(VALID_BANKINFO);
				return artist;
			}
			if (invocation.getArgument(0).equals(INVALID_ARTISTID)) {
				Artist artist = new Artist();
				artist.setArtistId(INVALID_ARTISTID);
				artist.setBankInfo(INVALID_BANKINFO);
				return artist;
			}
			else {
				return null;
			}
		});
		lenient().doAnswer((i)->null).when(artistRepo).delete(any(Artist.class));
	}
	@Test
	public void testValidArtistDelete() {
		ArtistDto dto = new ArtistDto();
		dto.setUsername(VALID_USERNAME);
		dto.setBankInfo(VALID_BANKINFO);
		
		Artist a = null;
		try {
			a = service.deleteArtistByUserName(dto);
		}catch(ArtistException e) {
			fail();
		}
		assertNotNull(a);
		assertEquals(a.getBankInfo(),VALID_BANKINFO);
	}
}
