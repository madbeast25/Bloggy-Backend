package com.bloggy.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.bloggy.JWT.JwtFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
	
	@Autowired
	UserDetailsService service;
	
	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public JwtFilterChain jwtFilterChain() {
		return new JwtFilterChain();
	}
	
	@Bean
	public AuthenticationManager getAuth(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}
	
	@Bean
	public DaoAuthenticationProvider provider() {
		DaoAuthenticationProvider auth=new DaoAuthenticationProvider(service);
		auth.setPasswordEncoder(encoder());
		
		return auth;
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		 http.cors((cors)->cors.configure(http))
		     .csrf((csrf)-> csrf.disable())
		     .authorizeHttpRequests((auth)-> 
		                    auth.requestMatchers("/bloggy/auth/**").permitAll()
		                        .requestMatchers("/bloggy/socials/**").authenticated()
		                        .requestMatchers("/bloggy/blog/**").authenticated()
		                        .requestMatchers("bloggy/photo/**").authenticated()
		                        .anyRequest().authenticated()
		    		 );
		 
		 http.addFilterBefore(jwtFilterChain(),UsernamePasswordAuthenticationFilter.class);
		 http.authenticationProvider(provider());
		 
		 return http.build();
	}
}
