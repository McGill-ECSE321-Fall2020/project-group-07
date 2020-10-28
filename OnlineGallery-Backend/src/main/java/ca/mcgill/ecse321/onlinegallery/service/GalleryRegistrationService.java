package ca.mcgill.ecse321.onlinegallery.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.onlinegallery.model.*;
import ca.mcgill.ecse321.onlinegallery.dao.*;
import ca.mcgill.ecse321.onlinegallery.dto.*;
import ca.mcgill.ecse321.onlinegallery.service.exception.*;

@Service
public class GalleryRegistrationService {

	@Autowired
	OnlineGalleryRepository ogRepo;
 
	@Autowired 
	GalleryRegistrationRepository regRepo;
	
	@Autowired
	GalleryAdminRepository adminRepo; 
	
	@Autowired
	CustomerRepository custRepo;
	
	@Autowired
	ArtistRepository artistRepo;
	
	 
	@Transactional
	public GalleryRegistration getGalleryRegistration(String username) throws GalleryRegistrationException{
		if (!regRepo.existsByUserName(username)) {
			throw new GalleryRegistrationException("No GalleryRegistration with username ["+username+"] exists");
		} 
		
		GalleryRegistration reg = regRepo.findGalleryRegisrationByUserName(username);
		return reg;
	}
	
	@Transactional
	public List<GalleryRegistration> getAllGalleryRegistrations() throws GalleryRegistrationException{
		
		List<GalleryRegistration> allReg = new ArrayList<GalleryRegistration>();
		
		if (regRepo.count()==0) {
			throw new GalleryRegistrationException("no GalleryRegistrations found in system");
		}
		else {
			for (GalleryRegistration reg:regRepo.findAll()) {
				allReg.add(reg);
			}
		}
		return allReg;
		
	}
	
	@Transactional
	public GalleryRegistration createGalleryRegistration(GalleryRegistrationDto dto) throws GalleryRegistrationException{
		String username=dto.getUsername();
		String firstname=dto.getFirstName();
		String lastname=dto.getLastName();
		String email=dto.getEmail();
		String password=dto.getPassword();
		
		// existing username
		if (regRepo.existsByUserName(username)) {
			throw new GalleryRegistrationException("A GalleryRegistration with username ["+username+"] already exists");
		}
		
		// empty username
		if (username=="") {
			throw new GalleryRegistrationException("username cannot be empty");
		}
		
		// invalid username
		try {
			isUsernameValid(username);
		}
		catch(IllegalArgumentException e){
			throw new GalleryRegistrationException(e.getMessage());
		}
		
		// empty firstname
		if (firstname=="") {
			throw new GalleryRegistrationException("firstname cannot be empty");
		}
		// invalid firstname
		try {
			isNameValid(firstname);
		}
		catch(IllegalArgumentException e){
			throw new GalleryRegistrationException(e.getMessage());
		}
		
		// empty lastname
		if (lastname=="") {
			throw new GalleryRegistrationException("lastname cannot be empty");
		}
		// invalid lastname
		try {
			isNameValid(lastname);
		}
		catch(IllegalArgumentException e){
			throw new GalleryRegistrationException(e.getMessage());
		}
		
		// emtpy email
		if (email=="") {
			throw new GalleryRegistrationException("email address cannot be empty");
		}
		
		//invalid email
		String emailRegexTemplate="^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
		if (!email.matches(emailRegexTemplate)) {
			throw new GalleryRegistrationException("invalid email address");
		}
		
		
		//emtpy password
		if (password=="") {
			throw new GalleryRegistrationException("password cannot be empty");
		}
		
		//invalid password
		try {
			isPasswordValid(password);
		}
		catch(IllegalArgumentException e){
			throw new GalleryRegistrationException(e.getMessage());
		}
		
		GalleryRegistration reg = new GalleryRegistration();
		reg.setUserName(dto.getUsername());
		reg.setFirstName(dto.getFirstName());
		reg.setLastName(dto.getLastName());
		reg.setEmail(dto.getEmail());
		reg.setPassWord(dto.getPassword());
		reg.setIsLoggedIn(false);
		
		OnlineGallery og;
		
		if (ogRepo.findAll().iterator().hasNext()) {
			og = ogRepo.findAll().iterator().next();
		} else {
			og = new OnlineGallery();
			og.setDaysUp(0);
			og.setTotalEarnings(0);
		}
		
		og.getAllRegistrations().add(reg);
		ogRepo.save(og);
		reg=regRepo.save(reg);
		
		return reg;
	}
	
