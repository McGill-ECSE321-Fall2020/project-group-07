package ca.mcgill.ecse321.onlinegallery.service.Artwork;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.lenient;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;

import ca.mcgill.ecse321.onlinegallery.dao.ArtistRepository;
import ca.mcgill.ecse321.onlinegallery.dao.ArtworkRepository;
import ca.mcgill.ecse321.onlinegallery.model.Artist;
import ca.mcgill.ecse321.onlinegallery.model.Artwork;
import ca.mcgill.ecse321.onlinegallery.model.ArtworkStatus;
import ca.mcgill.ecse321.onlinegallery.service.ArtworkService;
import ca.mcgill.ecse321.onlinegallery.service.exception.ArtworkException;

@ExtendWith(MockitoExtension.class)
public class TestArtworkServiceGetByArtistId {
	
	@Mock
	private ArtistRepository artistRepo;
	
	@Mock
	private ArtworkRepository artworkRepo;
	
	@InjectMocks
	private ArtworkService service;
	
	private static final Long VALIDARTISTID = (long) 1;
	private static final Long INVALIDARTISTID = (long) 0;
	private static final Long ARTWORKTID = (long) 1;
	private static final Double COMMISION = (double) 0.5;
	private static final String DESCRIPTION = "extreme beauty";
	private static final String DIMENSION = "12X12";
	private static final String NAME = "Masterpiece";
	private static final int NUMVIEWS = (int) 10;
	private static final Double PRICE = (double) 999.99;
	private static final ArtworkStatus ARTWORKSTATUS = ArtworkStatus.AVAILABLE;
	private static final Double WEIGHT = (double) 15;
	
	@BeforeEach
	public void setMockOutput() {		
		lenient().when(artistRepo.existsById(anyLong())).thenAnswer((InvocationOnMock invocation) -> {
			
			if (invocation.getArgument(0).equals(VALIDARTISTID)) {
					
			return true;

			}
			
			else return false;
		});
		lenient().when(artistRepo.findArtistByArtistId(anyLong())).thenAnswer((InvocationOnMock invocation) -> {
			
			if (invocation.getArgument(0).equals(VALIDARTISTID)) {
					
			Artwork artwork = new Artwork();
			artwork.setArtworkId(ARTWORKTID);
			artwork.setCommission(COMMISION);
			artwork.setDescription(DESCRIPTION);
			artwork.setDimension(DIMENSION);
			artwork.setName(NAME);
			artwork.setNumViews(NUMVIEWS);
			artwork.setPrice(PRICE);
			artwork.setStatus(ARTWORKSTATUS);
			artwork.setWeight(WEIGHT);
			
			Artist artist = new Artist();
			artist.setArtistId(VALIDARTISTID);
			Set<Artwork> set = artist.getArtwork();
			set.add(artwork);
			artwork.setArtist(artist);
			
			return artist;

			}
			
			else return null;
		});
	}
	
	@Test
	public void testValidGetAvailableArtworkByArtistId() {
		
		Set<Artwork> set = null;
		try {
			set = service.getAvailableArtworkByArtistId(VALIDARTISTID);
		}catch(ArtworkException e) {
			fail();
		}
		assertNotNull(set);

	}
	
	@Test
	public void testInalidGetAvailableArtworkByArtistId() {
		
		Set<Artwork> set = null;
		String error = null;
		try {
			set = service.getAvailableArtworkByArtistId(INVALIDARTISTID);
		}catch(ArtworkException e) {
			error = e.getMessage();
		}
		assertNull(set);
		assertEquals(error, "No artist with artistId ["+INVALIDARTISTID+"] exists");
		
	}

}
