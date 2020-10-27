package ca.mcgill.ecse321.onlinegallery.serviceAll;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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

import ca.mcgill.ecse321.onlinegallery.dao.*;
import ca.mcgill.ecse321.onlinegallery.dto.*;
import ca.mcgill.ecse321.onlinegallery.model.*;
import ca.mcgill.ecse321.onlinegallery.service.GalleryRegistrationService;
import ca.mcgill.ecse321.onlinegallery.service.exception.*;

@ExtendWith(MockitoExtension.class)
public class TestGalleryRegistrationServiceCreation {
	
	@Mock
	private GalleryRegistrationRepository regRepo;
	
	@Mock  
	private OnlineGalleryRepository ogRepo;
	
	@InjectMocks
	private GalleryRegistrationService service;
	
	private static final String VALID_USERNAME = "ValidUserName";
	private static final String NO_USERNAME="";
	private static final String INVALID_USERNAMETOOSHORT="sho";
	private static final String INVALID_USERNAMETOOLONG="ThisIsAReallllllllllllllllllllllllllllllllllllllllllllllllyLongUesrname";
	private static final String INVALID_USERNAMESPECIALCHAR="Invalid@User";
	private static final String INVALID_USERNAMEDUPLICATE="ValidUser";
	private static final String INVALID_USERNAMESPACE="INValid User";

	
	private static final String VALID_FIRSTNAME = "John";
	private static final String NO_FIRSTNAME="";
	private static final String INVALID_FIRSTNAMETOOSHORT="s";
	private static final String INVALID_FIRSTNAMETOOLONG="ThisIsAReallllllllllllllllllllllllllllllllllllllllllllllllyFIRSTname";
	private static final String INVALID_FIRSTNAMESPACE="J ohn";
	private static final String INVALID_FIRSTNAMESPECIALCHAR="@dam";
	
	private static final String VALID_LASTNAME = "smith";
	private static final String NO_LASTNAME="";
	private static final String INVALID_LASTNAMETOOSHORT="t";
	private static final String INVALID_LASTNAMETOOLONG="ThisIsAReallllllllllllllllllllllllllllllllllllllllllllllllyLastTname";
	private static final String INVALID_LASTNAMESPACE="Smi th";
	private static final String INVALID_LASTNAMESPECIALCHAR="$mith";

	private static final String VALID_EMAIL="johnsmith@gmail.com";
	private static final String INVALID_EMAIL="johnsmith@gmail";

	private static final String VALID_PASSWORD="Ecse321@000";
	private static final String INVALID_PASSWORD_SHORT="ecse";
	private static final String INVALID_PASSWORD_LONG="jjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjEcse321@000";
	private static final String INVALID_PASSWORD_SPACE="Ecse321 @000";
	private static final String INVALID_PASSWORD_NOCAPITAL="ecse321@000";
	private static final String INVALID_PASSWORD_NOSPECIAL="Ecse321000";
	private static final String INVALID_PASSWORD_NOLOWER="ECSE321@000";
	private static final String INVALID_PASSWORD_NONUMBER="ECSEeafd!ddeew@";

	
	@BeforeEach
	public void setMockOutput() {
		
		Answer<?> paramAsAnswer = (InvocationOnMock invocation)->{
			return invocation.getArgument(0);
		};
		
		lenient().when(ogRepo.save(any(OnlineGallery.class))).thenAnswer(paramAsAnswer);
		
		lenient().when(regRepo.existsByUserName(anyString())).thenAnswer((InvocationOnMock invocation)->{
			if (invocation.getArgument(0).equals(INVALID_USERNAMEDUPLICATE)) {
				return true;
			}
			else {
				return false;
			} 
		});
	}
	
	@Test
	public void createValidRegistation() {
		GalleryRegistrationDto dto = new GalleryRegistrationDto();
		dto.setUsername(VALID_USERNAME);
		dto.setFirstName(VALID_FIRSTNAME);
		dto.setLastName(VALID_LASTNAME); 
		dto.setEmail(VALID_EMAIL);
		dto.setPassword(VALID_PASSWORD);
		
		GalleryRegistration reg=null;
		try {
			reg = service.createGalleryRegistration(dto);
		}
		catch(GalleryRegistrationException e) {
			fail();
		}
		assertNotNull(reg);
	}
	
