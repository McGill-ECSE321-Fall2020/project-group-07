package ca.mcgill.ecse321.onlinegallery.serviceAll;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
import ca.mcgill.ecse321.onlinegallery.service.*;
import ca.mcgill.ecse321.onlinegallery.service.exception.*;

@ExtendWith(MockitoExtension.class)
public class TestShipmentServicePayShipment {
	
	@Mock
	private ShipmentRepository shipRepo;

	@Mock
	private PurchaseRepository purchaseRepo;

	@InjectMocks
	private ShipmentService service;
	
	private static final Long VALID_SHIPID_EXIST_HAS_PURCHASE = (long) 0;
	private static final Long VALID_SHIPID_EXIST_HAS_NO_PURCHASE = (long) 1;
	private static final Long INVALID_SHIPID_NONEXIST = (long) 3;
	
	private static final String VALID_CCNUM="4024007159291722";
	private static final String INVALID_CCNUM_EMPTY="";
	private static final String INVALID_CCNUM_SPECIAL_CHAR="4024!0715929@722";
	private static final String INVALID_CCNUM_NOT_VISA="2024007159291722";
	private static final String INVALID_CCNUM_WRONG_LENGTH="402400715929152722";
	private static final String INVALID_CCNUM_WRONG_CHECKSUM="4024007159291721";
	
	private static final String VALID_CSV="123";
	private static final String INVALID_CSV_EMPTY="";
	private static final String INVALID_CSV_SPECIAL_CHAR="!123";
	private static final String INVALID_CSV_WRONG_LENGTH="1234";

	private static final String VALID_EXP="1023";
	private static final String INVALID_EXP_EMPTY="";
	private static final String INVALID_EXP_SPECIAL_CHAR="!102";
	private static final String INVALID_EXP_WRONG_LENGTH="123";
	private static final String INVALID_EXP_INVALID_MONTH="1325";
	private static final String INVALID_EXP_YEAR="1019";
	private static final String INVALID_EXP_MONTH="0920";

	private static final String VALID_FIRSTNAME="Han";
	private static final String INVALID_FIRSTNAME_EMPTY="";
	private static final String INVALID_FIRSTNAME_SPACE="Ha N";
	private static final String INVALID_FIRSTNAME_SPECIAL_CHAR="Han!";
	
	@BeforeEach
	public void MockOutput() {
		lenient().when(shipRepo.existsById(anyLong())).thenAnswer((InvocationOnMock i) -> {
			if (i.getArgument(0).equals(INVALID_SHIPID_NONEXIST)) {
				return false;
			} else {
				return true;
			}
		});
		
		lenient().when(shipRepo.findShipmentByShipmentId(anyLong())).thenAnswer((InvocationOnMock i) -> {
			if (i.getArgument(0).equals(VALID_SHIPID_EXIST_HAS_PURCHASE)) {
				Purchase p = new Purchase();
				p.setShipmentType(ShipmentType.OFFSITE_DELIVERY);
				p.setPurchaseId((long) 1);

				Shipment s = new Shipment();
				s.getPurchase().add(p);

				return s;
			}
			if (i.getArgument(0).equals(VALID_SHIPID_EXIST_HAS_NO_PURCHASE)) {

				Shipment s = new Shipment();
				return s;
			}

			return null;
		});
		
		lenient().when(shipRepo.save(any(Shipment.class))).thenAnswer((InvocationOnMock i) -> {
			return i.getArgument(0);
		});
	}
	
	@Test
	public void payValidShipmentWithPurchaseValidCreditInfo(){
		PaymentDto dto = new PaymentDto();
		dto.setShipmentId(VALID_SHIPID_EXIST_HAS_PURCHASE);
		dto.setCcNum(VALID_CCNUM);
		dto.setCcCSV(VALID_CSV);
		dto.setCcExp(VALID_EXP);
		dto.setCcFirstname(VALID_FIRSTNAME);
		dto.setCcLastname("Solo");
		
		Shipment s = null;
		try {
			s=service.payShipment(dto);
		}
		catch(CreditCardException | ShipmentException e) {
			fail();
		} 
		
		assertNotNull(s);
		assertEquals(s.getShipmentStatus(),ShipmentStatus.PAID);
		assertEquals(s.getCreditCardNumber(),VALID_CCNUM.substring(VALID_CCNUM.length()-4));
	}
	
