package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.LoginEntity;
import com.example.demo.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userrepo;
	
	public List<LoginEntity> GetAllData(LoginEntity enti) {
		return userrepo.findAll();
	}
	
	public LoginEntity saveData(LoginEntity save)
	{
		LoginEntity li = new LoginEntity();
		li.setId(save.getId());
		li.setUserName(save.getUserName());
		li.setPassword(save.getPassword());
		li.setEmail(save.getEmail());
		LoginEntity ui = userrepo.save(li);
		ui.setId(save.getId());
		ui.setUserName(save.getUserName());
		ui.setPassword(save.getPassword());
		ui.setEmail(save.getEmail());
		return ui;
	}
	
	public LoginEntity finValueId(int id)
	{
		return userrepo.findById(id).get();
	}
	
	public LoginEntity updatingValue(LoginEntity value, int id)
	{
		LoginEntity io = userrepo.findById(id).get();
		io.setUserName(value.getUserName());
		io.setEmail(value.getEmail());
		io.setPassword(value.getPassword());
		LoginEntity ui = userrepo.save(io);
		return ui;
	}
	
	public void deletevalue(int id)
	{
		userrepo.deleteById(id);
	}
	
	public LoginEntity LoadUserByUsername(LoginEntity logen)
	{
		Optional<LoginEntity> ui = userrepo.findByUserName(logen.getUserName());
		
		LoginEntity io = ui.get();
		
		return io;
	}

}
