package com.bloggy.Controllers;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bloggy.DTO.Profile;
import com.bloggy.DTO.UserRes;
import com.bloggy.Entities.User;
import com.bloggy.Services.AuthServices;
import com.bloggy.Services.SocialServices;

@RestController
@RequestMapping("/bloggy/socials")
public class SocialControllers {
	
	@Autowired
	private SocialServices service;

	@PutMapping("/follow/{user_id}/{follow_id}")
	public ResponseEntity<Set<UserRes>> followUser(@PathVariable int user_id,@PathVariable int follow_id){
		 Set<UserRes> altered_User=service.followNewUser(user_id, follow_id);
		 
		 return new ResponseEntity<>(altered_User,HttpStatus.OK);
	}
	
	@GetMapping("/follow/getFollowers/{user_id}")
	public ResponseEntity<Set<UserRes>> getFollowers(@PathVariable int user_id){
		Set<UserRes> followers=service.getMyFollowers(user_id);
		
		return new ResponseEntity<>(followers,HttpStatus.OK);
	}
	
	@GetMapping("/follow/getFollowing/{user_id}")
	public ResponseEntity<Set<UserRes>> getFollowing(@PathVariable int user_id){
		Set<UserRes> following=service.getMyFollowing(user_id);
		
		return new ResponseEntity<>(following,HttpStatus.OK);
	}
	
	@GetMapping("/follow/getUsers/{user_id}")
	public ResponseEntity<List<UserRes>> getUsers(@PathVariable int user_id){
		List<UserRes> availableUsers=service.getNewUsersToFollow(user_id);
		
		return new  ResponseEntity<>(availableUsers,HttpStatus.OK);
	}
	
	@GetMapping("/getProfile/{id}")
	public ResponseEntity<Profile> getProfile(@PathVariable int id){
		Profile profile=service.getProfile(id);
		
		return new ResponseEntity<>(profile,HttpStatus.OK);
	}
	
	@GetMapping("/getStatus/{user_id}/{follow_id}")
	public ResponseEntity<Boolean> getStatus(@PathVariable int user_id,@PathVariable int follow_id){
		Boolean followed=service.getStatus(user_id, follow_id);
		
		return new ResponseEntity<>(followed,HttpStatus.OK);
	}
}
