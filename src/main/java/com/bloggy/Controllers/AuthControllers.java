package com.bloggy.Controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bloggy.DTO.LoginDTO;
import com.bloggy.Entities.User;
import com.bloggy.JWT.JwtResponse;
import com.bloggy.JWT.JwtUtils;
import com.bloggy.Services.AuthServices;

@RestController
@RequestMapping("/bloggy/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthControllers {
	
	@Autowired
	private AuthenticationManager manager;
	@Autowired
	private JwtUtils utils;
	@Autowired
	private AuthServices service;

	@PostMapping("/login")
	public ResponseEntity<JwtResponse> loginUser(@RequestBody LoginDTO req,Principal principal){
		try {
			JwtResponse res=service.authenticate(req);
			return new ResponseEntity<>(res,HttpStatus.OK);
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
			return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
		}
		
	}
	
	@PostMapping("/register")
	public ResponseEntity<User> newUser(@RequestBody User user) throws Exception{
		User res=service.registerNewUser(user);
		
		return new ResponseEntity<>(res,HttpStatus.OK);
	}
}
