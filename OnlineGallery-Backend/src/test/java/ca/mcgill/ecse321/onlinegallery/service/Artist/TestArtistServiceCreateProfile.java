package ca.mcgill.ecse321.onlinegallery.service.Artist;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
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

import ca.mcgill.ecse321.onlinegallery.dao.GalleryRegistrationRepository;
import ca.mcgill.ecse321.onlinegallery.dao.ProfileRepository;
import ca.mcgill.ecse321.onlinegallery.model.Artist;
import ca.mcgill.ecse321.onlinegallery.model.Customer;
import ca.mcgill.ecse321.onlinegallery.model.GalleryRegistration;
import ca.mcgill.ecse321.onlinegallery.model.Profile;
import ca.mcgill.ecse321.onlinegallery.service.ArtistService;

@ExtendWith(MockitoExtension.class)
public class TestArtistServiceCreateProfile {

	@Mock
	private GalleryRegistrationRepository regRepo;

	@Mock
	private ProfileRepository profileRepo;
	
	@InjectMocks
	private ArtistService service;
	
	private static final String VALID_USERNAME="validUsername";
	private static final String INVALID_USERNAME_NONEXIST="nonexistUsername";
	private static final String INVALID_USERNAME_NOT_AN_ASRTIST="userNotArtist";

	@BeforeEach
	public void setMockOutput() {
		Answer<?> paramAsAnswer = (InvocationOnMock invocation)->{
			return invocation.getArgument(0);
		};
		lenient().when(regRepo.existsByUserName(anyString())).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(VALID_USERNAME)) {
				return true;
			}
			if (invocation.getArgument(0).equals(INVALID_USERNAME_NOT_AN_ASRTIST)) {
				return true;
			}
			else {
				return false;
			}
		});
		
		lenient().when(regRepo.findGalleryRegisrationByUserName(anyString())).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(VALID_USERNAME)) {
				GalleryRegistration gallReg = new GalleryRegistration();
				Artist artist = new Artist();
				
				gallReg.setUserName(VALID_USERNAME);
				gallReg.setArtist(artist);
				
				return gallReg;
			}
			else if(invocation.getArgument(0).equals(INVALID_USERNAME_NOT_AN_ASRTIST)) {
				GalleryRegistration gallReg = new GalleryRegistration();
				Customer customer = new Customer();
				
				gallReg.setUserName(INVALID_USERNAME_NOT_AN_ASRTIST);
				gallReg.setCustomer(customer);
				
				return gallReg;
			}
			else {
				return null;
			}
		});
		
		lenient().when(profileRepo.save(any(Profile.class))).thenAnswer((InvocationOnMock invocation) -> {
			GalleryRegistration reg = new GalleryRegistration();			
			Artist artist = new Artist();
			Profile profile = new Profile();
			
			reg.setUserName(VALID_USERNAME);
			
			artist.setGalleryRegistration(reg);
			reg.setArtist(artist);
			
			profile.setSelfDescription(((Profile)invocation.getArgument(0)).getSelfDescription());
			artist.setProfile(profile);

			return profile;
		});
		
	}

	@Test
	public void createProfileValidUsername() {
		
		Profile profile = null;
		String selfDescription = "Reddit User LULW";
		
		try {
			profile = service.createProfile(VALID_USERNAME, selfDescription);
		} catch(Exception e){
			fail();
		}
		
		assertNotNull(profile);
		assertEquals(profile.getSelfDescription(),selfDescription);
		
	}
	
	@Test
	public void createProfileInValidUsernameNotAnArtist() {
		
		Profile profile = null;
		String selfDescription = "4Chan User PepeLaugh";
		String error = null;
		
		try {
			profile = service.createProfile(INVALID_USERNAME_NOT_AN_ASRTIST, selfDescription);
		} catch (Exception e){
			System.out.println(e.getMessage());
			error = e.getMessage();
		}
		
		assertNull(profile);
		assertEquals(error,"No artist exists under the username ["+INVALID_USERNAME_NOT_AN_ASRTIST+"]");
		
	}

	@Test
	public void createProfileInValidUsernameNonExistant() {
		
		Profile profile = null;
		String selfDescription = "StackOverFlow User Poggers";
		String error = null;
		
		try {
			profile = service.createProfile(INVALID_USERNAME_NONEXIST, selfDescription);
		} catch (Exception e){
			error = e.getMessage();
		}
		
		assertNull(profile);
		assertEquals(error,"No registration exists under the username ["+INVALID_USERNAME_NONEXIST+"]");
		
	}

}
