package ca.mcgill.ecse321.onlinegallery.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.onlinegallery.model.*;
import ca.mcgill.ecse321.onlinegallery.dao.*;
import ca.mcgill.ecse321.onlinegallery.dto.*;
import ca.mcgill.ecse321.onlinegallery.service.exception.*;

@Service
public class ShipmentService {
	
	@Autowired
	ShipmentRepository shipRepo; 
	
	@Autowired
	PurchaseRepository purchaseRepo;
	
	@Transactional
	public Shipment addToShipment(Long shipmentId, Long purchaseId) throws ShipmentException, PurchaseException{
		if (!shipRepo.existsById(shipmentId)) {
			throw new ShipmentException("no Shipment with id ["+shipmentId+"] found");
		}
		if (!purchaseRepo.existsById(purchaseId)) {
			throw new PurchaseException("no Purchase with id ["+purchaseId+"] found");
		}
		
		Purchase purchase = purchaseRepo.findPurchaseByPurchaseId(purchaseId);
		Shipment shipment = shipRepo.findShipmentByShipmentId(shipmentId);
		
		if (shipment.getPurchase().iterator().hasNext()) {
			ShipmentType existingType = shipment.getPurchase().iterator().next().getShipmentType();
			ShipmentType newType = purchase.getShipmentType();
			if (!(existingType.name().equals(newType.name()))) {
				throw new ShipmentException("incompatible shipment types between existing Purchases in Shipment and Shipment to add");
			}
		}
		
		if (shipment.getPurchase().iterator().hasNext()) {
			Long existingCustomerId=shipment.getPurchase().iterator().next().getCustomer().getCustomerId();
			Long newCustomerId=purchase.getCustomer().getCustomerId();
			
			if (!(newCustomerId.equals(existingCustomerId))) {
				throw new ShipmentException("cannot add a Purchase to a Shipment owned by another Customer");
			}
		}
				
		
		shipment.getPurchase().add(purchase);
		purchase.setShipment(shipment);
		shipRepo.save(shipment);
		
		return shipment;
	}
	
	@Transactional
	public Shipment payShipment(PaymentDto dto) throws ShipmentException, CreditCardException{
		
		Long shipmentId = dto.getShipmentId();
		String ccNum=dto.getCcNum();
		String ccCSV=dto.getCcCSV();
		String firstName=dto.getCcFirstname();
		String lastName=dto.getccLastname();
		String ccExp=dto.getCcExp();
		
		if (!shipRepo.existsById(shipmentId)) {
			throw new ShipmentException("no Shipment with id ["+shipmentId+"] found");
		}
		
		Shipment shipment = shipRepo.findShipmentByShipmentId(shipmentId);
		if (!(shipment.getPurchase().iterator().hasNext())) {
			throw new ShipmentException("Shipment with id ["+shipmentId+"] has no Purchases. Cannot pay for empty Shipment");
		}
		
		//credit card validations
		
		try {
			validateCcNum(ccNum);
		}
		catch(CreditCardException e) {
			throw new CreditCardException(e.getMessage()); 
		}
		
		try {
			validateCSV(ccCSV);
		}
		catch(CreditCardException e) {
			throw new CreditCardException(e.getMessage());
		}
		
		try {
			validateExp(ccExp);
		}
		catch(CreditCardException e) {
			throw new CreditCardException(e.getMessage());
		}
		if (firstName=="") {
			throw new CreditCardException("firstname cannot be empty");
		}
		try {
			validateName(firstName);
		}
		catch(CreditCardException e){
			throw new CreditCardException(e.getMessage());
		}
		if (lastName=="") {
			throw new CreditCardException("lastname cannot be empty");
		}
		// invalid lastname
		try {
			validateName(lastName);
		}
		catch(CreditCardException e){
			throw new CreditCardException(e.getMessage());
		}
		
		shipment.setCreditCardNumber(ccNum.substring(ccNum.length()-4));		//store only last 4 digits of credit card
		shipment.setShipmentStatus(ShipmentStatus.PAID);
		
		shipment=shipRepo.save(shipment);
		
		return shipment;
	}
	
	public List<Shipment> getAllShipments() throws ShipmentException{
		if (shipRepo.count()==0) {
			throw new ShipmentException("no Shipments in system");
		}
		
		List<Shipment> slist= new ArrayList<Shipment>();
		
		for (Shipment s:shipRepo.findAll()) {
			slist.add(s);
		}
		
		return slist;
	}
	
