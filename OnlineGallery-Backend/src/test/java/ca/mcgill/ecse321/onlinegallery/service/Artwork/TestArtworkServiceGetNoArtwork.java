package ca.mcgill.ecse321.onlinegallery.service.Artwork;

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

import ca.mcgill.ecse321.onlinegallery.dao.ArtworkRepository;
import ca.mcgill.ecse321.onlinegallery.model.Artwork;
import ca.mcgill.ecse321.onlinegallery.service.ArtworkService;
import ca.mcgill.ecse321.onlinegallery.service.exception.ArtworkException;

@ExtendWith(MockitoExtension.class)
public class TestArtworkServiceGetNoArtwork {
	
	@Mock
	private ArtworkRepository artworkRepo;
	
	@InjectMocks
	private ArtworkService service;
	
	@BeforeEach
	public void setMockOutput() {

		lenient().when(artworkRepo.findAll()).thenAnswer((InvocationOnMock invocation)->{
			
			List<Artwork> list = new ArrayList<Artwork>();
						
			return list;
		});
	}
	@Test
	public void testInvalidFindEmpty() {
		
		List<Artwork> a = null;
		String error = null;
		try {
			a = service.getAllAvailableArtwork();
		}catch(ArtworkException e) {
			error = e.getMessage();
		}
		assertNull(a);
		assertEquals(error, "No artwork exists");
	}
}