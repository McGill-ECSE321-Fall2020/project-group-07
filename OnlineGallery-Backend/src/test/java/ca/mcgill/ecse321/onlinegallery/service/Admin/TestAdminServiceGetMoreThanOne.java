package ca.mcgill.ecse321.onlinegallery.service.Admin;

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
import org.mockito.stubbing.Answer;

import ca.mcgill.ecse321.onlinegallery.dao.GalleryAdminRepository;
import ca.mcgill.ecse321.onlinegallery.model.GalleryAdmin;
import ca.mcgill.ecse321.onlinegallery.service.AdminService;

@ExtendWith(MockitoExtension.class)
public class TestAdminServiceGetMoreThanOne {

	@Mock
	private GalleryAdminRepository adminRepo;
	
	@InjectMocks
	private AdminService service;
	
	@BeforeEach
	public void setMockOutput() {		
		Answer<?> paramAsAnswer = (InvocationOnMock invocation)->{
			return invocation.getArgument(0);
		};
		lenient().when(adminRepo.findAll()).thenAnswer((InvocationOnMock invocation) -> {
			
			GalleryAdmin admin1 = new GalleryAdmin();
			GalleryAdmin admin2 = new GalleryAdmin();
			List<GalleryAdmin> admins = new ArrayList<GalleryAdmin>();
			admins.add(admin1);
			admins.add(admin2);
			
			Iterable<GalleryAdmin> iterable = admins; 
			return iterable;
		});
	}
	
	@Test
	public void TestGetAdminInValidToMany() {
		
		GalleryAdmin admin = null;
		String error = null;
		
		try {
			admin = service.getAdmin();
		} catch (Exception e) {
			error = e.getMessage();
		}
		
		assertNull(admin);
		assertEquals(error, "There exists more than one Admin");
		
	}
	
}
