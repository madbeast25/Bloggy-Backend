package com.bloggy.Entities;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Users")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY )
	private int id;
	private String username;
	private String password;
	private String email;
	private int age;
	private String role="ROLE_USER";
	@CreationTimestamp
	private LocalDate createdAt;
	private LocalDate dob;
	
	@OneToMany(mappedBy = "user")
	private List<Blog> blogs;
	
	@ManyToMany
	@JoinTable(
		name = "Followers",
		joinColumns=@JoinColumn(name = "follower_id"),
		inverseJoinColumns = @JoinColumn(name = "following_id")
    )
	Set<User> following=new HashSet<>();
	
	@ManyToMany(mappedBy = "following")
	Set<User> followers=new HashSet<>();
	
	@OneToOne(mappedBy = "photo_user",cascade = CascadeType.ALL)
	private Photo profilePic;
	
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public LocalDate getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDate createdAt) {
		this.createdAt = createdAt;
	}
	public LocalDate getDob() {
		return dob;
	}
	public void setDob(LocalDate dob) {
		this.dob = dob;
	}
	public List<Blog> getBlogs() {
		return blogs;
	}
	public void setBlogs(List<Blog> blogs) {
		this.blogs = blogs;
	}
	public Set<User> getFollowing() {
		return following;
	}
	public void setFollowing(Set<User> following) {
		this.following = following;
	}
	public Set<User> getFollowers() {
		return followers;
	}
	public void setFollowers(Set<User> followers) {
		this.followers = followers;
	}
	public Photo getProfilePic() {
		return profilePic;
	}
	public void setProfilePic(Photo profilePic) {
		this.profilePic = profilePic;
	}
	
	
	
	
	
	

}
