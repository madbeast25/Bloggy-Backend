package com.bloggy.Services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bloggy.DTO.Profile;
import com.bloggy.DTO.UserRes;
import com.bloggy.Entities.User;
import com.bloggy.Repositories.UserRepo;

@Service
public class SocialServices {
	
	@Autowired
	private UserRepo repo;

	public Set<UserRes> followNewUser(int user_id,int follow_id) {
		User Current_User=repo.findById(user_id)
				              .orElseThrow(()-> new UsernameNotFoundException("user not found 404"));
		User friend_User=repo.findById(follow_id)
				             .orElseThrow(()-> new UsernameNotFoundException("user not found 404"));
		
		Current_User.getFollowing().add(friend_User);
		friend_User.getFollowers().add(Current_User);
		
		repo.save(Current_User);
		repo.save(friend_User);
		
		Set<User> following=Current_User.getFollowing();
		Set<UserRes> res=new HashSet<>();
		
		for(User user:following) {
			UserRes us=new UserRes(user.getId(),
					               user.getUsername(),
					               user.getEmail(),
					               user.getDob(),
					               user.getBlogs().size(),
					               user.getFollowing().size(),
					               user.getFollowers().size());
			
			res.add(us);
		}
		
		return res;
	}
	
	public Set<UserRes> getMyFollowers(int id){
		User me=repo.findById(id)
				    .orElseThrow(()-> new UsernameNotFoundException("User not found 404"));
		
		Set<User> Followers=me.getFollowers();
		Set<UserRes> res=new HashSet<>();
		
		for(User user:Followers) {
			UserRes follower=new UserRes(user.getId(),
					                     user.getUsername(),
					                     user.getEmail(),
					                     user.getDob(),
					                     user.getBlogs().size(),
					                     user.getFollowing().size(),
					                     user.getFollowers().size());
			
			res.add(follower);
		}
		
		return res;
	}
	
	public Set<UserRes> getMyFollowing(int user_id){
		User me=repo.findById(user_id)
				    .orElseThrow(()-> new UsernameNotFoundException("user not found 404!"));
		
		Set<User> following=me.getFollowing();
		Set<UserRes> myFollowing=new HashSet<>();
		
		for(User user:following) {
			UserRes fav=new UserRes(user.getId(),
					                user.getUsername(),
					                user.getEmail(),
					                user.getDob(),
					                user.getBlogs().size(),
					                user.getFollowing().size(),
					                user.getFollowers().size());
			
			myFollowing.add(fav);
		}
		
		return myFollowing;
	}
	
	public List<UserRes> getNewUsersToFollow(int id){
		List<User> allUsers=repo.findAll();
		User Current_User=repo.findById(id).get();
		
		Set<User> following=Current_User.getFollowing();
		List<Integer> following_ids=new ArrayList<>();
		
		for(User user:following) {
			following_ids.add(user.getId());
		}
		
		List<UserRes> available=new ArrayList<>();
		
		for(User allUser:allUsers) {
			if(!following_ids.contains(allUser.getId()) && allUser.getId() != id) {
				available.add(new UserRes(allUser.getId(),
						                  allUser.getUsername(),
						                  allUser.getEmail(),
						                  allUser.getDob(),
						                  allUser.getBlogs().size(),
						                  allUser.getFollowing().size(),
						                  allUser.getFollowers().size()));
			}
		}
		
		return available;
	}
	
	public Profile getProfile(int id) {
		User user=repo.findById(id).get();
		
		Profile user_profile=new Profile();
		
		user_profile.setId(user.getId());
		user_profile.setUsername(user.getUsername());
		user_profile.setEmail(user.getEmail());
		user_profile.setPosts(user.getBlogs().size());
		user_profile.setFollowers(user.getFollowers().size());
		user_profile.setFollowing(user.getFollowing().size());
		user_profile.setDob(user.getDob());
		user_profile.setCreatedAt(user.getCreatedAt());
		
		return user_profile;
	}
	
	public Boolean getStatus(int user_id,int follow_id) {
		User user=repo.findById(user_id).get();
		
		Set<User> following=user.getFollowing();
		
		for(User fellow:following) {
			if(fellow.getId() == follow_id) {
				return true;
			}
		}
		
		return false;
	}
}
