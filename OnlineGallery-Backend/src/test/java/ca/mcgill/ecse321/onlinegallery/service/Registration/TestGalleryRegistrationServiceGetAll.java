package ca.mcgill.ecse321.onlinegallery.service.Registration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import ca.mcgill.ecse321.onlinegallery.dao.*;
import ca.mcgill.ecse321.onlinegallery.dto.*;
import ca.mcgill.ecse321.onlinegallery.model.*;
import ca.mcgill.ecse321.onlinegallery.service.GalleryRegistrationService;
import ca.mcgill.ecse321.onlinegallery.service.exception.*;

@ExtendWith(MockitoExtension.class)
public class TestGalleryRegistrationServiceGetAll {

	@Mock
	private GalleryRegistrationRepository regRepo;

	@Mock
	private OnlineGalleryRepository ogRepo;

	@InjectMocks
	private GalleryRegistrationService service;



	@BeforeEach
	public void setMockOutput() {

		Answer<?> paramAsAnswer = (InvocationOnMock invocation) -> {
			return invocation.getArgument(0);
		}; 

		lenient().when(regRepo.count()).thenReturn((long) 2);
		lenient().when(regRepo.findAll()).thenAnswer((InvocationOnMock invocation)->{
			GalleryRegistration reg1 = new GalleryRegistration();
			reg1.setUserName("user1");
			
			GalleryRegistration reg2 = new GalleryRegistration();
			reg2.setUserName("user2");
			
			Set<GalleryRegistration> allReg = new HashSet<GalleryRegistration>();
			
			allReg.add(reg1);
			allReg.add(reg2);
			
			return allReg;
		});
	}

	@Test
	public void testGetAllRegistratrionsNonEmpty() {

		List<GalleryRegistration> allReg = null;
		try {
			allReg=service.getAllGalleryRegistrations();
		} catch (GalleryRegistrationException e) {
			fail();
		}
		assertNotNull(allReg);
		assertEquals(allReg.size(),2);
		
		List<String> expectedUserNames=new ArrayList<String>(List.of("user1","user2"));
		
		for (GalleryRegistration eachReg:allReg) {
			assertEquals(true,expectedUserNames.contains(eachReg.getUserName()));
		}
		
		
	}




}
