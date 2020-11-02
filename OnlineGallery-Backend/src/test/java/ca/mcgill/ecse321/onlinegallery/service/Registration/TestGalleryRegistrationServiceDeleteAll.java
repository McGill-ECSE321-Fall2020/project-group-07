package ca.mcgill.ecse321.onlinegallery.service.Registration;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;

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
import ca.mcgill.ecse321.onlinegallery.dao.*;
import ca.mcgill.ecse321.onlinegallery.model.*;
import ca.mcgill.ecse321.onlinegallery.service.GalleryRegistrationService;
import ca.mcgill.ecse321.onlinegallery.service.exception.*;

@ExtendWith(MockitoExtension.class)
public class TestGalleryRegistrationServiceDeleteAll {

	@Mock
	private GalleryRegistrationRepository regRepo;

	@Mock
	private OnlineGalleryRepository ogRepo;

	@Mock
	private GalleryAdminRepository adminRepo;

	@Mock
	private CustomerRepository custRepo;

	@Mock
	private ArtistRepository artistRepo;

	@InjectMocks
	private GalleryRegistrationService service;

	private static final String VALID_USERNAME1 = "ValidUserName1";
	private static final String VALID_USERNAME2 = "ValidUserName2";

	@BeforeEach
	public void setMockOutput() {
		
		lenient().when(regRepo.existsByUserName(anyString())).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(VALID_USERNAME1)) {
				return true;
			} else if (invocation.getArgument(0).equals(VALID_USERNAME2)) {
				return true;
			} 
			return false;
		});
		
		lenient().when(regRepo.count()).thenReturn((long) 2);
		lenient().when(regRepo.findAll()).thenAnswer((InvocationOnMock invocation) -> {
			GalleryRegistration reg1 = new GalleryRegistration();
			reg1.setUserName(VALID_USERNAME1);

			GalleryRegistration reg2 = new GalleryRegistration();
			reg2.setUserName(VALID_USERNAME2);

			Set<GalleryRegistration> allReg = new HashSet<GalleryRegistration>();

			allReg.add(reg1);
			allReg.add(reg2);

			return allReg;
		});

		lenient().when(regRepo.findGalleryRegisrationByUserName(anyString()))
				.thenAnswer((InvocationOnMock invocation) -> {
					if (invocation.getArgument(0).equals(VALID_USERNAME1)) {
						GalleryRegistration reg = new GalleryRegistration();
						reg.setUserName(VALID_USERNAME1);
						return reg;
					}
					else if (invocation.getArgument(0).equals(VALID_USERNAME2)) {
						GalleryRegistration reg = new GalleryRegistration();
						reg.setUserName(VALID_USERNAME2);
						return reg;
					}

					return null;
				});

		lenient().when(ogRepo.findAll()).thenAnswer((i) -> {
			Set<OnlineGallery> ogSet = new HashSet<OnlineGallery>();

			OnlineGallery og = new OnlineGallery();
			GalleryRegistration reg1 = new GalleryRegistration();
			GalleryRegistration reg2 = new GalleryRegistration();

			reg1.setUserName(VALID_USERNAME1);
			reg2.setUserName(VALID_USERNAME2);


			og.getAllRegistrations().add(reg1);
			og.getAllRegistrations().add(reg2);

			ogSet.add(og);

			return ogSet;
		});

		lenient().doAnswer((i) -> null).when(regRepo).delete(any(GalleryRegistration.class));

	}

	@Test
	public void testDeleteAllTwoExists() {

		List<GalleryRegistration> allReg = null;
		try {
			allReg = service.deleteAllGalleryRegistration();
		} catch (GalleryRegistrationException e) {
			fail();
		}
		assertNotNull(allReg);
	}
}
