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
import ca.mcgill.ecse321.onlinegallery.dto.ProfileDto;
import ca.mcgill.ecse321.onlinegallery.model.Artist;
import ca.mcgill.ecse321.onlinegallery.model.Customer;
import ca.mcgill.ecse321.onlinegallery.model.GalleryRegistration;
import ca.mcgill.ecse321.onlinegallery.model.Profile;
import ca.mcgill.ecse321.onlinegallery.service.ArtistService;

@ExtendWith(MockitoExtension.class)
public class TestArtistServiceUpdateProfile {

	@Mock
	private GalleryRegistrationRepository regRepo;

	@Mock
	private ProfileRepository profileRepo;
	
	@InjectMocks
	private ArtistService service;
	
	private static final String VALID_USERNAME="validUsername";
	private static final String INVALID_USERNAME_NONEXIST="nonexistUsername";
	private static final String INVALID_USERNAME_NOT_AN_ASRTIST="userNotArtist";
	private static final String INVALID_USERNAME_NO_PROFILE = "ArtistNoProfile";
	
	@BeforeEach
	public void setMockOutput() {
		Answer<?> paramAsAnswer = (InvocationOnMock invocation)->{
			return invocation.getArgument(0);
		};
		lenient().when(regRepo.existsByUserName(anyString())).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(VALID_USERNAME)) {
				return true;
			}
			else if (invocation.getArgument(0).equals(INVALID_USERNAME_NOT_AN_ASRTIST)) {
				return true;
			}
			else if (invocation.getArgument(0).equals(INVALID_USERNAME_NO_PROFILE)) {
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
				Profile profile = new Profile();
				
				artist.setProfile(profile);
				gallReg.setUserName(VALID_USERNAME);
				gallReg.setArtist(artist);
				
				return gallReg;
			}
			else if(invocation.getArgument(0).equals(INVALID_USERNAME_NO_PROFILE)) {
				GalleryRegistration gallReg = new GalleryRegistration();
				Artist artist = new Artist();
			
				gallReg.setUserName(INVALID_USERNAME_NO_PROFILE);
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
			profile.setNumSold(((Profile)invocation.getArgument(0)).getNumSold());
			profile.setRating(((Profile)invocation.getArgument(0)).getRating());
			profile.setTotalEarnings(((Profile)invocation.getArgument(0)).getTotalEarnings());
			artist.setProfile(profile);

			return profile;
		});
		
	}
	
	@Test
	public void updateProfileValidUsername() {
		
		Profile profile = null;
		String selfDescription = "Hey, I love Art";
		double totalEarnings = -1000000.0;
		double rating = 01101001.0;
		int numSold = 69;
		
		ProfileDto profileDto = new ProfileDto();
		profileDto.setNumSold(numSold);
		profileDto.setRating(rating);
		profileDto.setSelfDescription(selfDescription);
		profileDto.setTotalEarnings(totalEarnings);
		
		try {
			profile = service.updateProfile(VALID_USERNAME, profileDto);
		} catch(Exception e){
			fail();
		}
		
		assertNotNull(profile);
		assertEquals(profile.getSelfDescription(),selfDescription);
		assertEquals(profile.getNumSold(),numSold);
		assertEquals(profile.getRating(),rating);
		assertEquals(profile.getTotalEarnings(),totalEarnings);
		
	}
	
	@Test
	public void updateProfileInValidUsernameNoProfile() {
		
		Profile profile = null;
		String selfDescription = "Sup, I love Art";
		double totalEarnings = -1000000.0;
		double rating = 01101001.0;
		int numSold = 69;
		
		String error = null;
		
		ProfileDto profileDto = new ProfileDto();
		profileDto.setNumSold(numSold);
		profileDto.setRating(rating);
		profileDto.setSelfDescription(selfDescription);
		profileDto.setTotalEarnings(totalEarnings);
		
		try {
			profile = service.updateProfile(INVALID_USERNAME_NO_PROFILE, profileDto);
		} catch (Exception e){
			System.out.println(e.getMessage());
			error = e.getMessage();
		}
		
		assertNull(profile);
		assertEquals(error,"No profile exists for this artist ["+INVALID_USERNAME_NO_PROFILE+"]");
		
	}
	
	@Test
	public void updateProfileInValidUsernameNotAnArtist() {
		
		Profile profile = null;
		String selfDescription = "Sup, I hate Art";
		double totalEarnings = -1000000.0;
		double rating = 01101001.0;
		int numSold = 69;
		
		String error = null;
		
		ProfileDto profileDto = new ProfileDto();
		profileDto.setNumSold(numSold);
		profileDto.setRating(rating);
		profileDto.setSelfDescription(selfDescription);
		profileDto.setTotalEarnings(totalEarnings);
		
		try {
			profile = service.updateProfile(INVALID_USERNAME_NOT_AN_ASRTIST, profileDto);
		} catch (Exception e){
			System.out.println(e.getMessage());
			error = e.getMessage();
		}
		
		assertNull(profile);
		assertEquals(error,"No artist exists under the username ["+INVALID_USERNAME_NOT_AN_ASRTIST+"]");
		
	}

	@Test
	public void updateProfileInValidUsernameNonExistant() {
		
		Profile profile = null;
		String selfDescription = "Hey, I hate Art";
		double totalEarnings = -1000000.0;
		double rating = 01101001.0;
		int numSold = 69;
		
		String error = null;
		
		ProfileDto profileDto = new ProfileDto();
		profileDto.setNumSold(numSold);
		profileDto.setRating(rating);
		profileDto.setSelfDescription(selfDescription);
		profileDto.setTotalEarnings(totalEarnings);
		
		try {
			profile = service.updateProfile(INVALID_USERNAME_NONEXIST, profileDto);
		} catch (Exception e){
			error = e.getMessage();
		}
		
		assertNull(profile);
		assertEquals(error,"No registration exists under the username ["+INVALID_USERNAME_NONEXIST+"]");
		
	}

}
