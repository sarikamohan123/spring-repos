package com.prs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prs.db.LoginRepo;
import com.prs.model.Login;
import com.prs.model.User;

@RestController
@RequestMapping("/api/Users")
public class LoginController {
	@Autowired
	private LoginRepo loginRepo;
	
	
	@PostMapping("/login")
	public ResponseEntity <User> login(@RequestBody Login login) {
		//Use the LoginRepo to find the user
		User user = loginRepo.findByUserNameAndPassWord(login.getUserName(),login.getPassWord());
		if(user ==null) {
			return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
		return ResponseEntity.ok(user);
	}
	

}
