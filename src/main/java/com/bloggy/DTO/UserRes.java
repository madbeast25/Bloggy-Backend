package com.bloggy.DTO;

import java.time.LocalDate;

public class UserRes {

	private int id;
	private String username;
	private String email;
	private LocalDate dob;
	private int posts;
	private int following_count;
	private int followers_count;
	
	
	public UserRes(int id, String username, String email, LocalDate dob, int following_count, int followers_count) {
		this.id = id;
		this.username = username;
		this.email = email;
		this.dob = dob;
		this.following_count = following_count;
		this.followers_count = followers_count;
	}
	
	
	
	public UserRes(int id, String username, String email, LocalDate dob, int posts, int following_count,
			int followers_count) {
		this.id = id;
		this.username = username;
		this.email = email;
		this.dob = dob;
		this.posts = posts;
		this.following_count = following_count;
		this.followers_count = followers_count;
	}



	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public LocalDate getDob() {
		return dob;
	}
	public void setDob(LocalDate dob) {
		this.dob = dob;
	}
	public int getFollowing_count() {
		return following_count;
	}
	public void setFollowing_count(int following_count) {
		this.following_count = following_count;
	}
	public int getFollowers_count() {
		return followers_count;
	}
	public void setFollowers_count(int followers_count) {
		this.followers_count = followers_count;
	}
	public int getPosts() {
		return posts;
	}
	public void setPosts(int posts) {
		this.posts = posts;
	}
	
	
	
	
}
