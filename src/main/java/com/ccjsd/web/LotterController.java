package com.ccjsd.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ccjsd.business.lotter.Lotter;
import com.ccjsd.business.lotter.LotterRepository;
import com.ccjsd.util.CCJSDMaintenanceReturn;

@CrossOrigin
@Controller    // This means that this class is a Controller
@RequestMapping(path="/Lotters") // This means URL's start with /lotter (after Application path)
public class LotterController extends BaseController {
	@Autowired // This means to get the bean called lotterRepository
	           // Which is auto-generated by Spring, we will use it to handle the data
	private LotterRepository lotterRepository;

	@GetMapping(path="/List") //instead of all, we use list 
	public @ResponseBody Iterable<Lotter> getAllLotters() {
		// This returns a JSON or XML with the lotters
		return lotterRepository.findAll();
	}
	
	@GetMapping(path="/Get")
	public @ResponseBody List<Lotter> getUSer (@RequestParam int id) {
		
		Optional<Lotter> u =lotterRepository.findById(id);
		return getReturnArray(u.get());
//		if (u.isPresent())
//			return u.get();
//		else
//			return null;
	}
	
//	@GetMapping(path="/Authenticate")
//	public @ResponseBody List<Lotter> getAuthenticate (@RequestParam String uname, @RequestParam String pwd) {
//	
//		Lotter lotter = lotterRepository.findByLotterName(uname);
//		return getReturnArray(lotter);
//	
//	}
	
	@PostMapping(path="/Add") // Map ONLY GET Requests
	public @ResponseBody CCJSDMaintenanceReturn addNewLotter (@RequestBody Lotter lotter) {
			try {
		lotterRepository.save(lotter);
		return CCJSDMaintenanceReturn.getMaintReturn(lotter);
	}
			catch (Exception e) {
		lotter = null;
	}
return CCJSDMaintenanceReturn.getMaintReturn(lotter);
}
	
	@GetMapping(path="/Remove") //Map ONLY GET requests
	public @ResponseBody CCJSDMaintenanceReturn deleteLotter (@RequestParam int id) {
		//@ResponseBody means the returned String is the response, not a view name
		//@ResponseParam means it is a parameter from the GET or POST request
		
	Optional <Lotter> lotter = lotterRepository.findById(id);
		try {
	lotterRepository.delete(lotter.get());
	//System.out.printlin("Lotter deleted:   " + lotter.get());
}
catch (Exception e) {
	lotter = null;
}
return CCJSDMaintenanceReturn.getMaintReturn(lotter);
}
	
	@PostMapping(path="/Change") 
	public @ResponseBody CCJSDMaintenanceReturn updateLotter (@RequestBody Lotter lotter) {
		try {
			lotterRepository.save(lotter);
		}
		catch (Exception e) {
			lotter = null;
		}
	return CCJSDMaintenanceReturn.getMaintReturn(lotter);
	}
}


	
	
	
	