package com.bloggy.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bloggy.Entities.User;
import com.bloggy.Repositories.UserRepo;

@Service
public class UserDetailServiceImp implements UserDetailsService {
	
	@Autowired
	private UserRepo repo;
	@Autowired
	private UserDetailsImp details;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user=repo.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("User not found"));
		UserDetails res=details.build(user);
		
		return res;
	}

}
