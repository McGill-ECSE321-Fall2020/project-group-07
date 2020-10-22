package ca.mcgill.ecse321.onlinegallery.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;


import ca.mcgill.ecse321.onlinegallery.dto.PhysicalGalleryDto;
import ca.mcgill.ecse321.onlinegallery.model.PhysicalGallery;
import ca.mcgill.ecse321.onlinegallery.service.PhysicalGalleryService;

@CrossOrigin(origins="*")
@RestController
public class PhysicalGalleryRestController {
	
	@Autowired
	PhysicalGalleryService service;
	
	@PostMapping(value = { "/create_physicalgallery/{address}", "/create_physicalgallery/{address}/" })
	public PhysicalGalleryDto createPhysicalGallery(@PathVariable("address") String address) throws IllegalArgumentException {
		PhysicalGallery pg = service.createPhysicalGallery(address);
		return convertToDto(pg);
	}
	
	@PutMapping(value = { "/update_physicaladdress/{address}", "/update_physicaladdress/{address}/" })
	public PhysicalGalleryDto updatePhysicalAddress(@PathVariable("address") String address) throws IllegalArgumentException {
		PhysicalGallery pg = service.updateAddress(address);
		return convertToDto(pg);
	}
	
	@DeleteMapping(value = { "/deletephysical", "/deletephysical/" })
	public PhysicalGalleryDto deletePhysicalGallery() throws IllegalArgumentException {
		PhysicalGallery pg = service.deletePhysicalGallery();
		PhysicalGalleryDto dto;
		try {
			dto=convertToDto(pg);
		}
		catch(IllegalArgumentException e){
			dto=null;
		}
		return dto;
	}
	
	@GetMapping(value= {"/physicalgallery","physicalgallery/"})
	public PhysicalGalleryDto getPhyiscalGallery() {
		PhysicalGalleryDto dto;
		try {
			dto=convertToDto(service.getPhysicalGallery());
		}
		catch(IllegalArgumentException e){
			dto=null;
		}
		return dto;
	}
	
	private PhysicalGalleryDto convertToDto(PhysicalGallery pg) {
		if (pg==null) {
			throw new IllegalArgumentException("There is no Physical Gallery!");
		}
		PhysicalGalleryDto pgDto=new PhysicalGalleryDto(pg.getAddress());
		return pgDto;
	}
	
}