	@Test
	public void payValidShipmentWithNoPurchaseValidCreditInfo(){
		PaymentDto dto = new PaymentDto();
		dto.setShipmentId(VALID_SHIPID_EXIST_HAS_NO_PURCHASE);
		dto.setCcNum(VALID_CCNUM);
		dto.setCcCSV(VALID_CSV);
		dto.setCcExp(VALID_EXP);
		dto.setCcFirstname(VALID_FIRSTNAME);
		dto.setCcLastname("Solo");
		 
		Shipment s = null;
		String error=null;
		try {
			s=service.payShipment(dto);
		}
		catch(CreditCardException | ShipmentException e) {
			error=e.getMessage();
		} 
		
		assertNull(s);
		assertEquals(error,"Shipment with id ["+VALID_SHIPID_EXIST_HAS_NO_PURCHASE+"] has no Purchases. Cannot pay for empty Shipment");
	}
	
	@Test
	public void payInvalidShipmentNonExistValidCreditInfo(){
		PaymentDto dto = new PaymentDto();
		dto.setShipmentId(INVALID_SHIPID_NONEXIST);
		dto.setCcNum(VALID_CCNUM);
		dto.setCcCSV(VALID_CSV);
		dto.setCcExp(VALID_EXP);
		dto.setCcFirstname(VALID_FIRSTNAME);
		dto.setCcLastname("Solo"); 
		 
		Shipment s = null;
		String error=null;
		try {
			s=service.payShipment(dto);
		}
		catch(CreditCardException | ShipmentException e) {
			error=e.getMessage();
		} 
		
		assertNull(s);
		assertEquals(error,"no Shipment with id ["+INVALID_SHIPID_NONEXIST+"] found");
	}
	
	@Test
	public void payValidShipmentWithPurchaseInvalidCreditNumberEmpty(){
		PaymentDto dto = new PaymentDto();
		dto.setShipmentId(VALID_SHIPID_EXIST_HAS_PURCHASE);
		dto.setCcNum(INVALID_CCNUM_EMPTY);
		dto.setCcCSV(VALID_CSV);
		dto.setCcExp(VALID_EXP);
		dto.setCcFirstname(VALID_FIRSTNAME);
		dto.setCcLastname("Solo"); 
		 
		Shipment s = null;
		String error=null;
		try {
			s=service.payShipment(dto);
		}
		catch(CreditCardException | ShipmentException e) {
			error=e.getMessage();
		} 
		
		assertNull(s);
		assertEquals(error,"credit card number cannot be empty");
	}
	
	@Test
	public void payValidShipmentWithPurchaseInvalidCreditNumberSpecialChar(){
		PaymentDto dto = new PaymentDto();
		dto.setShipmentId(VALID_SHIPID_EXIST_HAS_PURCHASE);
		dto.setCcNum(INVALID_CCNUM_SPECIAL_CHAR);
		dto.setCcCSV(VALID_CSV);
		dto.setCcExp(VALID_EXP);
		dto.setCcFirstname(VALID_FIRSTNAME);
		dto.setCcLastname("Solo"); 
		 
		Shipment s = null;
		String error=null;
		try {
			s=service.payShipment(dto);
		}
		catch(CreditCardException | ShipmentException e) {
			error=e.getMessage();
		} 
		
		assertNull(s);
		assertEquals(error,"cred card number should only contain digits 0-9, space, and dash");
	}
	
