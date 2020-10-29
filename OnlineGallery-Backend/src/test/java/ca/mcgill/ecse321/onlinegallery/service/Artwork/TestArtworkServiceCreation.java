package ca.mcgill.ecse321.onlinegallery.service.Artwork;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
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
import ca.mcgill.ecse321.onlinegallery.dao.ArtworkRepository;
import ca.mcgill.ecse321.onlinegallery.dao.GalleryRegistrationRepository;
import ca.mcgill.ecse321.onlinegallery.dto.ArtworkDto;
import ca.mcgill.ecse321.onlinegallery.model.Artist;
import ca.mcgill.ecse321.onlinegallery.model.Artwork;
import ca.mcgill.ecse321.onlinegallery.model.ArtworkStatus;
import ca.mcgill.ecse321.onlinegallery.model.GalleryRegistration;
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

	@SuppressWarnings("deprecation")
	private static final Long VALID_ARTISTID=new Long(1);
	@SuppressWarnings("deprecation")
	private static final Long INVALID_ARTISTID=new Long(2);
	
	private static final Long ARTWORK_ID=new Long(4);
	private static final String NAME = "pretty";
	private static final String DESCRIPTION = "so nice";
	private static final double PRICE = 10101010;
	private static final ArtworkStatus STATUS = ArtworkStatus.AVAILABLE;
	private static final int VALIDVIEWS = 0;
	private static final int INVALIDVIEWS = 100;
	private static final String DIMENSION = "very big";
	private static final double COMMISSION = 10;
	private static final double WEIGHT = 11;

	@BeforeEach
	public void setMockOutput() {
		Answer<?> paramAsAnswer = (InvocationOnMock invocation)->{
			return invocation.getArgument(0);
		};
		
		lenient().when(artistRepo.existsById(anyLong())).thenAnswer((InvocationOnMock invocation)->{
			if (invocation.getArgument(0).equals(VALID_ARTISTID)) {
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
			art.setStatus(ArtworkStatus.AVAILABLE);
			
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
		dto.setCommission(COMMISSION);
		dto.setDescription(DESCRIPTION);
		dto.setDimension(DIMENSION);
		dto.setName(NAME);
		dto.setNumViews(VALIDVIEWS);
		dto.setPrice(PRICE);
		dto.setStatus(STATUS);
		dto.setWeight(WEIGHT);
		Artwork a = null;
		try {
			a = service.createArtwork(VALID_ARTISTID, dto);
		}catch(ArtworkException e) {
			e.getMessage();
		}catch(ArtistException e) {
			e.getMessage();
		}
		assertNotNull(a);
		assertEquals(a.getArtist().getArtistId(),VALID_ARTISTID);
	}
	@Test
	public void testInvalidArtistIdArtworkCreation() {
		
		ArtworkDto dto = new ArtworkDto();
		dto.setCommission(COMMISSION);
		dto.setDescription(DESCRIPTION);
		dto.setDimension(DIMENSION);
		dto.setName(NAME);
		dto.setNumViews(VALIDVIEWS);
		dto.setPrice(PRICE);
		dto.setStatus(STATUS);
		dto.setWeight(WEIGHT);
		Artwork a = null;
		String error = null;
		try {
			a = service.createArtwork(INVALID_ARTISTID, dto);
		}catch(ArtworkException e) {
			error = e.getMessage();
		}catch(ArtistException e) {
			error = e.getMessage();
		}
		assertNull(a);
		assertEquals(error,"No artist with ID ["+INVALID_ARTISTID+"] exists");
	}
	@Test
	public void testInvalidAttributesArtworkCreation() {
		
		ArtworkDto dto = new ArtworkDto();
		dto.setCommission(COMMISSION);
		dto.setDescription(DESCRIPTION);
		dto.setDimension(DIMENSION);
		dto.setName(NAME);
		dto.setNumViews(INVALIDVIEWS);
		dto.setPrice(PRICE);
		dto.setStatus(STATUS);
		dto.setWeight(WEIGHT);
		Artwork a = null;
		String error = null;
		try {
			a = service.createArtwork(VALID_ARTISTID, dto);
		}catch(ArtworkException e) {
			error = e.getMessage();
		}catch(ArtistException e) {
			error = e.getMessage();
		}
		assertNull(a);
		assertEquals(error,"Invalid artwork attributes");
	}
}