	@Test
	public void createRegistrationEmptyUserName() {
		GalleryRegistrationDto dto = new GalleryRegistrationDto();
		dto.setUsername(NO_USERNAME);
		dto.setFirstName(VALID_FIRSTNAME);
		dto.setLastName(VALID_LASTNAME);
		dto.setEmail(VALID_EMAIL);
		dto.setPassword(VALID_PASSWORD);
		
		GalleryRegistration reg=null;
		String error=null;
		try {
			reg = service.createGalleryRegistration(dto);
		}
		catch(GalleryRegistrationException e) {
			error=e.getMessage();
		}	
		
		assertNull(reg);
		assertEquals(error,"username cannot be empty");
	}
	
	@Test
	public void createRegistrationInvalidUserNameTooShort() {
		GalleryRegistrationDto dto = new GalleryRegistrationDto();
		dto.setUsername(INVALID_USERNAMETOOSHORT);
		dto.setFirstName(VALID_FIRSTNAME);
		dto.setLastName(VALID_LASTNAME);
		dto.setEmail(VALID_EMAIL);
		dto.setPassword(VALID_PASSWORD);
		
		GalleryRegistration reg=null;
		String error=null;
		try {
			reg = service.createGalleryRegistration(dto);
		}
		catch(GalleryRegistrationException e) {
			error=e.getMessage();
		}	
		
		assertNull(reg);
		assertEquals(error,"username has to be 5 and 20 characters inclusively");
	}
	
	@Test
	public void createRegistrationInvalidUserNameTooLong() {
		GalleryRegistrationDto dto = new GalleryRegistrationDto();
		dto.setUsername(INVALID_USERNAMETOOLONG);
		dto.setFirstName(VALID_FIRSTNAME);
		dto.setLastName(VALID_LASTNAME);
		dto.setEmail(VALID_EMAIL);
		dto.setPassword(VALID_PASSWORD);
		
		GalleryRegistration reg=null;
		String error=null;
		try {
			reg = service.createGalleryRegistration(dto);
		}
		catch(GalleryRegistrationException e) {
			error=e.getMessage();
		}	
		
		assertNull(reg);
		assertEquals(error,"username has to be 5 and 20 characters inclusively");
	}
	
	@Test
	public void createRegistrationInvalidUserNameSpecialChar() {
		GalleryRegistrationDto dto = new GalleryRegistrationDto();
		dto.setUsername(INVALID_USERNAMESPECIALCHAR);
		dto.setFirstName(VALID_FIRSTNAME);
		dto.setLastName(VALID_LASTNAME);
		dto.setEmail(VALID_EMAIL);
		dto.setPassword(VALID_PASSWORD);
		
		GalleryRegistration reg=null;
		String error=null;
		try {
			reg = service.createGalleryRegistration(dto);
		}
		catch(GalleryRegistrationException e) {
			error=e.getMessage();
		}	
		
		assertNull(reg);
		assertEquals(error,"username cannot contain special characters");
	}
	
	@Test
	public void createRegistrationInvalidUserNameSpace() {
		GalleryRegistrationDto dto = new GalleryRegistrationDto();
		dto.setUsername(INVALID_USERNAMESPACE);
		dto.setFirstName(VALID_FIRSTNAME);
		dto.setLastName(VALID_LASTNAME);
		dto.setEmail(VALID_EMAIL);
		dto.setPassword(VALID_PASSWORD);
		
		GalleryRegistration reg=null;
		String error=null;
		try {
			reg = service.createGalleryRegistration(dto);
		}
		catch(GalleryRegistrationException e) {
			error=e.getMessage();
		}	
		
		assertNull(reg);
		assertEquals(error,"username cannot contain space");
	}
	
