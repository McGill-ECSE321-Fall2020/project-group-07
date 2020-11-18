package ca.mcgill.ecse321.onlinegallery.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.onlinegallery.dto.AdminDto;
import ca.mcgill.ecse321.onlinegallery.model.GalleryAdmin;
import ca.mcgill.ecse321.onlinegallery.service.AdminService;
import ca.mcgill.ecse321.onlinegallery.service.exception.AdminException;

@CrossOrigin(origins = "*")
@RestController
public class AdminRestController {

	@Autowired
	AdminService service;
	
	@GetMapping(value = { "/getAdmin", "/getAdmin/" })
	public ResponseEntity<?> getAdmin() throws AdminException {
		try {
			GalleryAdmin admin = service.getAdmin();
			return new ResponseEntity<>(convertToDto(admin), HttpStatus.OK);			
		} catch(AdminException e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
	}
	
	private AdminDto convertToDto(GalleryAdmin admin) {

		AdminDto dto = new AdminDto();
		dto.setUsername(admin.getGalleryRegistration().getUserName());
		dto.setPassword(admin.getGalleryRegistration().getPassWord());
		
		return dto;
	}
	
}
