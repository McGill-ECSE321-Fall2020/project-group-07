package ca.mcgill.ecse321.onlinegallery.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.onlinegallery.dao.GalleryAdminRepository;
import ca.mcgill.ecse321.onlinegallery.model.GalleryAdmin;
import ca.mcgill.ecse321.onlinegallery.service.exception.AdminException;

@Service
public class AdminService {

	@Autowired
	GalleryAdminRepository adminRepo;
	
	@Transactional
	public GalleryAdmin getAdmin() throws AdminException {
	
		List<GalleryAdmin> admins = toList(adminRepo.findAll());
		
		if(admins.size() == 0) throw new AdminException("There exsists no Admin");
		if(admins.size() > 1) throw new AdminException("There exists more than one Admin");
		
		return admins.get(0);
		
	}
	
	private <T> List<T> toList(Iterable<T> iterable){
		List<T> resultList = new ArrayList<T>();
		for (T t : iterable) {
			resultList.add(t);
		}
		return resultList;
	}
	
}