	@Test
	public void createRegistrationInvalidUserNameDuplicate() {
		
		GalleryRegistrationDto dto = new GalleryRegistrationDto();
		dto.setUsername(VALID_USERNAME);
		dto.setFirstName(VALID_FIRSTNAME);
		dto.setLastName(VALID_LASTNAME);
		dto.setEmail(VALID_EMAIL);
		dto.setPassword(VALID_PASSWORD);
		
		
		GalleryRegistrationDto dto2 = new GalleryRegistrationDto();
		dto2.setUsername(INVALID_USERNAMEDUPLICATE);
		dto2.setFirstName(VALID_FIRSTNAME);
		dto2.setLastName(VALID_LASTNAME);
		dto2.setEmail(VALID_EMAIL);
		dto2.setPassword(VALID_PASSWORD);
		
		GalleryRegistration reg=null;
		try {
			reg = service.createGalleryRegistration(dto);
		}
		catch(GalleryRegistrationException e) {
			fail();
		}	
		
		GalleryRegistration reg2=null;
		String error=null;
		try {
			reg2 = service.createGalleryRegistration(dto2);
		}
		catch(GalleryRegistrationException e) {
			error=e.getMessage();
		}	
		
		assertNull(reg2);
		assertEquals(error,"A GalleryRegistration with username ["+INVALID_USERNAMEDUPLICATE+"] already exists");
	}
	
	
	@Test
	public void createRegistrationNoFirstName() {
		GalleryRegistrationDto dto = new GalleryRegistrationDto();
		dto.setUsername(VALID_USERNAME);
		dto.setFirstName(NO_FIRSTNAME);
		dto.setLastName(VALID_LASTNAME);
		dto.setEmail(VALID_EMAIL);
		dto.setPassword(VALID_PASSWORD);
		
		GalleryRegistration reg=null;
		String error=null;
		try {
			reg = service.createGalleryRegistration(dto);
		}
		catch(GalleryRegistrationException e) {
			error=e.getMessage();
		}	
		
		assertNull(reg);
		assertEquals(error,"firstname cannot be empty");
	}
	
	@Test
	public void createRegistrationInValidFirstNameTooShort() {
		GalleryRegistrationDto dto = new GalleryRegistrationDto();
		dto.setUsername(VALID_USERNAME);
		dto.setFirstName(INVALID_FIRSTNAMETOOSHORT);
		dto.setLastName(VALID_LASTNAME);
		dto.setEmail(VALID_EMAIL);
		dto.setPassword(VALID_PASSWORD);
		
		GalleryRegistration reg=null;
		String error=null;
		try {
			reg = service.createGalleryRegistration(dto);
		}
		catch(GalleryRegistrationException e) {
			error=e.getMessage();
		}	
		
		assertNull(reg);
		assertEquals(error,"first or last name has to be 2 and 20 characters inclusively");
	}
	
	@Test
	public void createRegistrationInValidFirstNameTooLong() {
		GalleryRegistrationDto dto = new GalleryRegistrationDto();
		dto.setUsername(VALID_USERNAME);
		dto.setFirstName(INVALID_FIRSTNAMETOOLONG);
		dto.setLastName(VALID_LASTNAME);
		dto.setEmail(VALID_EMAIL);
		dto.setPassword(VALID_PASSWORD);
		
		GalleryRegistration reg=null;
		String error=null;
		try {
			reg = service.createGalleryRegistration(dto);
		}
		catch(GalleryRegistrationException e) {
			error=e.getMessage();
		}	
		
		assertNull(reg);
		assertEquals(error,"first or last name has to be 2 and 20 characters inclusively");
	}
	
	@Test
	public void createRegistrationInValidFirstNameSpace() {
		GalleryRegistrationDto dto = new GalleryRegistrationDto();
		dto.setUsername(VALID_USERNAME);
		dto.setFirstName(INVALID_FIRSTNAMESPACE);
		dto.setLastName(VALID_LASTNAME);
		dto.setEmail(VALID_EMAIL);
		dto.setPassword(VALID_PASSWORD);
		
		GalleryRegistration reg=null;
		String error=null;
		try {
			reg = service.createGalleryRegistration(dto);
		}
		catch(GalleryRegistrationException e) {
			error=e.getMessage();
		}	
		
		assertNull(reg);
		assertEquals(error,"first or last name cannot contain space");
	}
	
