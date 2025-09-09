package com.bloggy.JWT;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.bloggy.Services.UserDetailServiceImp;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilterChain extends OncePerRequestFilter {
	
	@Autowired
	private JwtUtils utils;
	@Autowired
	private UserDetailServiceImp service;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		try {
			String token=utils.extractTokenFromHeader(request);
			
			if(token != null && utils.validateToken(token) == true) {
				String username=utils.extractNameFromToken(token);
				UserDetails details=service.loadUserByUsername(username);
				
				if(details != null) {
					UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(details,null,details.getAuthorities());
					authenticationToken.setDetails(request);
					SecurityContextHolder.getContext().setAuthentication(authenticationToken);	
				}
			}
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
		
		filterChain.doFilter(request, response);
		
	}

}
