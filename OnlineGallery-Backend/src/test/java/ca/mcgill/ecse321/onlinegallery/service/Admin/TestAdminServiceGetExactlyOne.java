package ca.mcgill.ecse321.onlinegallery.service.Admin;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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

import ca.mcgill.ecse321.onlinegallery.dao.GalleryAdminRepository;
import ca.mcgill.ecse321.onlinegallery.model.GalleryAdmin;
import ca.mcgill.ecse321.onlinegallery.service.AdminService;

@ExtendWith(MockitoExtension.class)
public class TestAdminServiceGetExactlyOne {

	@Mock
	private GalleryAdminRepository adminRepo;
	
	@InjectMocks
	private AdminService service;
	
	
	@SuppressWarnings("deprecation")
	private static final Long ADMIN_ID = new Long(1);
	
	@BeforeEach
	public void setMockOutput() {		

		lenient().when(adminRepo.findAll()).thenAnswer((InvocationOnMock invocation) -> {
			
			GalleryAdmin admin = new GalleryAdmin();
			admin.setAdminId(ADMIN_ID);
			
			List<GalleryAdmin> admins = new ArrayList<GalleryAdmin>();
			admins.add(admin);
			
			Iterable<GalleryAdmin> iterable = admins; 
			return iterable;
		});
	}
	
	@Test
	public void TestGetAdminValid() {
		
		GalleryAdmin admin = null;
		
		try {
			admin = service.getAdmin();
		} catch (Exception e) {
			fail();
		}
		
		assertNotNull(admin);
		assertEquals(admin.getAdminId(), ADMIN_ID);
		
	}
	
}