	@Test
	public void createRegistrationInValidFirstNameSpecialChars() {
		GalleryRegistrationDto dto = new GalleryRegistrationDto();
		dto.setUsername(VALID_USERNAME);
		dto.setFirstName(INVALID_FIRSTNAMESPECIALCHAR);
		dto.setLastName(VALID_LASTNAME);
		dto.setEmail(VALID_EMAIL);
		dto.setPassword(VALID_PASSWORD);
		
		GalleryRegistration reg=null;
		String error=null;
		try {
			reg = service.createGalleryRegistration(dto);
		}
		catch(GalleryRegistrationException e) {
			error=e.getMessage();
		}	
		
		assertNull(reg);
		assertEquals(error,"first or last name cannot contain special characters");
	}
	
	


	@Test
	public void createRegistrationNoLasttName() {
		GalleryRegistrationDto dto = new GalleryRegistrationDto();
		dto.setUsername(VALID_USERNAME);
		dto.setFirstName(VALID_FIRSTNAME);
		dto.setLastName(NO_LASTNAME);
		dto.setEmail(VALID_EMAIL);
		dto.setPassword(VALID_PASSWORD);
		
		GalleryRegistration reg=null;
		String error=null;
		try {
			reg = service.createGalleryRegistration(dto);
		}
		catch(GalleryRegistrationException e) {
			error=e.getMessage();
		}	
		
		assertNull(reg);
		assertEquals(error,"lastname cannot be empty");
	}
	
	@Test
	public void createRegistrationInValidLastNameTooShort() {
		GalleryRegistrationDto dto = new GalleryRegistrationDto();
		dto.setUsername(VALID_USERNAME);
		dto.setFirstName(VALID_FIRSTNAME);
		dto.setLastName(INVALID_LASTNAMETOOSHORT);
		dto.setEmail(VALID_EMAIL);
		dto.setPassword(VALID_PASSWORD);
		
		GalleryRegistration reg=null;
		String error=null;
		try {
			reg = service.createGalleryRegistration(dto);
		}
		catch(GalleryRegistrationException e) {
			error=e.getMessage();
		}	
		
		assertNull(reg);
		assertEquals(error,"first or last name has to be 2 and 20 characters inclusively");
	}
	
	@Test
	public void createRegistrationInValidLastNameTooLong() {
		GalleryRegistrationDto dto = new GalleryRegistrationDto();
		dto.setUsername(VALID_USERNAME);
		dto.setFirstName(VALID_FIRSTNAME);
		dto.setLastName(INVALID_LASTNAMETOOLONG);
		dto.setEmail(VALID_EMAIL);
		dto.setPassword(VALID_PASSWORD);
		
		GalleryRegistration reg=null;
		String error=null;
		try {
			reg = service.createGalleryRegistration(dto);
		}
		catch(GalleryRegistrationException e) {
			error=e.getMessage();
		}	
		
		assertNull(reg);
		assertEquals(error,"first or last name has to be 2 and 20 characters inclusively");
	}
	
	@Test
	public void createRegistrationInValidLastNameSpace() {
		GalleryRegistrationDto dto = new GalleryRegistrationDto();
		dto.setUsername(VALID_USERNAME);
		dto.setFirstName(VALID_FIRSTNAME);
		dto.setLastName(INVALID_LASTNAMESPACE);
		dto.setEmail(VALID_EMAIL);
		dto.setPassword(VALID_PASSWORD);
		
		GalleryRegistration reg=null;
		String error=null;
		try {
			reg = service.createGalleryRegistration(dto);
		}
		catch(GalleryRegistrationException e) {
			error=e.getMessage();
		}	
		
		assertNull(reg);
		assertEquals(error,"first or last name cannot contain space");
	}
	
