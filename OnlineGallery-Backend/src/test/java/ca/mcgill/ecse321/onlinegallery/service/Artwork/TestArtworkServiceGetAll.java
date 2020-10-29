package ca.mcgill.ecse321.onlinegallery.service.Artwork;


import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.anyString;
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
import ca.mcgill.ecse321.onlinegallery.dao.GalleryRegistrationRepository;
import ca.mcgill.ecse321.onlinegallery.model.Artist;
import ca.mcgill.ecse321.onlinegallery.model.Artwork;
import ca.mcgill.ecse321.onlinegallery.model.ArtworkStatus;
import ca.mcgill.ecse321.onlinegallery.model.GalleryRegistration;
import ca.mcgill.ecse321.onlinegallery.service.ArtworkService;
import ca.mcgill.ecse321.onlinegallery.service.ArtistService;
import ca.mcgill.ecse321.onlinegallery.service.exception.ArtworkException;


@ExtendWith(MockitoExtension.class)
public class TestArtworkServiceGetAll {
	
//	@Mock
//	private GalleryRegistrationRepository regRepo;
//	
//	@Mock
//	private ArtistRepository artistRepo;
	
	@Mock
	private ArtworkRepository artworkRepo;
	
	@InjectMocks
	private ArtworkService service;
	
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
		lenient().when(artworkRepo.findAll()).thenAnswer((InvocationOnMock invocation) -> {
			
			ArrayList<Artwork> list = new ArrayList<Artwork>();
			Iterable<Artwork> iterables = new ArrayList<Artwork>();
			
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
			
			list.add(artwork);
			iterables = (Iterable<Artwork>) list;
			return iterables;
		});
	}
	
	@Test
	public void testValidGetAllArtworks() {
		
		List<Artwork> list = null;
		try {
			list = service.getAllArtworks();
		}catch(ArtworkException e) {
			fail();
		}
		assertNotNull(list);
	}

}