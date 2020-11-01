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

import ca.mcgill.ecse321.onlinegallery.dao.ArtistRepository;
import ca.mcgill.ecse321.onlinegallery.dao.GalleryRegistrationRepository;
import ca.mcgill.ecse321.onlinegallery.dto.ProfileDto;
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
	private ArtistRepository artistRepo;
	
	@InjectMocks
	private ArtistService service;
	
	@SuppressWarnings("deprecation")
	private static final Long id = new Long(1);
	
	private static final String VALID_USERNAME="validUsername";
	private static final String INVALID_USERNAME_NONEXIST="nonexistUsername";
	private static final String INVALID_USERNAME_NOT_AN_ARTIST="userNotArtist";
	private static final String INVALID_USERNAME_HAS_PROFILE ="artistWithProfile";

	@BeforeEach
	public void setMockOutput() {
		Answer<?> paramAsAnswer = (InvocationOnMock invocation)->{
			return invocation.getArgument(0);
		};
		lenient().when(regRepo.existsByUserName(anyString())).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(VALID_USERNAME)) {
				return true;
			}
			else if (invocation.getArgument(0).equals(INVALID_USERNAME_NOT_AN_ARTIST)) {
				return true;
			}
			else if (invocation.getArgument(0).equals(INVALID_USERNAME_HAS_PROFILE)) {
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
				artist.setGalleryRegistration(gallReg);
				
				return gallReg;
			}
			else if(invocation.getArgument(0).equals(INVALID_USERNAME_NOT_AN_ARTIST)) {
				GalleryRegistration gallReg = new GalleryRegistration();
				Customer customer = new Customer();
				
				gallReg.setUserName(INVALID_USERNAME_NOT_AN_ARTIST);
				gallReg.setCustomer(customer);
				customer.setGalleryRegistration(gallReg);
				
				return gallReg;
			}
			else if(invocation.getArgument(0).equals(INVALID_USERNAME_HAS_PROFILE)) {
				GalleryRegistration gallReg = new GalleryRegistration();
				Artist artist = new Artist();
				Profile profile = new Profile();
				
				artist.setProfile(profile);
				gallReg.setUserName(INVALID_USERNAME_HAS_PROFILE);
				gallReg.setArtist(artist);
				artist.setGalleryRegistration(gallReg);
				
				return gallReg;
			}
			else {
				return null;
			}
		});
		
		lenient().when(artistRepo.save(any(Artist.class))).thenAnswer((InvocationOnMock invocation) -> {
			GalleryRegistration reg = new GalleryRegistration();			
			Artist artist = new Artist();
			Profile profile = new Profile();
			
			reg.setUserName(VALID_USERNAME);
			
			artist.setGalleryRegistration(reg);
			reg.setArtist(artist);
			
			artist.setProfile(profile);
			
			profile.setSelfDescription(((Artist)invocation.getArgument(0)).getProfile().getSelfDescription());
			profile.setProfileId(id);

			return artist;
		});
		
	}

	@Test
	public void createProfileValidUsername() {
		
		Profile profile = null;
		Artist artist = null;
		String selfDescription = "Reddit User LULW";
		double totalEarnings = 0.0;
		double rating = 0.0;
		int numSold = 0;
		
		ProfileDto dto = new ProfileDto();
		dto.setNumSold(numSold);
		dto.setRating(rating);
		dto.setTotalEarnings(totalEarnings);
		dto.setSelfDescription(selfDescription);
		dto.setUsername(VALID_USERNAME);
	
		try {
			artist = service.createProfile(dto);
		} catch(Exception e){
			fail();
		}
		
		assertNotNull(artist);
		profile = artist.getProfile();
		
		assertNotNull(profile);
		assertEquals(profile.getSelfDescription(),selfDescription);
		assertEquals(profile.getNumSold(),numSold);
		assertEquals(profile.getRating(),rating);
		assertEquals(profile.getTotalEarnings(),totalEarnings);
		assertEquals(profile.getProfileId(), id);
		
	}
	
	@Test
	public void createProfileInValidUsernameNotAnArtist() {
		
		Artist artist = null;
		String selfDescription = "4Chan User PepeLaugh";
		String error = null;
		
		ProfileDto dto = new ProfileDto();
		dto.setSelfDescription(selfDescription);
		dto.setUsername(INVALID_USERNAME_NOT_AN_ARTIST);
		
		try {
			artist = service.createProfile(dto);
		} catch (Exception e){
			error = e.getMessage();
		}
		
		assertNull(artist);
		assertEquals(error,"No artist exists under the username ["+INVALID_USERNAME_NOT_AN_ARTIST+"]");
		
	}
	
	@Test
	public void createProfileInValidArtistWithProfile() {
		
		Artist artist = null;
		String selfDescription = "I love Profiles";
		String error = null;
		
		ProfileDto dto = new ProfileDto();
		dto.setSelfDescription(selfDescription);
		dto.setUsername(INVALID_USERNAME_HAS_PROFILE);
		
		try {
			artist = service.createProfile(dto);
		} catch (Exception e){
			error = e.getMessage();
		}
		
		assertNull(artist);
		assertEquals(error,"This artist already has a profile");
		
	}

	@Test
	public void createProfileInValidUsernameNonExistant() {
		
		Artist artist = null;
		String selfDescription = "StackOverFlow User Poggers";
		String error = null;
		
		ProfileDto dto = new ProfileDto();
		dto.setSelfDescription(selfDescription);
		dto.setUsername(INVALID_USERNAME_NONEXIST);
		
		try {
			artist = service.createProfile(dto);
		} catch (Exception e){
			error = e.getMessage();
		}
		
		assertNull(artist);
		assertEquals(error,"No registration exists under the username ["+INVALID_USERNAME_NONEXIST+"]");
		
	}

}