	@Test
	public void createRegistrationInValidLastNameSpecialChars() {
		GalleryRegistrationDto dto = new GalleryRegistrationDto();
		dto.setUsername(VALID_USERNAME);
		dto.setFirstName(VALID_FIRSTNAME);
		dto.setLastName(INVALID_LASTNAMESPECIALCHAR);
		dto.setEmail(VALID_EMAIL);
		dto.setPassword(VALID_PASSWORD);
		
		GalleryRegistration reg=null;
		String error=null;
		try {
			reg = service.createGalleryRegistration(dto);
		}
		catch(GalleryRegistrationException e) {
			error=e.getMessage();
		}	
		
		assertNull(reg);
		assertEquals(error,"first or last name cannot contain special characters");
	}
	
	@Test
	public void createRegistrationInValidEmail() {
		GalleryRegistrationDto dto = new GalleryRegistrationDto();
		dto.setUsername(VALID_USERNAME);
		dto.setFirstName(VALID_FIRSTNAME);
		dto.setLastName(VALID_LASTNAME);
		dto.setEmail(INVALID_EMAIL);
		dto.setPassword(VALID_PASSWORD);
		
		GalleryRegistration reg=null;
		String error=null;
		try {
			reg = service.createGalleryRegistration(dto);
		}
		catch(GalleryRegistrationException e) {
			error=e.getMessage();
		}	
		
		assertNull(reg);
		assertEquals(error,"invalid email address");
	}

	@Test
	public void createRegistrationEmptyEmail() {
		GalleryRegistrationDto dto = new GalleryRegistrationDto();
		dto.setUsername(VALID_USERNAME);
		dto.setFirstName(VALID_FIRSTNAME);
		dto.setLastName(VALID_LASTNAME);
		dto.setEmail("");
		dto.setPassword(VALID_PASSWORD);
		
		GalleryRegistration reg=null;
		String error=null;
		try {
			reg = service.createGalleryRegistration(dto);
		}
		catch(GalleryRegistrationException e) {
			error=e.getMessage();
		}	
		
		assertNull(reg);
		assertEquals(error,"email address cannot be empty");
	}
	
	
	@Test
	public void createRegistrationInValidPasswordTooShort() {
		GalleryRegistrationDto dto = new GalleryRegistrationDto();
		dto.setUsername(VALID_USERNAME);
		dto.setFirstName(VALID_FIRSTNAME);
		dto.setLastName(VALID_LASTNAME);
		dto.setEmail(VALID_EMAIL);
		dto.setPassword(INVALID_PASSWORD_SHORT);
		
		GalleryRegistration reg=null;
		String error=null;
		try {
			reg = service.createGalleryRegistration(dto);
		}
		catch(GalleryRegistrationException e) {
			error=e.getMessage();
		}	
		
		assertNull(reg);
		assertEquals(error,"password has to be between 8 and 15 characters inclusively");
	}
	
	@Test
	public void createRegistrationInValidPasswordTooLong() {
		GalleryRegistrationDto dto = new GalleryRegistrationDto();
		dto.setUsername(VALID_USERNAME);
		dto.setFirstName(VALID_FIRSTNAME);
		dto.setLastName(VALID_LASTNAME);
		dto.setEmail(VALID_EMAIL);
		dto.setPassword(INVALID_PASSWORD_LONG);
		
		GalleryRegistration reg=null;
		String error=null;
		try {
			reg = service.createGalleryRegistration(dto);
		}
		catch(GalleryRegistrationException e) {
			error=e.getMessage();
		}	
		
		assertNull(reg);
		assertEquals(error,"password has to be between 8 and 15 characters inclusively");
	}
	
	@Test
	public void createRegistrationInValidPasswordSpace(){
		GalleryRegistrationDto dto = new GalleryRegistrationDto();
		dto.setUsername(VALID_USERNAME);
		dto.setFirstName(VALID_FIRSTNAME);
		dto.setLastName(VALID_LASTNAME);
		dto.setEmail(VALID_EMAIL);
		dto.setPassword(INVALID_PASSWORD_SPACE);
		
		GalleryRegistration reg=null;
		String error=null;
		try {
			reg = service.createGalleryRegistration(dto);
		}
		catch(GalleryRegistrationException e) {
			error=e.getMessage();
		}	
		
		assertNull(reg);
		assertEquals(error,"password cannot contain space");
	}
	