	@Test
	public void payValidShipmentWithPurchaseInvalidCreditNumberNonVisa(){
		PaymentDto dto = new PaymentDto();
		dto.setShipmentId(VALID_SHIPID_EXIST_HAS_PURCHASE);
		dto.setCcNum(INVALID_CCNUM_NOT_VISA);
		dto.setCcCSV(VALID_CSV);
		dto.setCcExp(VALID_EXP);
		dto.setCcFirstname(VALID_FIRSTNAME);
		dto.setCcLastname("Solo"); 
		 
		Shipment s = null;
		String error=null;
		try {
			s=service.payShipment(dto);
		}
		catch(CreditCardException | ShipmentException e) {
			error=e.getMessage();
		} 
		
		assertNull(s);
		assertEquals(error,"sorry, only Visa cards accepted. Those credit card numbers start with 4");
	}
	
	@Test
	public void payValidShipmentWithPurchaseInvalidCreditNumberWrongLength(){

		PaymentDto dto = new PaymentDto();
		dto.setShipmentId(VALID_SHIPID_EXIST_HAS_PURCHASE);
		dto.setCcNum(INVALID_CCNUM_WRONG_LENGTH);
		dto.setCcCSV(VALID_CSV);
		dto.setCcExp(VALID_EXP);
		dto.setCcFirstname(VALID_FIRSTNAME);
		dto.setCcLastname("Solo"); 
		 
		Shipment s = null;
		String error=null;
		try {
			s=service.payShipment(dto);
		}
		catch(CreditCardException | ShipmentException e) {
			error=e.getMessage();
		} 
		
		assertNull(s);
		assertEquals(error,"invalid Visa credit card number. Those are either 13, 16, or 19 dgits. Check and try again");
	}
	
	@Test
	public void payValidShipmentWithPurchaseInvalidCreditNumberWrongChecksum(){
		PaymentDto dto = new PaymentDto();
		dto.setShipmentId(VALID_SHIPID_EXIST_HAS_PURCHASE);
		dto.setCcNum(INVALID_CCNUM_WRONG_CHECKSUM);
		dto.setCcCSV(VALID_CSV);
		dto.setCcExp(VALID_EXP);
		dto.setCcFirstname(VALID_FIRSTNAME);
		dto.setCcLastname("Solo"); 
		 
		Shipment s = null;
		String error=null;
		try {
			s=service.payShipment(dto);
		}
		catch(CreditCardException | ShipmentException e) {
			error=e.getMessage();
		} 
		
		assertNull(s);
		assertEquals(error,"invalid Visa credit card number. Try again");
	}
	
	@Test
	public void payValidShipmentWithPurchaseInvalidCreditExpEmpty(){
		PaymentDto dto = new PaymentDto();
		dto.setShipmentId(VALID_SHIPID_EXIST_HAS_PURCHASE);
		dto.setCcNum(VALID_CCNUM);
		dto.setCcCSV(VALID_CSV);
		dto.setCcExp(INVALID_EXP_EMPTY);
		dto.setCcFirstname(VALID_FIRSTNAME);
		dto.setCcLastname("Solo"); 
		 
		Shipment s = null;
		String error=null;
		try {
			s=service.payShipment(dto);
		}
		catch(CreditCardException | ShipmentException e) {
			error=e.getMessage();
		} 
		
		assertNull(s);
		assertEquals(error,"credit card expiration date cannot be empty");
	}
	
	@Test
	public void payValidShipmentWithPurchaseInvalidCreditExpSpecialChars(){
		PaymentDto dto = new PaymentDto();
		dto.setShipmentId(VALID_SHIPID_EXIST_HAS_PURCHASE);
		dto.setCcNum(VALID_CCNUM);
		dto.setCcCSV(VALID_CSV);
		dto.setCcExp(INVALID_EXP_SPECIAL_CHAR);
		dto.setCcFirstname(VALID_FIRSTNAME);
		dto.setCcLastname("Solo"); 
		 
		Shipment s = null;
		String error=null;
		try {
			s=service.payShipment(dto);
		}
		catch(CreditCardException | ShipmentException e) {
			error=e.getMessage();
		} 
		
		assertNull(s);
		assertEquals(error,"credit card expiration date should only contain digits 0-9");
	}
	
