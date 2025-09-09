package com.bloggy.JWT;

import java.net.http.HttpRequest;
import java.security.Key;
import java.util.Date;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class JwtUtils {
	
	@Value("${jwt.secret}")
	String secret;
	@Value("${jwt.expiration}")
	String expiration;
	
	public String extractTokenFromHeader(HttpServletRequest req) {
		String token=req.getHeader("Authorization");
		
		if(token != null && token.startsWith("Bearer ")) {
			return token.substring(7);
		}
		
		return null;
	}
	
	public String extractNameFromToken(String token) {
		return Jwts.parser()
				   .verifyWith((SecretKey) key())
				   .build()
				   .parseSignedClaims(token)
				   .getPayload()
				   .getSubject();
	}
	
	public boolean validateToken(String token) {
		return Jwts.parser()
				   .verifyWith((SecretKey) key())
				   .build()
				   .parseSignedClaims(token)
				   .getPayload() != null;
	}
	
	public String generateToken(UserDetails details) {
		String username=details.getUsername();
		String roles=details.getAuthorities()
				            .stream()
				            .map(auth-> auth.getAuthority())
				            .collect(Collectors.joining(","));
		
		return Jwts.builder()
				   .subject(username)
				   .claim("roles",roles)
				   .signWith(key())
				   .issuedAt(new Date())
				   .expiration(new Date(System.currentTimeMillis()+Long.parseLong(expiration)))
				   .compact();
				   
	}
	
	public Key key() {
		return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
	}

}