	public void validateExp(String exp) throws CreditCardException{

		
		int length=exp.length();
		if (length==0) {
			throw new CreditCardException("credit card expiration date cannot be empty");
		}

		String allowedChars="0123456789";
		List<Character> charList=new ArrayList<Character>();
		
		for (char c:allowedChars.toCharArray()) {
			charList.add(c);
		}
		
		for (char c:exp.toCharArray()) {
			if (!(charList.contains(c))) {
				throw new CreditCardException("credit card expiration date should only contain digits 0-9");
			}
		}
	
		if (length!=4) {
			throw new CreditCardException("invalid credit card expiration date. Must be only 4 digits. Check and try again");						
		}
		
		int year = Integer.parseInt(exp.substring(2));
		int month = Integer.parseInt(exp.substring(0, 2));
		
		
		if (month<1 || month>12) {
			throw new CreditCardException("invalid credit card expiration date. Month must be between 1-12 inclusive");						
		}
		
		int nowYear=LocalDate.now().getYear()%100;
		int nowMonth=LocalDate.now().getMonthValue();
		
		if (year<nowYear) {
			throw new CreditCardException("credit card expired by year");									
		}
		
		if (year==nowYear) {
			if (month<=nowMonth) {
				throw new CreditCardException("credit card expired by month");		
			}
		}
	}
	
	private void validateCSV(String ccCSV) throws CreditCardException{
		
		int length=ccCSV.length();
		if (length==0) {
			throw new CreditCardException("credit card CSV cannot be empty");
		}

		String allowedChars="0123456789";
		List<Character> charList=new ArrayList<Character>();
		
		for (char c:allowedChars.toCharArray()) {
			charList.add(c);
		}
		
		for (char c:ccCSV.toCharArray()) {
			if (!(charList.contains(c))) {
				throw new CreditCardException("credit card CSV should only contain digits 0-9");
			}
		}
	
		if (length!=3) {
			throw new CreditCardException("invalid credit card CSV. Must be only 3 digits. Check and try again");						
		}
		
	}
	
	private void validateCcNum(String ccNum) throws CreditCardException{

			
		// check if empty card number
		int length=ccNum.length();
		if (length==0) {
			throw new CreditCardException("credit card number cannot be empty");
		}

		String allowedChars="0123456789- ";
		List<Character> charList=new ArrayList<Character>();
		
		for (char c:allowedChars.toCharArray()) {
			charList.add(c);
		}
		
		for (char c:ccNum.toCharArray()) {
			if (!(charList.contains(c))) {
				throw new CreditCardException("cred card number should only contain digits 0-9, space, and dash");
			}
		}
		
		// strip space and dash, common delimiters in cc num entry
		ccNum=ccNum.replaceAll("\\s+", "");
		ccNum=ccNum.replaceAll("-", "");
		
		length=ccNum.length();
		
		//limit to only Visa for now
		// all Visa cards start with 4, check if prefix matches
		int prefix=Integer.parseInt(String.valueOf(ccNum.charAt(0)));	
		if (prefix!=4) {
			throw new CreditCardException("sorry, only Visa cards accepted. Those credit card numbers start with 4");			
		}
		
		// check if the numbers are 13, 16, or 19 digits
		if (length!=13 && length!=16 && length!=19) {
			throw new CreditCardException("invalid Visa credit card number. Those are either 13, 16, or 19 dgits. Check and try again");						
		}
		
		
		// by now, establish that the cc number fits the Visa template
		// start with the Luhn verification algorith
		// reference: https://en.wikipedia.org/wiki/Luhn_algorithm
		
		// checksum for verification
		int checksum=Integer.parseInt(String.valueOf(ccNum.charAt(ccNum.length()-1)));
		
		// convert String to digits
		String lastDigitDropped=ccNum.substring(0,ccNum.length()-1);
		List<Integer> digits = new ArrayList<Integer>();
		
		for (char c:lastDigitDropped.toCharArray()) {
			digits.add(Integer.parseInt(String.valueOf(c)));
		}
		
		// reverse digits		
		Collections.reverse(digits);
		
		// double odd digits, subtract 9 if greater than 9
		for (int i=0;i<digits.size();i+=2) {
			Integer result=digits.get(i)*2;
			if (result>9) {
				result=result-9;
			}
			digits.set(i, result);
		}

		//sum up digits and checksum
		int sum=0;
		for (Integer i:digits) {
			sum+=i;
		}
		sum+=checksum;
		
		boolean valid=(sum%10==0);
		
		if (!valid) {
			throw new CreditCardException("invalid Visa credit card number. Try again");
		}
		
	}
	
	private void validateName(String name) throws CreditCardException{

		// btwn 2 and 20 chars
		// no space
		// no special character


		// to check space
		if (name.contains(" ")) {
			throw new CreditCardException("first or last name cannot contain space");
		}

		// for special characters
		if ((name.contains("@") || name.contains("#") || name.contains("!") || name.contains("~")
				|| name.contains("$") || name.contains("%") || name.contains("^") || name.contains("&")
				|| name.contains("*") || name.contains("(") || name.contains(")") || name.contains("-")
				|| name.contains("+") || name.contains("/") || name.contains(":") || name.contains(".")
				|| name.contains(", ") || name.contains("<") || name.contains(">") || name.contains("?")
				|| name.contains("|"))) {
			throw new CreditCardException("first or last name cannot contain special characters");
		}
	}

}