	@Test
	public void payValidShipmentWithPurchaseInvalidCreditExpWrongLength(){
		PaymentDto dto = new PaymentDto();
		dto.setShipmentId(VALID_SHIPID_EXIST_HAS_PURCHASE);
		dto.setCcNum(VALID_CCNUM);
		dto.setCcCSV(VALID_CSV);
		dto.setCcExp(INVALID_EXP_WRONG_LENGTH);
		dto.setCcFirstname(VALID_FIRSTNAME);
		dto.setCcLastname("Solo"); 
		 
		Shipment s = null;
		String error=null;
		try {
			s=service.payShipment(dto);
		}
		catch(CreditCardException | ShipmentException e) {
			error=e.getMessage();
		} 
		
		assertNull(s);
		assertEquals(error,"invalid credit card expiration date. Must be only 4 digits. Check and try again");
	}
	
	@Test
	public void payValidShipmentWithPurchaseInvalidCreditExpInvalidMonth(){
		PaymentDto dto = new PaymentDto();
		dto.setShipmentId(VALID_SHIPID_EXIST_HAS_PURCHASE);
		dto.setCcNum(VALID_CCNUM);
		dto.setCcCSV(VALID_CSV);
		dto.setCcExp(INVALID_EXP_INVALID_MONTH);
		dto.setCcFirstname(VALID_FIRSTNAME);
		dto.setCcLastname("Solo"); 
		 
		Shipment s = null;
		String error=null;
		try {
			s=service.payShipment(dto);
		}
		catch(CreditCardException | ShipmentException e) {
			error=e.getMessage();
		} 
		
		assertNull(s);
		assertEquals(error,"invalid credit card expiration date. Month must be between 1-12 inclusive");
	}
	
	@Test
	public void payValidShipmentWithPurchaseInvalidCreditExpiredYear(){
		PaymentDto dto = new PaymentDto();
		dto.setShipmentId(VALID_SHIPID_EXIST_HAS_PURCHASE);
		dto.setCcNum(VALID_CCNUM);
		dto.setCcCSV(VALID_CSV);
		dto.setCcExp(INVALID_EXP_YEAR);
		dto.setCcFirstname(VALID_FIRSTNAME);
		dto.setCcLastname("Solo"); 
		 
		Shipment s = null;
		String error=null;
		try {
			s=service.payShipment(dto);
		}
		catch(CreditCardException | ShipmentException e) {
			error=e.getMessage();
		} 
		
		assertNull(s);
		assertEquals(error,"credit card expired by year");
	}
	
	@Test
	public void payValidShipmentWithPurchaseInvalidCreditExpiredMonth(){
		PaymentDto dto = new PaymentDto();
		dto.setShipmentId(VALID_SHIPID_EXIST_HAS_PURCHASE);
		dto.setCcNum(VALID_CCNUM);
		dto.setCcCSV(VALID_CSV);
		dto.setCcExp(INVALID_EXP_MONTH);
		dto.setCcFirstname(VALID_FIRSTNAME);
		dto.setCcLastname("Solo"); 
		 
		Shipment s = null;
		String error=null;
		try {
			s=service.payShipment(dto);
		}
		catch(CreditCardException | ShipmentException e) {
			error=e.getMessage();
		} 
		
		assertNull(s);
		assertEquals(error,"credit card expired by month");
	}
	
	@Test
	public void payValidShipmentWithPurchaseInvalidCreditCsvEmpty(){
		PaymentDto dto = new PaymentDto();
		dto.setShipmentId(VALID_SHIPID_EXIST_HAS_PURCHASE);
		dto.setCcNum(VALID_CCNUM);
		dto.setCcCSV(INVALID_CSV_EMPTY);
		dto.setCcExp(VALID_EXP);
		dto.setCcFirstname(VALID_FIRSTNAME);
		dto.setCcLastname("Solo"); 
		 
		Shipment s = null;
		String error=null;
		try {
			s=service.payShipment(dto);
		}
		catch(CreditCardException | ShipmentException e) {
			error=e.getMessage();
		} 
		
		assertNull(s);
		assertEquals(error,"credit card CSV cannot be empty");
	}
	
