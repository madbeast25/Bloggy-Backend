package com.bloggy.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bloggy.DTO.LoginDTO;
import com.bloggy.Entities.User;
import com.bloggy.Exceptions.EmailAlreadyExistException;
import com.bloggy.JWT.JwtResponse;
import com.bloggy.JWT.JwtUtils;
import com.bloggy.Repositories.UserRepo;

@Service
public class AuthServices {
	
	@Autowired
	private UserRepo repo;
	@Autowired
	private PasswordEncoder encoder;
	@Autowired
	private AuthenticationManager manager;
	@Autowired
	private JwtUtils utils;

	public User registerNewUser(User newUser) throws Exception {
		User user=new User();
		
		String email=newUser.getEmail();
		
		if(repo.existsByEmail(email)) {
			throw new EmailAlreadyExistException("Sorry already an user with this email exist in our DB!");
		}
		
		user.setUsername(newUser.getUsername());
		user.setPassword(encoder.encode(newUser.getPassword()));
		user.setEmail(newUser.getEmail());
		user.setAge(newUser.getAge());
		user.setDob(newUser.getDob());
		
		repo.save(user);
		return user;
	}
	
	public JwtResponse authenticate(LoginDTO req) {
		Authentication auth=manager.authenticate(new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(auth);
		
		UserDetails details=(UserDetails) auth.getPrincipal();
		String token=utils.generateToken(details);
		User user=repo.findByUsername(req.getUsername()).get();
		
		JwtResponse res=new JwtResponse(token,user.getId());
		
		return res;
	}
}
