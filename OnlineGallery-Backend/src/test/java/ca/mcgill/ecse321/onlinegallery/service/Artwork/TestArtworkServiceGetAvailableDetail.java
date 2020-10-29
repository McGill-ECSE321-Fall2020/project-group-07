package ca.mcgill.ecse321.onlinegallery.service.Artwork;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.lenient;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import ca.mcgill.ecse321.onlinegallery.model.Artist;
import ca.mcgill.ecse321.onlinegallery.model.Artwork;
import ca.mcgill.ecse321.onlinegallery.model.ArtworkStatus;
import ca.mcgill.ecse321.onlinegallery.model.GalleryRegistration;
import ca.mcgill.ecse321.onlinegallery.service.ArtworkService;
import ca.mcgill.ecse321.onlinegallery.service.exception.ArtworkException;
import ca.mcgill.ecse321.onlinegallery.service.ArtistService;

@ExtendWith(MockitoExtension.class)
public class TestArtworkServiceGetAvailableDetail {
	
	
	@Mock
	private ArtworkRepository artworkRepo;
	
	@InjectMocks
	private ArtworkService service;
	

	private static final Long VALIDARTWORKTID_AVAILABLE = (long) 1;
	private static final Long VALIDARTWORKTID_UNAVAILABLE = (long) 2;
	private static final Long INVALIDARTWORKTID = (long) 3;
	private static final Double COMMISION = (double) 0.5;
	private static final String DESCRIPTION = "extreme beauty";
	private static final String DIMENSION = "12X12";
	private static final String NAME = "Masterpiece";
	private static final int NUMVIEWS = (int) 10;
	private static final Double PRICE = (double) 999.99;
	private static final ArtworkStatus ARTWORKSTATUS_AVAILABLE = ArtworkStatus.AVAILABLE;
	private static final ArtworkStatus ARTWORKSTATUS_UNAVAILABLE = ArtworkStatus.UNAVAILABLE;
	private static final Double WEIGHT = (double) 15;
	
	@BeforeEach
	public void setMockOutput() {		
		lenient().when(artworkRepo.existsByArtworkId(anyLong())).thenAnswer((InvocationOnMock invocation) -> {
			
			if (invocation.getArgument(0).equals(VALIDARTWORKTID_AVAILABLE) || invocation.getArgument(0).equals(VALIDARTWORKTID_UNAVAILABLE)) {
					
			return true;

			}
			
			else return false;
		});
		lenient().when(artworkRepo.findArtworkByArtworkId(anyLong())).thenAnswer((InvocationOnMock invocation) -> {
			
			if (invocation.getArgument(0).equals(VALIDARTWORKTID_AVAILABLE)) {
					
			Artwork artwork = new Artwork();
			artwork.setArtworkId(VALIDARTWORKTID_AVAILABLE);
			artwork.setCommission(COMMISION);
			artwork.setDescription(DESCRIPTION);
			artwork.setDimension(DIMENSION);
			artwork.setName(NAME);
			artwork.setNumViews(NUMVIEWS);
			artwork.setPrice(PRICE);
			artwork.setStatus(ARTWORKSTATUS_AVAILABLE);
			artwork.setWeight(WEIGHT);
		
			
			return artwork;

			}
			else if (invocation.getArgument(0).equals(VALIDARTWORKTID_UNAVAILABLE)) {
				
			Artwork artwork = new Artwork();
			artwork.setArtworkId(VALIDARTWORKTID_UNAVAILABLE);
			artwork.setCommission(COMMISION);
			artwork.setDescription(DESCRIPTION);
			artwork.setDimension(DIMENSION);
			artwork.setName(NAME);
			artwork.setNumViews(NUMVIEWS);
			artwork.setPrice(PRICE);
			artwork.setStatus(ARTWORKSTATUS_UNAVAILABLE);
			artwork.setWeight(WEIGHT);
		
			
			return artwork;

			}
			
			else return null;
		});
	}
	
	@Test
	public void testValidGetAvailableDetail() {
		
		Artwork artwork = null;
		try {
			artwork = service.getAvailableArtworkDetail(VALIDARTWORKTID_AVAILABLE);
		}catch(ArtworkException e) {
			fail();
		}
		assertNotNull(artwork);

	}
	
	@Test
	public void testInvalidGetAvailableDetail() {
		
		Artwork artwork = null;
		String error = null;
		try {
			artwork = service.getAvailableArtworkDetail(INVALIDARTWORKTID);
		}catch(ArtworkException e) {
			error = e.getMessage();
		}
		assertNull(artwork);
		assertEquals(error, "No artwork with artworkID ["+INVALIDARTWORKTID+"] exists");
		
	}
	
	@Test
	public void testUnavailableGetAvailableDetail() {
		
		Artwork artwork = null;
		String error = null;
		try {
			artwork = service.getAvailableArtworkDetail(VALIDARTWORKTID_UNAVAILABLE);
		}catch(ArtworkException e) {
			error = e.getMessage();
		}
		assertNull(artwork);
		assertEquals(error, "Artwork with artworkID ["+VALIDARTWORKTID_UNAVAILABLE+"] is unavailable");
		
	}

}