	@Test
	public void payValidShipmentWithPurchaseInvalidCreditCsvSpecialChar(){
		PaymentDto dto = new PaymentDto();
		dto.setShipmentId(VALID_SHIPID_EXIST_HAS_PURCHASE);
		dto.setCcNum(VALID_CCNUM);
		dto.setCcCSV(INVALID_CSV_SPECIAL_CHAR);
		dto.setCcExp(VALID_EXP);
		dto.setCcFirstname(VALID_FIRSTNAME);
		dto.setCcLastname("Solo"); 
		 
		Shipment s = null;
		String error=null;
		try {
			s=service.payShipment(dto);
		}
		catch(CreditCardException | ShipmentException e) {
			error=e.getMessage();
		} 
		
		assertNull(s);
		assertEquals(error,"credit card CSV should only contain digits 0-9");
	}
	
	@Test
	public void payValidShipmentWithPurchaseInvalidCreditCsvWrongLength(){
		PaymentDto dto = new PaymentDto();
		dto.setShipmentId(VALID_SHIPID_EXIST_HAS_PURCHASE);
		dto.setCcNum(VALID_CCNUM);
		dto.setCcCSV(INVALID_CSV_WRONG_LENGTH);
		dto.setCcExp(VALID_EXP);
		dto.setCcFirstname(VALID_FIRSTNAME);
		dto.setCcLastname("Solo"); 
		 
		Shipment s = null;
		String error=null;
		try {
			s=service.payShipment(dto);
		}
		catch(CreditCardException | ShipmentException e) {
			error=e.getMessage();
		} 
		
		assertNull(s);
		assertEquals(error,"invalid credit card CSV. Must be only 3 digits. Check and try again");
	}
	
	@Test
	public void payValidShipmentWithPurchaseInvalidCreditFirstnameEmpty(){
		PaymentDto dto = new PaymentDto();
		dto.setShipmentId(VALID_SHIPID_EXIST_HAS_PURCHASE);
		dto.setCcNum(VALID_CCNUM);
		dto.setCcCSV(VALID_CSV);
		dto.setCcExp(VALID_EXP);
		dto.setCcFirstname(INVALID_FIRSTNAME_EMPTY);
		dto.setCcLastname("Solo"); 
		 
		Shipment s = null;
		String error=null;
		try {
			s=service.payShipment(dto);
		}
		catch(CreditCardException | ShipmentException e) {
			error=e.getMessage();
		} 
		
		assertNull(s);
		assertEquals(error,"firstname cannot be empty");
	}
	
	@Test
	public void payValidShipmentWithPurchaseInvalidCreditFirstnameSpace(){
		PaymentDto dto = new PaymentDto();
		dto.setShipmentId(VALID_SHIPID_EXIST_HAS_PURCHASE);
		dto.setCcNum(VALID_CCNUM);
		dto.setCcCSV(VALID_CSV);
		dto.setCcExp(VALID_EXP);
		dto.setCcFirstname(INVALID_FIRSTNAME_SPACE);
		dto.setCcLastname("Solo"); 
		 
		Shipment s = null;
		String error=null;
		try {
			s=service.payShipment(dto);
		}
		catch(CreditCardException | ShipmentException e) {
			error=e.getMessage();
		} 
		
		assertNull(s);
		assertEquals(error,"first or last name cannot contain space");
	}
	
	@Test
	public void payValidShipmentWithPurchaseInvalidCrediFirstNameSpecialChar(){
		PaymentDto dto = new PaymentDto();
		dto.setShipmentId(VALID_SHIPID_EXIST_HAS_PURCHASE);
		dto.setCcNum(VALID_CCNUM);
		dto.setCcCSV(VALID_CSV);
		dto.setCcExp(VALID_EXP);
		dto.setCcFirstname(INVALID_FIRSTNAME_SPECIAL_CHAR);
		dto.setCcLastname("Solo"); 
		 
		Shipment s = null;
		String error=null;
		try {
			s=service.payShipment(dto);
		}
		catch(CreditCardException | ShipmentException e) {
			error=e.getMessage();
		} 
		
		assertNull(s);
		assertEquals(error,"first or last name cannot contain special characters");
	}
	
}