	@Test
	public void createRegistrationInValidPasswordNoCapital(){
		GalleryRegistrationDto dto = new GalleryRegistrationDto();
		dto.setUsername(VALID_USERNAME);
		dto.setFirstName(VALID_FIRSTNAME);
		dto.setLastName(VALID_LASTNAME);
		dto.setEmail(VALID_EMAIL);
		dto.setPassword(INVALID_PASSWORD_NOCAPITAL);
		
		GalleryRegistration reg=null;
		String error=null;
		try {
			reg = service.createGalleryRegistration(dto);
		}
		catch(GalleryRegistrationException e) {
			error=e.getMessage();
		}	
		
		assertNull(reg);
		assertEquals(error,"password has to contain at least one capital character");
	}
	
	@Test
	public void createRegistrationInValidPasswordNoSpecial(){
		GalleryRegistrationDto dto = new GalleryRegistrationDto();
		dto.setUsername(VALID_USERNAME);
		dto.setFirstName(VALID_FIRSTNAME);
		dto.setLastName(VALID_LASTNAME);
		dto.setEmail(VALID_EMAIL);
		dto.setPassword(INVALID_PASSWORD_NOSPECIAL);
		
		GalleryRegistration reg=null;
		String error=null;
		try {
			reg = service.createGalleryRegistration(dto);
		}
		catch(GalleryRegistrationException e) {
			error=e.getMessage();
		}	
		
		assertNull(reg);
		assertEquals(error,"password has to contain at least one special character");
	}
	
	@Test
	public void createRegistrationInValidPasswordNoLower(){
		GalleryRegistrationDto dto = new GalleryRegistrationDto();
		dto.setUsername(VALID_USERNAME);
		dto.setFirstName(VALID_FIRSTNAME);
		dto.setLastName(VALID_LASTNAME);
		dto.setEmail(VALID_EMAIL);
		dto.setPassword(INVALID_PASSWORD_NOLOWER);
		
		GalleryRegistration reg=null;
		String error=null;
		try {
			reg = service.createGalleryRegistration(dto);
		}
		catch(GalleryRegistrationException e) {
			error=e.getMessage();
		}	
		
		assertNull(reg);
		assertEquals(error,"password has to contain at least one lower character");
	}
	
	@Test
	public void createRegistrationInValidPasswordNoNum(){
		GalleryRegistrationDto dto = new GalleryRegistrationDto();
		dto.setUsername(VALID_USERNAME);
		dto.setFirstName(VALID_FIRSTNAME);
		dto.setLastName(VALID_LASTNAME);
		dto.setEmail(VALID_EMAIL);
		dto.setPassword(INVALID_PASSWORD_NONUMBER);
		
		GalleryRegistration reg=null;
		String error=null;
		try {
			reg = service.createGalleryRegistration(dto);
		}
		catch(GalleryRegistrationException e) {
			error=e.getMessage();
		}	
		
		assertNull(reg);
		assertEquals(error,"password has to contain at least one number");
	}
	
	@Test
	public void createRegistrationInValidPasswordEmpty(){
		GalleryRegistrationDto dto = new GalleryRegistrationDto();
		dto.setUsername(VALID_USERNAME);
		dto.setFirstName(VALID_FIRSTNAME);
		dto.setLastName(VALID_LASTNAME);
		dto.setEmail(VALID_EMAIL);
		dto.setPassword("");
		
		GalleryRegistration reg=null;
		String error=null;
		try {
			reg = service.createGalleryRegistration(dto);
		}
		catch(GalleryRegistrationException e) {
			error=e.getMessage();
		}	
		
		assertNull(reg);
		assertEquals(error,"password cannot be empty");
	}
	
}
