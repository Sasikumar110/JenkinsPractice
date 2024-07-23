package com.example.demo.UserController;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.LoginEntity;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class UserController {
	
	@Autowired
	private UserService service;
	
	@Autowired
	private UserRepository repo;
	
	@RequestMapping(value="/getAll",method=RequestMethod.GET)
	public List<LoginEntity> getData(LoginEntity user)
	{
		return service.GetAllData(user);
	   
	}
	
	
	@GetMapping(value="/getId/{id}")
	public LoginEntity getIdvalue(@PathVariable("id") int id)
	{
		return service.finValueId(id);
	}
	
	@PutMapping(value="/update/{id}")
	public LoginEntity getIdvalue(@RequestBody LoginEntity value,@PathVariable("id") int id)
	{
		return service.updatingValue(value,id);
	}
	
	@PostMapping("/save")
	public LoginEntity saveValue(@RequestBody LoginEntity save)
	{
		return service.saveData(save);
	}
	
	@DeleteMapping("/delete/{id}")
	public String deleteValue(@PathVariable("id") int id)
	{
		service.deletevalue(id);
		return "Deleted successfully";
	}
	
	@PostMapping(value="/login/details",produces="application/json")
	public ResponseEntity<?> getLoginDetails(@RequestBody LoginEntity user)
	{
		Optional<LoginEntity> username= repo.findByUserName(user.getUserName());
		if(!username.isPresent()) 
		{
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
		}

		
		LoginEntity ln = username.get();
		
		if(!user.getPassword().equals(ln.getPassword()))
		{
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
		}
		
		if(user.getUserName().equals(ln.getUserName()) && user.getPassword().equals(ln.getPassword()))
		{
			return ResponseEntity.ok(user);
		}
		else
		{
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
		}
	}

}
