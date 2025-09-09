package com.bloggy.Services;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.bloggy.Entities.User;

@Service
public class UserDetailsImp implements UserDetails {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String username;
	private String password;
	
	private Collection<? extends GrantedAuthority> authority;
	

	public UserDetailsImp() {
		
	}

	public UserDetailsImp(String username, String password, Collection<? extends GrantedAuthority> authority) {
		this.username = username;
		this.password = password;
		this.authority = authority;
	}
	
	public UserDetails build(User user) {
		GrantedAuthority authority=new SimpleGrantedAuthority(user.getRole());
		UserDetailsImp details=new UserDetailsImp(
				                      user.getUsername(),
				                      user.getPassword(),
				                      Collections.singleton(authority));
		
		return details;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authority;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

}