	@Transactional
	public GalleryRegistration updateEmail(UpdateEmailDto dto) throws GalleryRegistrationException {
		String username = dto.getUsername();
		String email = dto.getEmail();
		
		// no username found
		if (!regRepo.existsByUserName(username)) {
			throw new GalleryRegistrationException("No GalleryRegistration with username [" + username + "] found");
		}

		// emtpy email
		if (email == "") {
			throw new GalleryRegistrationException("email address cannot be empty");
		}

		// invalid email
		String emailRegexTemplate = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
		if (!email.matches(emailRegexTemplate)) {
			throw new GalleryRegistrationException("invalid email address");
		}
		
		GalleryRegistration reg = regRepo.findGalleryRegisrationByUserName(username);
		reg.setEmail(email);
		
		regRepo.save(reg);
		
		return reg;

	}
	
	@Transactional
	public GalleryRegistration updatePassowrd(UpdatePasswordDto dto) throws GalleryRegistrationException {
		String username = dto.getUsername();
		String password = dto.getPassowrd();
		
		// no username found
		if (!regRepo.existsByUserName(username)) {
			throw new GalleryRegistrationException("No GalleryRegistration with username [" + username + "] found");
		}

		// emtpy email
		if (password == "") {
			throw new GalleryRegistrationException("password cannot be empty");
		}

		// invalid password
		try {
			isPasswordValid(password);
		} catch (IllegalArgumentException e) {
			throw new GalleryRegistrationException(e.getMessage());
		}
		
		GalleryRegistration reg = regRepo.findGalleryRegisrationByUserName(username);
		reg.setPassWord(password);
		
		regRepo.save(reg);
		
		return reg;

	}
	
	@Transactional
	public List<GalleryRegistration> deleteAllGalleryRegistration() throws GalleryRegistrationException{
		List<GalleryRegistration> deletedRegs = new ArrayList<GalleryRegistration>();
		if (regRepo.count()==0) {
			throw new GalleryRegistrationException("no GalleryRegistrations in system");
		}
		
		for (GalleryRegistration reg:regRepo.findAll()) {
			deletedRegs.add(reg);
			this.deleteGalleryRegistration(reg.getUserName());
		}
		
		return deletedRegs;
	}
	
	@Transactional
	public GalleryRegistration deleteGalleryRegistration(String username) throws GalleryRegistrationException{
		if (!regRepo.existsByUserName(username)) {
			throw new GalleryRegistrationException("No GalleryRegistration with username ["+username+"] exists");
		}
		
		GalleryRegistration reg = regRepo.findGalleryRegisrationByUserName(username);
		OnlineGallery og = ogRepo.findAll().iterator().next();
		
		
		if (reg.getCustomer()!=null) {
			Customer cust = reg.getCustomer();
			reg.setCustomer(null);
			custRepo.delete(cust);
		}
		
		if (reg.getArtist()!=null) {
			Artist artist = reg.getArtist();
			reg.setArtist(null);
			artistRepo.delete(artist);
		}
		
		if (reg.getAdmin()!=null) {
			GalleryAdmin admin = reg.getAdmin();
			reg.setAdmin(null);
			adminRepo.delete(admin);
		}
		
		og.getAllRegistrations().remove(reg);
		
		regRepo.delete(reg);
		return reg;
		
	}
	
	@Transactional
	public GalleryRegistration setCustomer(String username) throws GalleryRegistrationException {
		
		// no username found
		if (!regRepo.existsByUserName(username)) {
			throw new GalleryRegistrationException("No GalleryRegistration with username [" + username + "] found");
		}
		
		GalleryRegistration reg = regRepo.findGalleryRegisrationByUserName(username);
		
		if (!(reg.getCustomer()==null)) {
			throw new GalleryRegistrationException("GalleryRegistration with username [" + username + "] already associated with a Customer");
		}
		
		reg.setCustomer(new Customer());
		
		regRepo.save(reg);
		
		return reg;

	}
	
	@Transactional
	public GalleryRegistration setArtist(String username) throws GalleryRegistrationException {
		
		// no username found
		if (!regRepo.existsByUserName(username)) {
			throw new GalleryRegistrationException("No GalleryRegistration with username [" + username + "] found");
		}
		
		GalleryRegistration reg = regRepo.findGalleryRegisrationByUserName(username);
		
		if (!(reg.getArtist()==null)) {
			throw new GalleryRegistrationException("GalleryRegistration with username [" + username + "] already associated with a Artist");
		}
		
		reg.setArtist(new Artist());
		
		regRepo.save(reg);
		
		return reg;

	}
	
