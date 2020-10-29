package ca.mcgill.ecse321.onlinegallery.service.Artwork;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
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

import ca.mcgill.ecse321.onlinegallery.dao.ArtworkRepository;
import ca.mcgill.ecse321.onlinegallery.model.Artwork;
import ca.mcgill.ecse321.onlinegallery.model.ArtworkStatus;
import ca.mcgill.ecse321.onlinegallery.service.ArtworkService;
import ca.mcgill.ecse321.onlinegallery.service.exception.ArtworkException;

<<<<<<< HEAD

=======
>>>>>>> origin/mass-rest
@ExtendWith(MockitoExtension.class)
public class TestArtworkServiceRetrieveRandom {
	@Mock
	private ArtworkRepository artworkRepo;
	
	@InjectMocks
	private ArtworkService service;
	
	private static final int VALIDNUMBERINPUT = (int) 2;
	private static final int INVALIDNUMBERINPUT = (int) 4;
	private static final Long ARTWORKTID1 = (long) 1;
	private static final Long ARTWORKTID2 = (long) 2;
	private static final Long ARTWORKTID3 = (long) 3;
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
			
			Artwork artwork1 = new Artwork();
			artwork1.setArtworkId(ARTWORKTID1);
			artwork1.setCommission(COMMISION);
			artwork1.setDescription(DESCRIPTION);
			artwork1.setDimension(DIMENSION);
			artwork1.setName(NAME);
			artwork1.setNumViews(NUMVIEWS);
			artwork1.setPrice(PRICE);
			artwork1.setStatus(ARTWORKSTATUS);
			artwork1.setWeight(WEIGHT);
			
			Artwork artwork2 = new Artwork();
			artwork2.setArtworkId(ARTWORKTID2);
			artwork2.setCommission(COMMISION);
			artwork2.setDescription(DESCRIPTION);
			artwork2.setDimension(DIMENSION);
			artwork2.setName(NAME);
			artwork2.setNumViews(NUMVIEWS);
			artwork2.setPrice(PRICE);
			artwork2.setStatus(ARTWORKSTATUS);
			artwork2.setWeight(WEIGHT);
			
			Artwork artwork3 = new Artwork();
			artwork3.setArtworkId(ARTWORKTID3);
			artwork3.setCommission(COMMISION);
			artwork3.setDescription(DESCRIPTION);
			artwork3.setDimension(DIMENSION);
			artwork3.setName(NAME);
			artwork3.setNumViews(NUMVIEWS);
			artwork3.setPrice(PRICE);
			artwork3.setStatus(ARTWORKSTATUS);
			artwork3.setWeight(WEIGHT);
			
			list.add(artwork1);
			list.add(artwork2);
			list.add(artwork3);
			iterables = (Iterable<Artwork>) list;
			return iterables;
		});
	}
	
	@Test
	public void testValidRetrieveRandomAvailableArtworks() {
		
		List<Artwork> list = null;
		try {
			list = service.retrieveRandomAvailableArtworks(VALIDNUMBERINPUT);
		}catch(ArtworkException e) {
			fail();
		}
		assertNotNull(list);
		assertEquals(list.size(), VALIDNUMBERINPUT);
		
	}
	
	@Test
	public void testInvalidRetrieveRandomAvailableArtworks() {
		
		List<Artwork> list = null;
		String error = null;
		try {
			list = service.retrieveRandomAvailableArtworks(INVALIDNUMBERINPUT);
		}catch(ArtworkException e) {
			error = e.getMessage();
		}
		assertNull(list);
		assertEquals(error, "there is less than ["+INVALIDNUMBERINPUT+"] artworks");
		
	}

}
<<<<<<< HEAD


=======
>>>>>>> origin/mass-rest