	@Transactional
	public GalleryRegistration setAdmin(String username) throws GalleryRegistrationException {
		

		// no username found
		if (!regRepo.existsByUserName(username)) {
			throw new GalleryRegistrationException("No GalleryRegistration with username [" + username + "] found");
		}
		
		GalleryRegistration reg = regRepo.findGalleryRegisrationByUserName(username);
		
		if (!(reg.getAdmin()==null)) {
			throw new GalleryRegistrationException("GalleryRegistration with username [" + username + "] already associated with a Admin");
		}
		
		if (adminRepo.count()!=0) {
			throw new GalleryRegistrationException("there is already an Admin in database");
		}
		
		
		reg.setAdmin(new GalleryAdmin());;
		
		regRepo.save(reg);
		
		return reg;

	}
	
	private void isNameValid(String name) {

		// btwn 2 and 20 chars
		// no space
		// no special character

		if (!((name.length() >= 2) && (name.length() <= 20))) {
			throw new IllegalArgumentException("first or last name has to be 2 and 20 characters inclusively");
		}

		// to check space
		if (name.contains(" ")) {
			throw new IllegalArgumentException("first or last name cannot contain space");
		}

		// for special characters
		if ((name.contains("@") || name.contains("#") || name.contains("!") || name.contains("~")
				|| name.contains("$") || name.contains("%") || name.contains("^") || name.contains("&")
				|| name.contains("*") || name.contains("(") || name.contains(")") || name.contains("-")
				|| name.contains("+") || name.contains("/") || name.contains(":") || name.contains(".")
				|| name.contains(", ") || name.contains("<") || name.contains(">") || name.contains("?")
				|| name.contains("|"))) {
			throw new IllegalArgumentException("first or last name cannot contain special characters");
		}
	}
	
	private void isUsernameValid(String username) {

		// btwn 5 and 20 chars
		// no space
		// no special character

		if (!((username.length() >= 5) && (username.length() <= 20))) {
			throw new IllegalArgumentException("username has to be 5 and 20 characters inclusively");
		}

		// to check space
		if (username.contains(" ")) {
			throw new IllegalArgumentException("username cannot contain space");
		}

		// for special characters
		if ((username.contains("@") || username.contains("#") || username.contains("!") || username.contains("~")
				|| username.contains("$") || username.contains("%") || username.contains("^") || username.contains("&")
				|| username.contains("*") || username.contains("(") || username.contains(")") || username.contains("-")
				|| username.contains("+") || username.contains("/") || username.contains(":") || username.contains(".")
				|| username.contains(", ") || username.contains("<") || username.contains(">") || username.contains("?")
				|| username.contains("|"))) {
			throw new IllegalArgumentException("username cannot contain special characters");
		}
	}
	
	private void isPasswordValid(String password) {

		// btwn 8 and 15 chars
		// no space
		// at least 1 num
		// at least 1 special char
		// at least 1 capital
		// at least 1 lower case

		if (!((password.length() >= 8) && (password.length() <= 15))) {
			throw new IllegalArgumentException("password has to be between 8 and 15 characters inclusively");
		}

		// to check space
		if (password.contains(" ")) {
			throw new IllegalArgumentException("password cannot contain space");
		}

		int numberCount = 0;
		for (int i = 0; i <= 9; i++) {
			String str1 = Integer.toString(i);

			if (password.contains(str1)) {
				numberCount = 1;
				break;
			}
		}
		if (numberCount == 0) {
			throw new IllegalArgumentException("password has to contain at least one number");
		}

		// for special characters
		if (!(password.contains("@") || password.contains("#") || password.contains("!") || password.contains("~")
				|| password.contains("$") || password.contains("%") || password.contains("^") || password.contains("&")
				|| password.contains("*") || password.contains("(") || password.contains(")") || password.contains("-")
				|| password.contains("+") || password.contains("/") || password.contains(":") || password.contains(".")
				|| password.contains(", ") || password.contains("<") || password.contains(">") || password.contains("?")
				|| password.contains("|"))) {
			throw new IllegalArgumentException("password has to contain at least one special character");
		}

		// checking capital letters
		int capitalCount = 0;
		for (int i = 65; i <= 90; i++) {

			// type casting
			char c = (char) i;

			String str1 = Character.toString(c);
			if (password.contains(str1)) {
				capitalCount = 1;
				break;
			}
		}
		if (capitalCount == 0) {
			throw new IllegalArgumentException("password has to contain at least one capital character");
		}

		int lowerCount = 0;

		// checking small letters
		for (int i = 90; i <= 122; i++) {

			// type casting
			char c = (char) i;
			String str1 = Character.toString(c);

			if (password.contains(str1)) {
				lowerCount = 1;
				break;
			}
		}
		if (lowerCount == 0) {
			throw new IllegalArgumentException("password has to contain at least one lower character");
		}
	}	

}
